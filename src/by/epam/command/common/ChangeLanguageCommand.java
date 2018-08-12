package by.epam.command.common;

import by.epam.command.Command;
import by.epam.utils.manager.Manager;
import by.epam.entity.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.util.Locale;

import org.apache.log4j.Logger;

public class ChangeLanguageCommand implements Command {

    private final static Logger LOG = Logger.getLogger(ChangeLanguageCommand.class);

    @Override
    public Page execute(HttpServletRequest request) {

        String lang = request.getParameter("lang");
        String uri = request.getHeader("Referer");
        int uriLength = Integer.parseInt(Manager.getProperty("url.length"));

        String referer = uri.substring(uriLength);
        LOG.info("referer: " + referer);
        LOG.info("ur2: " + request.getRequestURL());

        Locale locale;
        switch (lang) {
            case "ru":
                locale = new Locale("ru", "RU");
                break;
            default:
                locale = new Locale("en", "US");
        }

        Config.set(request.getSession(), Config.FMT_LOCALE, locale);
        HttpSession session = request.getSession();

        if (!uri.contains("Controller"))
            session.setAttribute("page", referer);

        String actualUri = (String) session.getAttribute("page");
        if (actualUri.isEmpty())
            return new Page(referer);
        else
            return new Page(actualUri);
    }
}