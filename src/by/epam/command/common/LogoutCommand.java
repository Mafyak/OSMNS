package by.epam.command.common;

import by.epam.command.Command;
import by.epam.utils.manager.Manager;
import by.epam.entity.Page;

import javax.servlet.http.HttpServletRequest;
/**
 * Logout command.
 *
 * @author Siarhei Huba
 */
public class LogoutCommand implements Command {

    public Page execute(HttpServletRequest request) {
        Page page = new Page(Manager.getMan().getPage("index_page"), true);
        request.getSession().invalidate();
        return page;
    }
}