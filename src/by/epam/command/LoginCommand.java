package by.epam.command;

import by.epam.config.ConfigurationManager;
import by.epam.entity.Page;
import by.epam.entity.User;
import by.epam.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements Command {
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";

    public Page execute(HttpServletRequest request) {

        Page page = null;
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);

        UserService userService = new UserService();
        try {
            User user = userService.login(login, pass);
            request.getSession().setAttribute("user", user);
            page = new Page(getProperPage(user), true);
        } catch (Exception e) {
            request.setAttribute("errorLoginPassMessage", "Incorrect login or password.");

            page = new Page(ConfigurationManager.getProperty("path.page.index"));
        }
        return page;
    }

    private String getProperPage(User user) {
        switch (user.getType()) {
            case HR:
                return ConfigurationManager.getProperty("mainPage");
            case ADMIN:
                return ConfigurationManager.getProperty("path.page.admin");
            default:
                return ConfigurationManager.getProperty("path.page.index");
        }
    }
}

