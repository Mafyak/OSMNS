package by.epam.command.admin;

import by.epam.command.Command;
import by.epam.entity.Page;
import by.epam.exception.ServiceException;
import by.epam.service.AdminService;
import by.epam.service.ConfigManager;

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

        return new Page(ConfigManager.getProperty("path.page.admin"));
    }
}
