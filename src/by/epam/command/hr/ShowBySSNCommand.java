package by.epam.command.hr;

import by.epam.command.Command;
import by.epam.exception.ServiceException;
import by.epam.service.ConfigManager;
import by.epam.entity.Page;
import by.epam.entity.User;
import by.epam.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.logging.Logger;

public class ShowBySSNCommand implements Command {

    private static Logger LOG = Logger.getLogger("ShowBySSNCommand");
    private static final String SSN = "SSN";

    @Override
    public Page execute(HttpServletRequest request) {
        Page page = new Page(ConfigManager.getProperty("path.page.emplProfile"), true);
        String employeeSSN = request.getParameter(SSN);
        UserService userService = new UserService();
        User employee = null;
        try {
            employee = userService.getUserBySSN(Integer.parseInt(employeeSSN));
        } catch (ServiceException e) {
            LOG.info("Error while getting data");
            request.setAttribute("errorShowBySsnMessage",
                    ConfigManager.message("cmd.ssn.noDataPerSSN"));
        }
        LOG.info("employee: " + employee);
        HttpSession session = request.getSession();
        session.setAttribute("employee", employee);
        return page;
    }
}