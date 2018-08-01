package by.epam.command.employee;

import by.epam.command.Command;
import by.epam.exception.ServiceException;
import by.epam.utils.manager.Manager;
import by.epam.entity.Page;
import by.epam.entity.User;
import by.epam.utils.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

public class ShowBySSNCommand implements Command {

    private static final Logger LOG = Logger.getLogger(ShowBySSNCommand.class);
    private static final String SSN = "SSN";

    @Override
    public Page execute(HttpServletRequest request) {
        Page page = new Page(Manager.getProperty("path.page.emplProfile"), true);
        String employeeSSN = request.getParameter(SSN);
        UserService userService = new UserService();
        User employee = null;
        try {
            employee = userService.getUserBySSN(Integer.parseInt(employeeSSN));
        } catch (ServiceException e) {
            LOG.info("Error while getting data");
            request.setAttribute("noDataPerSSN",
                    Manager.message("cmd.ssn.noDataPerSSN"));
        }
        LOG.info("employee: " + employee);
        HttpSession session = request.getSession();
        session.setAttribute("employee", employee);
        return page;
    }
}