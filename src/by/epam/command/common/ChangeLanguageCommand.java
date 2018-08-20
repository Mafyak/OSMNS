package by.epam.command.common;

import by.epam.command.Command;
import by.epam.utils.manager.Manager;
import by.epam.entity.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.util.Locale;

import by.epam.utils.session.SessionCleaner;
import org.apache.log4j.Logger;
/**
 * Command to change UI language.
 *
 * @author Siarhei Huba
 */
public class ChangeLanguageCommand implements Command {

    private final static Logger LOG = Logger.getLogger(ChangeLanguageCommand.class);

    @Override
    public Page execute(HttpServletRequest request) {

        String lang = request.getParameter("lang");
        String uri = request.getHeader("Referer");
        int uriLength = Integer.parseInt(Manager.getMan().getPage("url.length"));

        String referer = uri.substring(uriLength);
        LOG.info("referer: " + referer);

        Locale locale;
        switch (lang) {
            case "ru":
                locale = new Locale("ru", "RU");
                break;
            default:
                locale = new Locale("en", "US");
        }

        HttpSession session = request.getSession();
        String link = (String) session.getAttribute("page");
        SessionCleaner.getCleaner().cleanSession(session);
        Config.set(session, Config.FMT_LOCALE, locale);
        String actualUri = null;
        LOG.info("uri: " + uri);
        LOG.info("link: " + link);

        if (referer.contains("goTo&page=")) {
            String goToPage = referer.substring(referer.lastIndexOf("=") + 1, referer.length());
            session.setAttribute("page", goToPage);
            return new Page(Manager.getMan().getPage(goToPage));
        }

        if (!referer.contains("Controller")) {
            session.setAttribute("page", referer);
            LOG.info("returning new Page(referer)");
            return new Page(referer);
        }

        if (link != null) {
            if (link.contains("_"))
                actualUri = Manager.getMan().getPage(link);
            if (link.equals("/"))
                return new Page(Manager.getMan().getPage("index_page"));
            if (link.contains(".jsp"))
                return new Page(link);
        }

        if (!uri.contains("Controller"))
            session.setAttribute("page", referer);


        if (referer.equals("/Controller")) {
            session.setAttribute("page", "index_page");
            return new Page(Manager.getMan().getPage("index_page"));
        }

        LOG.info("returning new Page(actualUri)");
        return new Page(actualUri);
    }
}