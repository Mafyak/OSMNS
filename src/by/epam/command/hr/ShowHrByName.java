package by.epam.command.hr;

import by.epam.command.Command;
import by.epam.config.ConfigurationManager;
import by.epam.entity.Page;
import by.epam.entity.User;
import by.epam.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ShowHrByName implements Command {

    @Override
    public Page execute(HttpServletRequest request) {
        String fName = request.getParameter("fName");
        String lName = request.getParameter("lName");
        UserService userService = new UserService();
        List<User> hrList = null;
        try {
            hrList = userService.getHrByName(fName, lName);
        } catch (SQLException e) {
            request.setAttribute("infoMessage", "Error while getting requested information.");
        }
        request.setAttribute("hrList", hrList);

        return new Page(ConfigurationManager.getProperty("path.page.admin"));
    }
}
