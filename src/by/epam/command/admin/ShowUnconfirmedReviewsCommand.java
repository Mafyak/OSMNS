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

public class ShowUnconfirmedReviewsCommand implements Command {


    @Override
    public Page execute(HttpServletRequest request) {

        AdminService adminService = new AdminService();
        List<UserHistory> userHistories = null;
        HttpSession session = request.getSession();
        session.removeAttribute("hrList");
        session.removeAttribute("reviewsList");
        session.removeAttribute("companyNameCollisions");
        try {
            userHistories = adminService.getUnconfirmedReviews();
            session.setAttribute("unconfirmedReviewsList", userHistories);
        } catch (ServiceException e) {
            session.setAttribute("ShowUnconfReviewsError", ConfigManager.message("cmd.adm.noUncRev"));
        }
        return new Page(ConfigManager.getProperty("path.page.admin"), true);
    }
}
