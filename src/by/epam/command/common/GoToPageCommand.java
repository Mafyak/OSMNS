package by.epam.command.common;

import by.epam.command.Command;
import by.epam.entity.Page;
import by.epam.utils.manager.Manager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
/**
 * Command for proper forward. Removes commonly used info attributes in order to provide clean pages.
 *
 * @author Siarhei Huba
 */
public class GoToPageCommand implements Command {

    private final static Logger LOG = Logger.getLogger(GoToPageCommand.class);

    @Override
    public Page execute(HttpServletRequest request) {
        String page = request.getParameter("page");

        HttpSession session = request.getSession();
        session.removeAttribute("infoMessage");
        session.removeAttribute("emptyEmployee");
        session.removeAttribute("employee");
        session.removeAttribute("infoSearcdDelHRMessage");

        if (page != null)
            return new Page(Manager.getMan().getPage(page));

        return (Page) session.getAttribute("pageObj");
    }
}