package by.epam.command.admin;

import by.epam.command.Command;
import by.epam.entity.Page;
import by.epam.exception.ServiceException;
import by.epam.service.AdminService;
import by.epam.service.ConfigManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.logging.Logger;

public class MergeCompanyCommand implements Command {
    Logger LOG = Logger.getLogger("MergeCompanyCommand");

    @Override
    public Page execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        int companyId = Integer.parseInt(request.getParameter("companyIdtoMerge"));
        AdminService adminService = new AdminService();
        try {
            adminService.mergeCompByInnerId(companyId);
        } catch (ServiceException e) {
            session.setAttribute("ShowCompanyNameCollisionssError", ConfigManager.message("msg.error.processing"));
        }
        return new Page(ConfigManager.getProperty("path.page.admin"));
    }
}

