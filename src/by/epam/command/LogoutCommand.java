package by.epam.command;

import by.epam.service.ConfigManager;
import by.epam.entity.Page;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {

    public Page execute(HttpServletRequest request) {
        Page page = new Page(ConfigManager.getProperty("path.page.index"), true);
        request.getSession().invalidate();
        return page;
    }
}