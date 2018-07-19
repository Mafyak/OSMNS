package by.epam.command.admin;

import by.epam.command.Command;
import by.epam.config.ConfigurationManager;
import by.epam.entity.Page;
import by.epam.service.AdminService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConfirmRatingCommand implements Command {


    @Override
    public Page execute(HttpServletRequest request) {

        int adminID = Integer.parseInt(request.getParameter("confirmerid"));
        int ratingIdToConfirm = Integer.parseInt(request.getParameter("ratingidtoconfirm"));

        AdminService adminService = new AdminService();
        try {
            adminService.confirmRating(ratingIdToConfirm, adminID);

        } catch (SQLException e) {
            request.setAttribute("infoMessage", "Can't confirm review.");
        }
        return new Page(ConfigurationManager.getProperty("path.page.admin"), false);
    }
}
