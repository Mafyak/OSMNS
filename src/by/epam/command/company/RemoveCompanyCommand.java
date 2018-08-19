package by.epam.command.company;

import by.epam.command.Command;
import by.epam.entity.Page;
import by.epam.exception.ServiceException;
import by.epam.utils.service.CompanyService;
import by.epam.utils.manager.Manager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.util.Locale;

public class RemoveCompanyCommand implements Command {

    @Override
    public Page execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Locale locale = (Locale) Config.get(session, Config.FMT_LOCALE);
        int companyId = Integer.parseInt(request.getParameter("companyIdtoRemove"));
        CompanyService companyService = new CompanyService();
        try {
            companyService.removeCompByInnerId(companyId);
        } catch (ServiceException e) {
            session.setAttribute("ShowCompanyNameCollisionssError",
                    Manager.getMan().message("msg.error.processing", locale));
        }
        return new ShowCompNameCollisionCommand().execute(request);
    }
}
