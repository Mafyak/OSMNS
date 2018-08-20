package by.epam.command.common;

import by.epam.command.Command;
import by.epam.exception.ServiceException;
import by.epam.utils.manager.Manager;
import by.epam.entity.Page;
import by.epam.entity.User;
import by.epam.utils.service.ContentValidator;
import by.epam.utils.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import org.apache.log4j.Logger;
import java.util.Locale;
/**
 * Login command.
 *
 * @author Siarhei Huba
 */
public class LoginCommand implements Command {
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final Logger LOG = Logger.getLogger(LoginCommand.class);

    public Page execute(HttpServletRequest request) {

        Page pageObj;
        HttpSession session = request.getSession();
        session.removeAttribute("infoMessage");
        Locale locale = (Locale) Config.get(session, Config.FMT_LOCALE);
        if (locale == null) {
            locale = new Locale("en");
            Config.set(session, Config.FMT_LOCALE, locale);
        }
        LOG.info("Locale: " + locale);

        String login = request.getParameter(PARAM_NAME_LOGIN);
        if (!ContentValidator.validate(login)) {
            session.setAttribute("infoMessage", Manager.getMan().message("cmd.email.error", locale));
            pageObj = new Page(Manager.getMan().getPage("index_page"), true);
            return pageObj;
        }

        String pass = request.getParameter(PARAM_NAME_PASSWORD);
        UserService userService = new UserService();
        try {
            LOG.info("Login: " + login + ", pass: " + pass);
            User user = userService.login(login, pass);
            session.setAttribute("user", user);
            LOG.info("User info: " + user);
            if (user.getId() == 0) {
                LOG.info("Invalid user/password combination for user: " + login);
                throw new ServiceException("Empty user");
            }
            pageObj = new Page(getProperPage(user), true);
        } catch (ServiceException e) {
            session.setAttribute("infoMessage", Manager.getMan().message("cmd.login.error", locale));
            pageObj = new Page(Manager.getMan().getPage("index_page"), true);
        }
        request.setAttribute("pageObj", pageObj);
        return pageObj;
    }
    /**
     * Command for proper forward. Removes commonly used info attributes in order to provide clean pages.
     *
     * @return String type link representation based on user type(HR/admin) during login process.
     * @author Siarhei Huba
     */
    private String getProperPage(User user) {
        switch (user.getType()) {
            case HR:
                return Manager.getMan().getPage("hr_main_page");
            case ADMIN:
                return Manager.getMan().getPage("admin_page");
            default:
                return Manager.getMan().getPage("index_page");
        }
    }
}

