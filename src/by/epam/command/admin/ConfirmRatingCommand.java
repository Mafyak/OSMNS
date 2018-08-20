package by.epam.command.admin;

import by.epam.command.Command;
import by.epam.exception.ServiceException;
import by.epam.utils.manager.Manager;
import by.epam.entity.Page;
import by.epam.utils.service.AdminService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.jstl.core.Config;
import java.util.Locale;

/**
 * Command to confirm rating.
 *
 * @author Siarhei Huba
 */
public class ConfirmRatingCommand implements Command {

    @Override
    public Page execute(HttpServletRequest request) {

        int adminID = Integer.parseInt(request.getParameter("confirmerid"));
        int ratingIdToConfirm = Integer.parseInt(request.getParameter("ratingidtoconfirm"));
        Locale locale = (Locale) Config.get(request.getSession(), Config.FMT_LOCALE);

        AdminService adminService = new AdminService();
        try {
            adminService.confirmRating(ratingIdToConfirm, adminID);
        } catch (ServiceException e) {
            request.setAttribute("infoMessage", Manager.getMan().message("cmd.review.cantConfirm", locale));
        }

        return new ShowUnconfirmedReviewsCommand().execute(request);
    }
}
