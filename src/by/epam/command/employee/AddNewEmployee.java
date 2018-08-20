package by.epam.command.employee;

import by.epam.command.Command;
import by.epam.entity.Page;
import by.epam.entity.User;
import by.epam.exception.ServiceException;
import by.epam.utils.manager.Manager;
import by.epam.utils.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
/**
 * This command adds new employee to existing database.
 *
 * @author Siarhei Huba.
 */
public class AddNewEmployee implements Command {

    private static final Logger LOG = Logger.getLogger(AddNewEmployee.class);

    @Override
    public Page execute(HttpServletRequest request) {
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Page page = new Page(Manager.getMan().getPage("hr_main_page"), true);

        HttpSession session = request.getSession();
        Locale locale = (Locale) Config.get(session, Config.FMT_LOCALE);
        session.removeAttribute("infoAddingEmpl");
        User currentHR = (User) session.getAttribute("user");
        String fName = request.getParameter("empFName");
        String mName = request.getParameter("empMName");
        LOG.info("name: " + fName);
        String lName = request.getParameter("empLName");
        int ssn = Integer.parseInt(request.getParameter("empSSN"));
        User employee = new User();

        employee.setfName(fName);
        employee.setmName(mName);
        employee.setlName(lName);
        employee.setSSN(ssn);

        UserService userService = new UserService();
        try {
            userService.addNewEmployee(employee);
            session.setAttribute("infoAddingEmpl", Manager.getMan().message("hr.succ.addEmpl", locale));
        } catch (ServiceException e) {
            LOG.info("Error adding employee");
            session.setAttribute("infoAddingEmpl", Manager.getMan().message("hr.err.addEmpl", locale));
        }

        session.setAttribute("user", currentHR);
        session.setAttribute("employee", employee);
        return page;
    }
}
