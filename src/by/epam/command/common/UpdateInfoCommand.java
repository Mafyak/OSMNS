package by.epam.command.common;

import by.epam.command.Command;
import by.epam.entity.Company;
import by.epam.entity.Page;
import by.epam.entity.User;
import by.epam.exception.ServiceException;
import by.epam.utils.service.*;

import javax.servlet.http.HttpServletRequest;

import by.epam.utils.manager.Manager;
import org.apache.log4j.Logger;

public class UpdateInfoCommand implements Command {

    private static final String PARAM_FIRST_NAME = "fName";
    private static final String PARAM_LAST_NAME = "lName";
    private static final String PARAM_EMAIL = "email";
    private static final String PARAM_PASSWORD = "pass";
    private static final String PARAM_COMP_OFF_ID = "cOffId";
    private static final Logger LOG = Logger.getLogger("LoginCommand");

    public Page execute(HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");
        LOG.info("Current user: " + user.toString());
        String fName = request.getParameter(PARAM_FIRST_NAME);
        String lName = request.getParameter(PARAM_LAST_NAME);
        String email = request.getParameter(PARAM_EMAIL);
        int cOffId = Integer.parseInt(request.getParameter(PARAM_COMP_OFF_ID));
        //String pass = request.getParameter(PARAM_PASSWORD);
        //Security security = new Security();
        //user.setPass(security.encryptData(pass));

        user.setfName(fName);
        user.setlName(lName);
        user.setEmail(email);
        UserService userService = new UserService();
        CompanyService companyService = new CompanyService();
        try {
            Company company = companyService.getCompanyByOffId(cOffId);
            user.setCompany(company);
            userService.updateUserInfo(user);

            request.getSession().setAttribute("user", user);
        } catch (ServiceException e) {
            LOG.info("DAO Exception during updateUserInfo method in UserDAO" + e);
            request.getSession().setAttribute("errorMessage", Manager.message("msg.error.processing"));
        }
        return new Page(Manager.getProperty("path.page.settings"));
    }
}