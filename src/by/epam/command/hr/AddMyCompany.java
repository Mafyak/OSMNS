package by.epam.command.hr;

import by.epam.command.Command;
import by.epam.exception.ServiceException;
import by.epam.utils.service.CompanyService;
import by.epam.utils.manager.Manager;
import by.epam.entity.Company;
import by.epam.entity.Page;
import by.epam.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AddMyCompany implements Command {
    private static final Logger LOG = Logger.getLogger(AddMyCompany.class);
    private static final String COMPANY_NAME = "companyName";
    private static final String NICHE = "niche";
    private static final String LOCATION = "location";
    private static final String HEADCOUNT = "headcount";
    private static final String COMPANY_OFF_ID = "companyOffId";

    @Override
    public Page execute(HttpServletRequest request) {
        Page page = new Page(Manager.getProperty("mainPage"), true);
        HttpSession session = request.getSession();

        User currentUser = (User) session.getAttribute("user");
        Company myCompany = new Company();

        myCompany.setName(request.getParameter(COMPANY_NAME));
        myCompany.setNiche(request.getParameter(NICHE));
        myCompany.setLocation(request.getParameter(LOCATION));
        myCompany.setHeadcount(Integer.parseInt(request.getParameter(HEADCOUNT)));
        myCompany.setCompanyOfficialId(Integer.parseInt(request.getParameter(COMPANY_OFF_ID)));
        CompanyService companyService = new CompanyService();
        LOG.info("Processing new COMPANY_NAME creation: " + myCompany.toString() + ". Passing to service.");
        try {
            companyService.addCompany(myCompany);
            currentUser.setCompany(myCompany);
        } catch (ServiceException e) {
            request.setAttribute("addMyCompanyError", Manager.message("msg.error.processing"));
            LOG.info("Error during addMyCompany method: " + e);
        }
        LOG.info("Processing new COMPANY_NAME creation. End of command.");
        return page;
    }
}
