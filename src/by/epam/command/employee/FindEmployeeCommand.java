package by.epam.command.employee;

import by.epam.command.Command;
import by.epam.command.common.GoToPageCommand;
import by.epam.entity.Page;
import by.epam.entity.User;
import by.epam.exception.ServiceException;
import by.epam.utils.manager.Manager;
import by.epam.utils.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

import by.epam.utils.session.SessionCleaner;
import org.apache.log4j.Logger;

import java.util.Locale;

public class FindEmployeeCommand implements Command {

    private static final Logger LOG = Logger.getLogger(FindEmployeeCommand.class);
    private static final String SSN = "SSN";

    @Override
    public Page execute(HttpServletRequest request) {
        Page pageObj = new Page(Manager.getMan().getPage("hr_main_page"), true);
        String employeeSSN = request.getParameter(SSN);
        boolean emptyDataFlag = false;

        UserService userService = new UserService();
        User employee = null;
        HttpSession session = request.getSession();
        Locale locale = (Locale) Config.get(session, Config.FMT_LOCALE);
        SessionCleaner.getCleaner().cleanSession(session);
        try {
            employee = userService.getEmployeeBySSN(Integer.parseInt(employeeSSN));
            emptyDataFlag = true;
        } catch (ServiceException e) {
            LOG.info("Error while getting data");
            session.setAttribute("emptyEmployee",
                    Manager.getMan().message("cmd.ssn.noDataPerSSN", locale));
        }
        LOG.info("employee: " + employee);
        if (employee.getSSN() == 0) {
            emptyDataFlag = false;
            session.setAttribute("emptyEmployee",
                    Manager.getMan().message("cmd.ssn.noDataPerSSN", locale));
        } else session.setAttribute("employee", employee);

       // session.setAttribute("pageObj", pageObj);
        session.setAttribute("emptyDataFlag", emptyDataFlag);
        return pageObj;
    }
}