package by.epam.command.admin;

import by.epam.command.Command;
import by.epam.exception.ServiceException;
import by.epam.service.ConfigManager;
import by.epam.entity.Page;
import by.epam.service.AdminService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DeleteRatingCommand implements Command {

    private static Logger LOG = Logger.getLogger("DeleteRatingCommand");

    @Override
    public Page execute(HttpServletRequest request) {

        int ratingID = Integer.parseInt(request.getParameter("ratingidtodelete"));

        AdminService adminService = new AdminService();
        try {
            adminService.deleteReview(ratingID);
        } catch (ServiceException e) {
            request.setAttribute("infoMessage", ConfigManager.message("msg.error.processing"));
            LOG.info("Error in DeleteRatingCommand" + e);
        }

        return new Page(ConfigManager.getProperty("path.page.admin"), true);
    }
}
