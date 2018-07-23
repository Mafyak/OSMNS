package by.epam.command.admin;

import by.epam.command.Command;
import by.epam.entity.Company;
import by.epam.entity.Page;
import by.epam.exception.ServiceException;
import by.epam.service.AdminService;
import by.epam.service.ConfigManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.logging.Logger;

public class ShowCompNameCollisionCommand implements Command {
    private static final Logger LOG = Logger.getLogger("ShowCompNameCollisionCommand");

    @Override
    public Page execute(HttpServletRequest request) {

        HttpSession session = request.getSession();
        AdminService adminService = new AdminService();
        List<Company> list = null;
        session.removeAttribute("hrList");
        session.removeAttribute("reviewsList");
        session.removeAttribute("unconfirmedReviewsList");

        try {
            list = adminService.getCompNameCollisions();
        } catch (ServiceException e) {
            LOG.info("Error while getting company name collisions." + e);
            session.setAttribute("ShowCompanyNameCollisionssError", "Collisions are not found");
        }
        session.setAttribute("companyNameCollisions", list);

        return new Page(ConfigManager.getProperty("path.page.admin"), true);
    }
}
