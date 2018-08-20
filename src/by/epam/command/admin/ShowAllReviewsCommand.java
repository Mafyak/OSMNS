package by.epam.command.admin;

import by.epam.command.Command;
import by.epam.exception.ServiceException;
import by.epam.utils.manager.Manager;
import by.epam.entity.Page;
import by.epam.entity.UserHistory;
import by.epam.utils.service.AdminService;
import by.epam.utils.session.SessionCleaner;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.util.List;
import java.util.Locale;

/**
 * Command to show all reviews.
 *
 * @author Siarhei Huba
 */
public class ShowAllReviewsCommand implements Command {

    private static final Logger LOG = Logger.getLogger(ShowAllReviewsCommand.class);

    @Override
    public Page execute(HttpServletRequest request) {

        AdminService adminService = new AdminService();
        List<UserHistory> userHistories = null;
        HttpSession session = request.getSession();
        Locale locale = (Locale) Config.get(session, Config.FMT_LOCALE);
        int index;

        try {
            index = Integer.parseInt(request.getParameter("page"));
        } catch (NumberFormatException e) {
            Object ind = session.getAttribute("index");
            index = (ind == null) ? 1 : (int) ind;
        }

        LOG.info("index:" + index);
        session.setAttribute("index", index);
        SessionCleaner.getCleaner().cleanSession(session);

        try {
            Long pages = adminService.getReviewsPoolSize();
            Long size = pages / 10;
            if (size % pages > 0)
                size++;
            userHistories = adminService.getPagedReviews(index);
            session.setAttribute("pages", size);
        } catch (ServiceException e) {
            request.setAttribute("ShowAllReviewsError", Manager.getMan().message("msg.error.processing", locale));
        }
        session.setAttribute("reviewsList", userHistories);
        //return new GoToPageCommand().execute(request);
        return new Page(Manager.getMan().getPage("admin_page"), true);
    }
}
