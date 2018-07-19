package by.epam.command;

import by.epam.config.ConfigurationManager;
import by.epam.entity.Page;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {

    public Page execute(HttpServletRequest request) {
        Page page = new Page(ConfigurationManager.getProperty("path.page.index"), true);
        request.getSession().invalidate();
        return page;
    }
}