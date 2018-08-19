package by.epam.command.hr;

import by.epam.command.Command;
import by.epam.entity.Page;
import by.epam.exception.ServiceException;
import by.epam.utils.manager.Manager;
import by.epam.utils.service.AdminService;
import by.epam.utils.session.SessionCleaner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class RemoveHrCommand implements Command {
    @Override
    public Page execute(HttpServletRequest request) {

        HttpSession session = request.getSession();
        int id = Integer.parseInt(request.getParameter("hrIdToRemove"));
        SessionCleaner.getCleaner().cleanSession(session);
        AdminService adminService = new AdminService();
        try {
            adminService.removeHrById(id);
            session.setAttribute("infoSearcdDelHRMessage", Manager.getMan().message("done"));
        } catch (ServiceException e) {
            session.setAttribute("infoSearcdDelHRMessage", Manager.getMan().message("msg.error.processing"));
        }
        return new ShowHrByName().execute(request);
    }
}
