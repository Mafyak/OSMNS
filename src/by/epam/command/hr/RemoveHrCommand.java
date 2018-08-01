package by.epam.command.hr;

import by.epam.command.Command;
import by.epam.entity.Page;
import by.epam.exception.ServiceException;
import by.epam.utils.service.AdminService;

import javax.servlet.http.HttpServletRequest;

public class RemoveHrCommand implements Command {
    @Override
    public Page execute(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("hrIdToRemove"));

        AdminService adminService = new AdminService();
        try {
            adminService.removeHrById(id);
        } catch (ServiceException e) {

        }

        return new ShowHrByName().execute(request);
    }
}
