package by.epam.command;

import by.epam.entity.User;
import by.epam.pool.ConnectionPool;
import by.epam.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class RegisterCommand implements Command {

    private static final String PARAM_NAME_LOGIN = "regLogin";
    private static final String PARAM_NAME_PASSWORD = "regPassword";
    private static final String PARAM_NAME_FIRST_NAME = "fName";
    private static final String PARAM_NAME_LAST_NAME = "lName";

    public String execute(HttpServletRequest request) {
        String page = null;
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
        String fName = request.getParameter(PARAM_NAME_FIRST_NAME);
        String mName = "";
        String lName = request.getParameter(PARAM_NAME_LAST_NAME);

        UserService userService = new UserService();
        boolean result = userService.register(login, pass, fName, mName, lName);

        if (result) {
            request.setAttribute("infoMessage", "Registration is successful. Please, sign on.");
            page = "/jsp/login.jsp";
        } else {
            request.setAttribute("infoMessage", "Registration failed. Please, try again.");
            page = "/jsp/login.jsp";
        }
        return page;
    }
}
