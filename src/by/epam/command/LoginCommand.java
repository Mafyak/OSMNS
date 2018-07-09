package by.epam.command;

import by.epam.entity.User;
import by.epam.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.ResourceBundle;

public class LoginCommand implements Command {
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("resources/config");

    public String execute(HttpServletRequest request) {
        String page = null;
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);

        UserService userService = new UserService();
        try {
            User user = userService.login(login, pass);
            request.setAttribute("user", user);
            page = getProperPage(user);
        } catch (Exception e) {
            request.setAttribute("errorLoginPassMessage", "Incorrect login or password.");
            page = resourceBundle.getString("path.page.index");
        }
        return page;
    }

    private String getProperPage(User user) {
        switch (user.getType()) {
            case HR:
                return resourceBundle.getString("mainPage");
            case ADMIN:
                return resourceBundle.getString("path.page.admin");
            default:
                return resourceBundle.getString("path.page.index");
        }
    }
}

