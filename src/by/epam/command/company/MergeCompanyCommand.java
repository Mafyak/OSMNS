package by.epam.command.company;

import by.epam.command.Command;
import by.epam.entity.Page;
import by.epam.exception.ServiceException;
import by.epam.utils.service.CompanyService;
import by.epam.utils.manager.Manager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

import org.apache.log4j.Logger;

import java.util.Locale;

/**
 * Merge company command. In case of company data collision such as - user enters proper company name but makes a mistake
 * in tax id, merge company command allow administrator to merge both companies into one, uniting the rest of the info.
 *
 * @author Siarhei Huba.
 */
public class MergeCompanyCommand implements Command {
    private static final Logger LOG = Logger.getLogger(MergeCompanyCommand.class);

    @Override
    public Page execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Locale locale = (Locale) Config.get(session, Config.FMT_LOCALE);
        int companyId = Integer.parseInt(request.getParameter("companyIdtoMerge"));
        CompanyService companyService = new CompanyService();
        try {
            companyService.mergeCompByInnerId(companyId);
        } catch (ServiceException e) {
            LOG.info("Can't merge companies.");
            session.setAttribute("ShowCompanyNameCollisionssError",
                    Manager.getMan().message("msg.error.processing", locale));
        }
        return new ShowCompNameCollisionCommand().execute(request);
    }
}

