package by.epam.command.admin;

import by.epam.command.Command;
import by.epam.exception.ServiceException;
import by.epam.service.ConfigManager;
import by.epam.entity.Page;
import by.epam.entity.UserHistory;
import by.epam.service.AdminService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowAllReviewsCommand implements Command {


    @Override
    public Page execute(HttpServletRequest request) {

        AdminService adminService = new AdminService();
        List<UserHistory> userHistories = null;
        HttpSession session = request.getSession();
        session.removeAttribute("hrList");
        session.removeAttribute("unconfirmedReviewsList");
        session.removeAttribute("companyNameCollisions");
        try {
            userHistories = adminService.getAllReviews();
        } catch (ServiceException e) {
            request.setAttribute("ShowAllReviewsError", ConfigManager.message("msg.error.processing"));
        }
        session.setAttribute("reviewsList", userHistories);
        return new Page(ConfigManager.getProperty("path.page.admin"), true);
    }
}
