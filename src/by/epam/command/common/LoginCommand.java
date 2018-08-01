package by.epam.command.common;

import by.epam.command.Command;
import by.epam.exception.ServiceException;
import by.epam.utils.manager.Manager;
import by.epam.entity.Page;
import by.epam.entity.User;
import by.epam.utils.service.UserService;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class LoginCommand implements Command {
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final Logger LOG = Logger.getLogger("LoginCommand");

    public Page execute(HttpServletRequest request) {

        Page page;
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
        UserService userService = new UserService();
        try {
            User user = userService.login(login, pass);
            request.getSession().setAttribute("user", user);
            page = new Page(getProperPage(user), true);
        } catch (ServiceException e) {
            request.setAttribute("errorLoginPassMessage", Manager.message("cmd.login.error"));
            page = new Page(Manager.getProperty("path.page.index"));
        }
        return page;
    }

    private String getProperPage(User user) {
        switch (user.getType()) {
            case HR:
                return Manager.getProperty("mainPage");
            case ADMIN:
                return Manager.getProperty("path.page.admin");
            default:
                return Manager.getProperty("path.page.index");
        }
    }
}

