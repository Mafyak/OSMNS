package by.epam.command.common;

import by.epam.command.Command;
import by.epam.entity.Page;
import by.epam.entity.User;
import by.epam.utils.manager.Manager;
import by.epam.utils.session.SessionCleaner;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class GoToPageCommand implements Command {

    private final static Logger LOG = Logger.getLogger(GoToPageCommand.class);

    @Override
    public Page execute(HttpServletRequest request) {
        String page = request.getParameter("page");
        if (page != null)
            return new Page(Manager.getMan().getPage(page));

        HttpSession session = request.getSession();
        return (Page) session.getAttribute("pageObj");
    }


//    @Override
//    public Page execute(HttpServletRequest request) {
//
//        String pageS = request.getParameter("page");
//        HttpSession session = request.getSession();
//
//        if (pageS != null) {
//            SessionCleaner.getCleaner().cleanSession(session);
//            return new Page(Manager.getMan().getPage(pageS), true);
//        }
//
//        Page pageObj = (Page) request.getAttribute("pageObj");
//        if (pageObj == null) {
//            pageObj = new Page();
//        }
//
//        LOG.info("request parameter page is: " + pageS);
//        LOG.info("pageObj page is: " + pageObj.getPage());
//        SessionCleaner.getCleaner().cleanSession(session);
//        LOG.info("Going to " + pageObj.getPage() + " page.");
//
//        if (pageObj.getPage().contains(".jsp"))
//            return pageObj;
//        pageObj.setPage(Manager.getMan().getPage(pageObj.getPage()));
//        return pageObj;
//    }
}