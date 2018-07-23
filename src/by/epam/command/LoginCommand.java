package by.epam.command;

import by.epam.service.ConfigManager;
import by.epam.entity.Page;
import by.epam.entity.User;
import by.epam.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

public class LoginCommand implements Command {
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final Logger LOG = Logger.getLogger("LoginCommand");

    public Page execute(HttpServletRequest request) {

        Page page = null;
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);

        UserService userService = new UserService();
        try {
            User user = userService.login(login, pass);
            request.getSession().setAttribute("user", user);
            LOG.info("Check 5");
            page = new Page(getProperPage(user), true);
        } catch (Exception e) {
            request.setAttribute("errorLoginPassMessage", ConfigManager.message("cmd.login.error"));

            page = new Page(ConfigManager.getProperty("path.page.index"));
        }
        return page;
    }

    private String getProperPage(User user) {
        switch (user.getType()) {
            case HR:
                return ConfigManager.getProperty("mainPage");
            case ADMIN:
                return ConfigManager.getProperty("path.page.admin");
            default:
                return ConfigManager.getProperty("path.page.index");
        }
    }
}

