package by.epam.command.company;

import by.epam.command.Command;
import by.epam.entity.Company;
import by.epam.entity.Page;
import by.epam.exception.ServiceException;
import by.epam.utils.service.CompanyService;
import by.epam.utils.manager.Manager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;

public class ShowCompNameCollisionCommand implements Command {

    private static final Logger LOG = Logger.getLogger(ShowCompNameCollisionCommand.class);

    @Override
    public Page execute(HttpServletRequest request) {

        HttpSession session = request.getSession();
        Locale locale = (Locale) Config.get(session, Config.FMT_LOCALE);
        CompanyService companyService = new CompanyService();
        List<Company> list = null;
        session.removeAttribute("hrList");
        session.removeAttribute("reviewsList");
        session.removeAttribute("unconfirmedReviewsList");

        try {
            list = companyService.getCompNameCollisions();
        } catch (ServiceException e) {
            LOG.info("Error while getting company name collisions." + e);
            session.setAttribute("ShowCompanyNameCollisionssError", Manager.getMan().message("adm.noCollis", locale));
        }
        session.setAttribute("companyNameCollisions", list);

        return new Page(Manager.getMan().getPage("admin_page"), true);
    }
}
