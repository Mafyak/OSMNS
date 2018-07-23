package by.epam.command;

import by.epam.exception.ServiceException;
import by.epam.service.ConfigManager;
import by.epam.entity.Page;
import by.epam.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class RegisterCommand implements Command {

    private static final String PARAM_NAME_LOGIN = "regLogin";
    private static final String PARAM_NAME_PASSWORD = "regPassword";
    private static final String PARAM_NAME_FIRST_NAME = "fName";
    private static final String PARAM_NAME_LAST_NAME = "lName";

    public Page execute(HttpServletRequest request) {
        Page page = null;
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
        String fName = request.getParameter(PARAM_NAME_FIRST_NAME);
        String mName = "";
        String lName = request.getParameter(PARAM_NAME_LAST_NAME);

        UserService userService = new UserService();
        HttpSession session = request.getSession();
        try {
            userService.register(login, pass, fName, mName, lName);
            session.setAttribute("infoMessage", ConfigManager.message("cmd.reg.success"));
            page = new Page(ConfigManager.getProperty("path.page.login"), true);
        } catch (ServiceException e) {
            session.setAttribute("infoMessage", ConfigManager.message("cmd.reg.failure"));
            page = new Page(ConfigManager.getProperty("path.page.login"), true);
        }
        return page;
    }
}
