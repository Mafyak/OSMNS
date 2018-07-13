package by.epam.command.user;

import by.epam.command.Command;
import by.epam.entity.User;
import by.epam.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ShowBySSNCommand implements Command{

    Logger LOG = Logger.getLogger("ShowBySSNCommand");
    private ResourceBundle resourceBundle = ResourceBundle.getBundle("resources/config");
    private static final String SSN = "SSN";

    @Override
    public String execute(HttpServletRequest request) {
        String page = resourceBundle.getString("mainPage");
        String employeeSSN = request.getParameter(SSN);
        UserService userService = new UserService();
        User employee = userService.getUserBySSN(Integer.parseInt(employeeSSN));
        LOG.info("employee: " + employee);
        request.setAttribute("employee", employee);
        return page;
    }
}























