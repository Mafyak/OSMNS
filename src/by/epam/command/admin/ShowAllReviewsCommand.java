package by.epam.command.admin;

import by.epam.command.Command;
import by.epam.exception.ServiceException;
import by.epam.utils.manager.Manager;
import by.epam.entity.Page;
import by.epam.entity.UserHistory;
import by.epam.utils.service.AdminService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowAllReviewsCommand implements Command {

    private static final Logger LOG = Logger.getLogger(ShowAllReviewsCommand.class);

    @Override
    public Page execute(HttpServletRequest request) {

        AdminService adminService = new AdminService();
        List<UserHistory> userHistories = null;
        HttpSession session = request.getSession();
        int index;

        try {
            index = Integer.parseInt(request.getParameter("page"));
        } catch (NumberFormatException e) {
            index = (Integer) session.getAttribute("index");
        }

        LOG.info("index:" + index);
        session.setAttribute("index", index);

        session.removeAttribute("hrList");
        session.removeAttribute("unconfirmedReviewsList");
        session.removeAttribute("companyNameCollisions");
        try {
            Long pages = adminService.getReviewsPoolSize();
            Long size = pages / 10;
            if (size % pages > 0)
                size++;
            userHistories = adminService.getPagedReviews(index);
            session.setAttribute("pages", size);
        } catch (ServiceException e) {
            request.setAttribute("ShowAllReviewsError", Manager.getMan().message("msg.error.processing"));
        }
        session.setAttribute("reviewsList", userHistories);
        return new Page(Manager.getMan().getPage("admin_page"), true);
    }
}
