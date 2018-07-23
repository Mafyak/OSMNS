package by.epam.command.hr;

import by.epam.command.Command;
import by.epam.exception.ServiceException;
import by.epam.service.ConfigManager;
import by.epam.entity.Company;
import by.epam.entity.Page;
import by.epam.entity.User;
import by.epam.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.logging.Logger;

public class AddMyCompany implements Command {
    private static Logger LOG = Logger.getLogger("AddMyCompany");
    private static final String company = "companyName";
    private static final String niche = "niche";
    private static final String location = "location";
    private static final String headcount = "headcount";
    private static final String companyOffId = "companyOffId";

    @Override
    public Page execute(HttpServletRequest request) {
        Page page = new Page(ConfigManager.getProperty("mainPage"), true);
        HttpSession session = request.getSession();

        User currentUser = (User) session.getAttribute("user");
        Company myCompany = new Company();

        myCompany.setName(request.getParameter(company));
        myCompany.setNiche(request.getParameter(niche));
        myCompany.setLocation(request.getParameter(location));
        myCompany.setHeadcount(Integer.parseInt(request.getParameter(headcount)));
        myCompany.setCompanyOfficialId(Integer.parseInt(request.getParameter(companyOffId)));
        UserService userService = new UserService();
        LOG.info("Processing new company creation: " + myCompany.toString() + ". Passing to service.");
        try {
            userService.addMyCompany(currentUser, myCompany);
        } catch (ServiceException e) {
            request.setAttribute("addMyCompanyError", ConfigManager.message("msg.error.processing"));
            LOG.info("Error during addMyCompany method: " + e);
        }
        LOG.info("Processing new company creation. End of command.");
        return page;
    }
}