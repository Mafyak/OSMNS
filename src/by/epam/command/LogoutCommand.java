package by.epam.command;

import javax.servlet.http.HttpServletRequest;
import java.util.ResourceBundle;

public class LogoutCommand implements Command {

    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("resources/config");
    public String execute(HttpServletRequest request) {
        String page = resourceBundle.getString("path.page.index");
        request.getSession().invalidate();
        return page;
    }
}