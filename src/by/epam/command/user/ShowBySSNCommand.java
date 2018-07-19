package by.epam.command.user;

import by.epam.command.Command;
import by.epam.config.ConfigurationManager;
import by.epam.entity.Page;
import by.epam.entity.User;
import by.epam.exception.DAOException;
import by.epam.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

public class ShowBySSNCommand implements Command {

    private static Logger LOG = Logger.getLogger("ShowBySSNCommand");
    private static final String SSN = "SSN";

    @Override
    public Page execute(HttpServletRequest request) {
        Page page = new Page(ConfigurationManager.getProperty("path.page.emplProfile"), true);
        String employeeSSN = request.getParameter(SSN);
        UserService userService = new UserService();
        User employee = null;
        try {
            employee = userService.getUserBySSN(Integer.parseInt(employeeSSN));
        } catch (DAOException e) {
            LOG.info("Error while getting data");
            request.setAttribute("errorShowBySsnMessage", "No data for this SSN, sorry.");
        }
        LOG.info("employee: " + employee);
        request.setAttribute("employee", employee);
        return page;
    }
}























