package by.epam.command.hr;

import by.epam.command.Command;
import by.epam.exception.ServiceException;
import by.epam.service.ConfigManager;
import by.epam.entity.Page;
import by.epam.entity.User;
import by.epam.entity.UserHistory;
import by.epam.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.logging.Logger;

public class AddNewReviewCommand implements Command {

    private static Logger LOG = Logger.getLogger("AddNewReview");
    private static final String fName = "fName";
    private static final String lName = "lName";
    private static final String SSN = "SSN";
    private static final String cName = "cName";
    private static final String cId = "cId";
    private static final String yEmployed = "yEmployed";
    private static final String yFired = "yFired";
    private static final String rating1 = "rating1";
    private static final String rating2 = "rating2";
    private static final String rating3 = "rating3";
    private static final String rating4 = "rating4";
    private static final String rating5 = "rating5";
    private static final String hireAgain = "hireAgain";

    @Override
    public Page execute(HttpServletRequest request) {
        Page page = new Page(ConfigManager.getProperty("mainPage"), true);
        HttpSession session = request.getSession();

        User currentHR = (User) session.getAttribute("user");
        User newUser = new User();
        UserHistory userHistory = new UserHistory();

        newUser.setfName(request.getParameter(fName));
        newUser.setlName(request.getParameter(lName));
        newUser.setSSN(Integer.parseInt(request.getParameter(SSN)));

        userHistory.setCompany(request.getParameter(cName));
        userHistory.setIdOfficialCompany(Integer.parseInt(request.getParameter(cId)));
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
        LOG.info("Review data: user:" + currentHR + ", employee: " + newUser + ", review data: " + userHistory);
        try {
            userService.addReview(currentHR, newUser, userHistory);
            session.setAttribute("reviewAddResult", ConfigManager.message("cmd.review.newReview"));
        } catch (ServiceException e) {
            session.setAttribute("reviewAddResult", ConfigManager.message("msg.error.processing"));
            LOG.info("Error during new review creation: " + e);
        }
        LOG.info("Processing new company creation. End of command.");
        return page;
    }
}