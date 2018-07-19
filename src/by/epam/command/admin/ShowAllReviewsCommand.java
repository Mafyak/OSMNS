package by.epam.command.admin;

import by.epam.command.Command;
import by.epam.config.ConfigurationManager;
import by.epam.entity.Page;
import by.epam.entity.UserHistory;
import by.epam.service.AdminService;
import by.epam.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ShowAllReviewsCommand implements Command {


    @Override
    public Page execute(HttpServletRequest request) {

        AdminService adminService = new AdminService();
        List<UserHistory> userHistories = null;
        try {
            userHistories = adminService.getAllReviews();
            request.setAttribute("reviewsList", userHistories);
        } catch (SQLException e) {
            request.setAttribute("infoMessage", "Error getting your information");
        }
        return new Page(ConfigurationManager.getProperty("path.page.admin"));
    }
}
