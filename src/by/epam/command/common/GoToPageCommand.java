package by.epam.command.common;

import by.epam.command.Command;
import by.epam.entity.Page;
import by.epam.utils.manager.Manager;

import javax.servlet.http.HttpServletRequest;

public class GoToPageCommand implements Command {
    @Override
    public Page execute(HttpServletRequest request) {
        String page = request.getParameter("page");
        return new Page(Manager.getMan().getPage(page));
    }
}
