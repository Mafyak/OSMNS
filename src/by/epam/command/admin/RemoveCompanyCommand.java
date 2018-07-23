package by.epam.command.admin;

import by.epam.command.Command;
import by.epam.entity.Page;
import by.epam.exception.ServiceException;
import by.epam.service.AdminService;
import by.epam.service.ConfigManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class RemoveCompanyCommand implements Command {

    @Override
    public Page execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        int companyId = Integer.parseInt(request.getParameter("companyIdtoRemove"));
        AdminService adminService = new AdminService();
        try {
            adminService.removeCompByInnerId(companyId);
        } catch (ServiceException e) {
            session.setAttribute("ShowCompanyNameCollisionssError", ConfigManager.message("msg.error.processing"));
        }
        return new Page(ConfigManager.getProperty("path.page.admin"));
    }
}
