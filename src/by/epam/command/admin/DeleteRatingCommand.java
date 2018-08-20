package by.epam.command.admin;

import by.epam.command.Command;
import by.epam.exception.ServiceException;
import by.epam.utils.manager.Manager;
import by.epam.entity.Page;
import by.epam.utils.service.AdminService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.jstl.core.Config;
import java.util.Locale;
/**
 * Command to delete rating.
 *
 * @author Siarhei Huba
 */
public class DeleteRatingCommand implements Command {

    private static final Logger LOG = Logger.getLogger(DeleteRatingCommand.class);

    @Override
    public Page execute(HttpServletRequest request) {

        Locale locale = (Locale) Config.get(request.getSession(), Config.FMT_LOCALE);
        int ratingID = Integer.parseInt(request.getParameter("ratingidtodelete"));
        AdminService adminService = new AdminService();
        try {
            adminService.deleteReview(ratingID);
        } catch (ServiceException e) {
            request.setAttribute("infoMessage", Manager.getMan().message("msg.error.processing", locale));
            LOG.info("Error in DeleteRatingCommand" + e);
        }

        return new ShowAllReviewsCommand().execute(request);
    }
}
