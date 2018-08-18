package by.epam.command.common;

import by.epam.command.Command;
import by.epam.exception.ServiceException;
import by.epam.utils.manager.Manager;
import by.epam.entity.Page;
import by.epam.entity.User;
import by.epam.utils.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class LoginCommand implements Command {
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final Logger LOG = Logger.getLogger(LoginCommand.class);

    public Page execute(HttpServletRequest request) {

        Page page;
        HttpSession session = request.getSession();
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
        UserService userService = new UserService();
        try {
            LOG.info("Login: " + login + ", pass: " + pass);
            User user = userService.login(login, pass);
            request.getSession().setAttribute("user", user);
            LOG.info("User info: " + user);
            if (user.getId() == 0) {
                LOG.info("Invalid user/password combination for user: " + login);
                throw new ServiceException("Empty user");
            }
            page = new Page(getProperPage(user), true);
        } catch (ServiceException e) {
            session.setAttribute("infoMessage", Manager.getMan().message("cmd.login.error"));
            page = new Page(Manager.getMan().getPage("index_page"), true);
        }
        return page;
    }

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

