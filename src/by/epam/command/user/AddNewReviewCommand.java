package by.epam.command.user;

import by.epam.command.Command;
import by.epam.entity.User;
import by.epam.entity.UserHistory;
import by.epam.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class AddNewReviewCommand implements Command {

    Logger LOG = Logger.getLogger("AddNewReview");
    private ResourceBundle resourceBundle = ResourceBundle.getBundle("resources/config");
    private static final String fName = "fName";
    private static final String lName = "lName";
    private static final String SSN = "SSN";
    private static final String cName = "cName";
    private static final String yEmployed = "yEmployed";
    private static final String yFired = "yFired";
    private static final String rating1 = "rating1";
    private static final String rating2 = "rating2";
    private static final String rating3 = "rating3";
    private static final String rating4 = "rating4";
    private static final String rating5 = "rating5";
    private static final String hireAgain = "hireAgain";

    @Override
    public String execute(HttpServletRequest request) {
        String page = resourceBundle.getString("mainPage");
        HttpSession session = request.getSession();

        User currentUser = (User) session.getAttribute("user");
        User newUser = new User();
        UserHistory userHistory = new UserHistory();

        newUser.setfName(request.getParameter(fName));
        newUser.setlName(request.getParameter(lName));
        newUser.setSSN(Integer.parseInt(request.getParameter(SSN)));

        userHistory.setCompany(request.getParameter(cName));
        userHistory.setYearEmployed(Integer.parseInt(request.getParameter(yEmployed)));
        userHistory.setYearTerminated(Integer.parseInt(request.getParameter(yFired)));
        userHistory.setRating1(Integer.parseInt(request.getParameter(rating1)));
        userHistory.setRating2(Integer.parseInt(request.getParameter(rating2)));
        userHistory.setRating3(Integer.parseInt(request.getParameter(rating3)));
        userHistory.setRating4(Integer.parseInt(request.getParameter(rating4)));
        userHistory.setRating5(Integer.parseInt(request.getParameter(rating5)));
        userHistory.setHireAgain((request.getParameter(hireAgain).equalsIgnoreCase("Yes") ? 1 : 0));

        UserService userService = new UserService();
        LOG.info("Processing new review.");
        try {
            userService.addReview(currentUser, newUser, userHistory);
            request.setAttribute("reviewAddResult", "New review is added");
        } catch (SQLException e) {
            request.setAttribute("reviewAddResult", "Error while processing");
            LOG.info("Error during new review creation: " + e);
        }

        LOG.info("Processing new company creation. End of command.");
        return page;
    }
}