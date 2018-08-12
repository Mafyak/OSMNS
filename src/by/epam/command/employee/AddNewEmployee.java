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
import java.util.ResourceBundle;

public class AddNewEmployee implements Command {

    private static final Logger LOG = Logger.getLogger(AddNewEmployee.class);
    private static final ResourceBundle langBundle = ResourceBundle.getBundle("resources/content");

    @Override
    public Page execute(HttpServletRequest request) {
        Page page = new Page(Manager.getProperty("mainPage"), true);

        HttpSession session = request.getSession();
        User currentHR = (User) session.getAttribute("user");
        String fName = request.getParameter("empFName");
        String mName = request.getParameter("empMName");
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
            request.setAttribute("infoAddingEmpl",
                    langBundle.getString("hr.succ.addEmpl"));
        } catch (ServiceException e) {
            LOG.info("Error adding employee");
            request.setAttribute("infoAddingEmpl",
                    langBundle.getString("hr.err.addEmpl"));
        }

        session.setAttribute("user", currentHR);
        session.setAttribute("employee", employee);
        return page;
    }
}
