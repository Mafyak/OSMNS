package by.epam.command.hr;

import by.epam.command.Command;
import by.epam.entity.Page;
import by.epam.exception.ServiceException;
import by.epam.utils.manager.Manager;
import by.epam.utils.service.AdminService;

import javax.servlet.http.HttpServletRequest;

public class RemoveHrCommand implements Command {
    @Override
    public Page execute(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("hrIdToRemove"));

        AdminService adminService = new AdminService();
        try {
            adminService.removeHrById(id);
            request.setAttribute("infoSearcdDelHRMessage", Manager.getMan().message("done"));
        } catch (ServiceException e) {
            request.setAttribute("infoSearcdDelHRMessage", Manager.getMan().message("msg.error.processing"));
        }
        return new ShowHrByName().execute(request);
    }
}
