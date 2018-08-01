package by.epam.command.company;

import by.epam.command.Command;
import by.epam.entity.Company;
import by.epam.entity.Page;
import by.epam.exception.ServiceException;
import by.epam.utils.service.CompanyService;
import by.epam.utils.manager.Manager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import org.apache.log4j.Logger;

public class ShowCompNameCollisionCommand implements Command {

    private static final Logger LOG = Logger.getLogger(ShowCompNameCollisionCommand.class);

    @Override
    public Page execute(HttpServletRequest request) {

        HttpSession session = request.getSession();
        CompanyService companyService = new CompanyService();
        List<Company> list = null;
        session.removeAttribute("hrList");
        session.removeAttribute("reviewsList");
        session.removeAttribute("unconfirmedReviewsList");

        try {
            list = companyService.getCompNameCollisions();
        } catch (ServiceException e) {
            LOG.info("Error while getting company name collisions." + e);
            session.setAttribute("ShowCompanyNameCollisionssError", "Collisions are not found");
        }
        session.setAttribute("companyNameCollisions", list);

        return new Page(Manager.getProperty("path.page.admin"), true);
    }
}
