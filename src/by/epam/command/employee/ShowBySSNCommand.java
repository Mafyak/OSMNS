package by.epam.command.employee;

import by.epam.command.Command;
import by.epam.command.common.GoToPageCommand;
import by.epam.entity.UserHistory;
import by.epam.exception.ServiceException;
import by.epam.utils.manager.Manager;
import by.epam.entity.Page;
import by.epam.entity.User;
import by.epam.utils.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

import by.epam.utils.session.SessionCleaner;
import org.apache.log4j.Logger;

import java.util.Locale;
/**
 * Show by SSN command looks up for an employee and tries to find review of this employee.
 *
 * @author Siarhei Huba
 */
public class ShowBySSNCommand implements Command {

    private static final Logger LOG = Logger.getLogger(ShowBySSNCommand.class);
    private static final String SSN = "SSN";

    @Override
    public Page execute(HttpServletRequest request) {
        Page page = new Page(Manager.getMan().getPage("emp_profile_page"), true);
        String employeeSSN = request.getParameter(SSN);
        UserService userService = new UserService();
        User employee = null;
        HttpSession session = request.getSession();
        Locale locale = (Locale) Config.get(session, Config.FMT_LOCALE);
        SessionCleaner.getCleaner().cleanSession(session);

        try {
            employee = userService.getUserBySSN(Integer.parseInt(employeeSSN));
        } catch (ServiceException e) {
            LOG.info("Error while getting data");
            session.setAttribute("infoMessage",
                    Manager.getMan().message("cmd.ssn.noDataPerSSN", locale));
        }

        if (employee.getId() == 0) {
            LOG.info("Employee is empty");
            session.setAttribute("infoMessage",
                    Manager.getMan().message("cmd.ssn.noDataPerSSN", locale));
        }

        LOG.info("employee: " + employee);

        try {
            employee.getHistory().sort(
                    (UserHistory o1, UserHistory o2) -> (o1.getYearEmployed() < o2.getYearEmployed()) ? 1 : 0);
        } catch (NullPointerException e) {
            LOG.info("No history for employee: " + employee);
        }

        session.setAttribute("employee", employee);
        session.setAttribute("pageObj", page);
        //return new GoToPageCommand().execute(request);
        return page;
    }
}