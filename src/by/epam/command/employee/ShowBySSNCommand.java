package by.epam.command.employee;

import by.epam.command.Command;
import by.epam.entity.UserHistory;
import by.epam.exception.ServiceException;
import by.epam.utils.manager.Manager;
import by.epam.entity.Page;
import by.epam.entity.User;
import by.epam.utils.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import java.util.Comparator;

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

        try{
            employee.getHistory().sort(
                    (UserHistory o1, UserHistory o2)->(o1.getYearEmployed() < o2.getYearEmployed()) ? 1 : 0);
        } catch (NullPointerException e){
            LOG.info("No history for employee: " + employee);
        }
        session.setAttribute("employee", employee);
        return page;
    }
}