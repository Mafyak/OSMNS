package by.epam.command.admin;

import by.epam.command.Command;
import by.epam.exception.ServiceException;
import by.epam.utils.manager.Manager;
import by.epam.entity.Page;
import by.epam.entity.UserHistory;
import by.epam.utils.service.AdminService;
import by.epam.utils.session.SessionCleaner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.util.List;
import java.util.Locale;

/**
 * Command to show unconfirmed reviews only.
 *
 * @author Siarhei Huba
 */
public class ShowUnconfirmedReviewsCommand implements Command {

    @Override
    public Page execute(HttpServletRequest request) {

        AdminService adminService = new AdminService();
        List<UserHistory> userHistories;
        HttpSession session = request.getSession();
        Locale locale = (Locale) Config.get(session, Config.FMT_LOCALE);
        SessionCleaner.getCleaner().cleanSession(session);
        try {
            userHistories = adminService.getUnconfirmedReviews();
            session.setAttribute("unconfirmedReviewsList", userHistories);
        } catch (ServiceException e) {
            session.setAttribute("ShowUnconfReviewsError", Manager.getMan().message("cmd.adm.noUncRev", locale));
        }
        return new Page(Manager.getMan().getPage("admin_page"), true);
    }
}
