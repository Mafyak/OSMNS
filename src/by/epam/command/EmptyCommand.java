package by.epam.command;

import javax.servlet.http.HttpServletRequest;
import java.util.ResourceBundle;

public class EmptyCommand implements Command {

    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("resources/config");

    public String execute(HttpServletRequest request) {
        return resourceBundle.getString("path.page.index");
    }
}
