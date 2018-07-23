package by.epam.command.admin;

import by.epam.command.Command;
import by.epam.exception.ServiceException;
import by.epam.service.ConfigManager;
import by.epam.entity.Page;
import by.epam.service.AdminService;
import javax.servlet.http.HttpServletRequest;

public class ConfirmRatingCommand implements Command {


    @Override
    public Page execute(HttpServletRequest request) {

        int adminID = Integer.parseInt(request.getParameter("confirmerid"));
        int ratingIdToConfirm = Integer.parseInt(request.getParameter("ratingidtoconfirm"));

        AdminService adminService = new AdminService();
        try {
            adminService.confirmRating(ratingIdToConfirm, adminID);
        } catch (ServiceException e) {
            request.setAttribute("infoMessage", ConfigManager.message("cmd.review.cantConfirm"));
        }
        return new Page(ConfigManager.getProperty("path.page.admin"), false);
    }
}
