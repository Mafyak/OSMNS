package by.epam.command.hr;

import by.epam.command.Command;
import by.epam.exception.ServiceException;
import by.epam.utils.manager.Manager;
import by.epam.entity.Page;
import by.epam.entity.User;
import by.epam.entity.UserHistory;
import by.epam.utils.session.SessionCleaner;
import by.epam.utils.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.util.Calendar;
import java.util.Locale;

public class AddNewReviewCommand implements Command {

    private static final Logger LOG = Logger.getLogger(AddNewReviewCommand.class);
    private static final String Y_EMPLOYED = "yEmployed";
    private static final String Y_FIRED = "yFired";
    private static final String RATING1 = "rating1";
    private static final String RATING2 = "rating2";
    private static final String RATING3 = "rating3";
    private static final String RATING4 = "rating4";
    private static final String RATING5 = "rating5";
    private static final String HIRE_AGAIN = "hireAgain";

    @Override
    public Page execute(HttpServletRequest request) {
        Page page = new Page(Manager.getMan().getPage("hr_main_page"), true);
        HttpSession session = request.getSession();

        User currentHR = (User) session.getAttribute("user");
        User employee = (User) session.getAttribute("employee");
        Locale locale = (Locale) Config.get(request.getSession(), Config.FMT_LOCALE);
        if (locale == null)
            locale = Locale.getDefault();

        if (employee == null) {
            session.setAttribute("infoMessage", Manager.getMan().message("msg.error.emptEmpl", locale));
            return page;
        }

        if (currentHR.getCompany().getName() == null) {
            session.setAttribute("infoMessage", Manager.getMan().message("msg.error.emptComp", locale));
            return page;
        }

        UserHistory userHistory = new UserHistory();
        userHistory.setIdCompany(currentHR.getCompany().getId());
        userHistory.setIdOfficialCompany(currentHR.getCompany().getCompanyOfficialId());
        userHistory.setYearEmployed(Integer.parseInt(request.getParameter(Y_EMPLOYED)));
        userHistory.setYearTerminated(Integer.parseInt(request.getParameter(Y_FIRED)));
        userHistory.setRating1(Integer.parseInt(request.getParameter(RATING1)));
        userHistory.setRating2(Integer.parseInt(request.getParameter(RATING2)));
        userHistory.setRating3(Integer.parseInt(request.getParameter(RATING3)));
        userHistory.setRating4(Integer.parseInt(request.getParameter(RATING4)));
        userHistory.setRating5(Integer.parseInt(request.getParameter(RATING5)));
        userHistory.setHireAgain((request.getParameter(HIRE_AGAIN).equalsIgnoreCase("Yes") ? 1 : 0));

        Calendar now = Calendar.getInstance();   // Gets the current date and time
        int year = now.get(Calendar.YEAR);       // The current year
        LOG.info("current year" + year);
        if(userHistory.getYearTerminated()>year){
            session.setAttribute("infoMessage", "Wrong termination year");
            return page;
        }

        UserService userService = new UserService();
        SessionCleaner sessionCleaner = new SessionCleaner();
        sessionCleaner.cleanSession(session);

        LOG.info("\nReview data: user:" + currentHR + ",\nemployee: " + employee + ",\nreview data: " + userHistory);
        try {
            userService.addReview(currentHR, employee, userHistory);
            session.setAttribute("infoMessage", Manager.getMan().message("cmd.review.newReview"));
        } catch (ServiceException | NullPointerException e) {
            session.setAttribute("infoMessage", Manager.getMan().message("msg.error.processing"));
            LOG.info("Error during new review creation: " + e);
        }
        LOG.info("Processing new company creation. End of command.");
        return page;
    }
}