package by.epam.command.hr;

import by.epam.command.Command;
import by.epam.exception.ServiceException;
import by.epam.utils.manager.Manager;
import by.epam.entity.Page;
import by.epam.entity.User;
import by.epam.utils.service.UserService;
import by.epam.utils.session.SessionCleaner;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class ShowHrByName implements Command {
    private static final Logger LOG = Logger.getLogger(ShowHrByName.class);

    @Override
    public Page execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String fName = request.getParameter("fName");
        if (fName==null)
            fName = (String) session.getAttribute("fName");
        String lName = request.getParameter("lName");
        if (lName==null)
            lName = (String) session.getAttribute("lName");
        UserService userService = new UserService();
        List<User> hrList = null;
        //SessionCleaner.getCleaner().cleanSession(session);

        try {
            int hrId = Integer.parseInt(request.getParameter("hrIdToSearch"));
            hrList = new ArrayList<>();
            hrList.add(userService.getHrById(hrId));
            session.setAttribute("hrList", hrList);
            session.removeAttribute("reviewsList");
            session.removeAttribute("unconfirmedReviewsList");
            session.removeAttribute("companyNameCollisions");
            LOG.info("hrId is set in ShowHrByName field");
            return new Page(Manager.getMan().getPage("admin_page"), true);
        } catch (NumberFormatException | ServiceException e) {
            LOG.info("hrId is not set in ShowHrByName field");
            session.setAttribute("infoSearcdDelHRMessage", Manager.getMan().message("cmd.showHR.error"));
        }

        try {
            if (!(fName == null) && !(lName == null)) {
                session.setAttribute("fName", fName);
                session.setAttribute("lName", lName);
                hrList = userService.getHrByName(fName, lName);
            } else
                session.setAttribute("infoSearcdDelHRMessage", Manager.getMan().message("cmd.showHR.error"));
        } catch (ServiceException e) {
            session.setAttribute("infoSearcdDelHRMessage", Manager.getMan().message("cmd.showHR.error"));
        }
        session.setAttribute("hrList", hrList);
        return new Page(Manager.getMan().getPage("admin_page"), true);
    }
}
