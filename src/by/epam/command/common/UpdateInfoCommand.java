package by.epam.command.common;

import by.epam.command.Command;
import by.epam.entity.Company;
import by.epam.entity.Page;
import by.epam.entity.User;
import by.epam.entity.UserType;
import by.epam.exception.ServiceException;
import by.epam.utils.service.*;
import by.epam.utils.manager.Manager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

import org.apache.log4j.Logger;

import java.util.Locale;

public class UpdateInfoCommand implements Command {

    private static final String PARAM_FIRST_NAME = "fName";
    private static final String PARAM_LAST_NAME = "lName";
    private static final String PARAM_EMAIL = "email";
    //private static final String PARAM_PASSWORD = "pass";
    private static final String PARAM_COMP_OFF_ID = "cOffId";
    private static final Logger LOG = Logger.getLogger("LoginCommand");

    public Page execute(HttpServletRequest request) {

        HttpSession session = request.getSession();
        Locale locale = (Locale) Config.get(session, Config.FMT_LOCALE);
        LOG.info("locale: " + locale);
        User user = (User) session.getAttribute("user");
        String backupEmail = user.getEmail();
        String backupPass = user.getPass();
        UserType backupType = user.getType();
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
            LOG.info("Company is set to: " + company.getName());
            if (company.getName() == null)
                session.setAttribute("companyMessage", Manager.getMan().message("msg.noCompany", locale));
            userService.updateUserInfo(user);
            session.setAttribute("user", user);
            session.setAttribute("infoMessage", Manager.getMan().message("cmn.done", locale));
            LOG.info("Ready to forward");
        } catch (ServiceException e) {
            LOG.info("ServiceException Exception during updateUserInfo method in UpdateInfoCommand" + e);
            session.setAttribute("infoMessage", Manager.getMan().message("msg.error.processing", locale));
            try {
                LOG.info("user: " + user);
                user = userService.getHrById(user.getId());
                user.setEmail(backupEmail);
                user.setPass(backupPass);
                user.setType(backupType);
                LOG.info("user: " + user);
                session.setAttribute("user", user);
                session.setAttribute("page", Manager.getMan().getPage("settings_page"));
            } catch (ServiceException e1) {
                LOG.info("ServiceException Exception during user recover in UpdateInfoCommand" + e);
            }
        }
        return new Page(Manager.getMan().getPage("settings_page"), true);
    }
}