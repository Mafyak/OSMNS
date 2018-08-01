package by.epam.command.common;

import by.epam.command.Command;
import by.epam.utils.manager.Manager;
import by.epam.entity.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.jstl.core.Config;
import java.util.Locale;
import org.apache.log4j.Logger;

public class ChangeLanguageCommand implements Command {

    private final static Logger LOG = Logger.getLogger(ChangeLanguageCommand.class);

    @Override
    public Page execute(HttpServletRequest request) {

        String lang = request.getParameter("lang");
        String uri2 = request.getHeader("Referer");
        LOG.info("uri2: " + uri2);
        String referer = uri2.substring(21);
        LOG.info("referer: " + referer);

        Locale locale;
        switch (lang) {
            case "ru":
                locale = new Locale("ru", "RU");
                break;
            default:
                locale = new Locale("en", "US");
        }

        Config.set(request.getSession(), Config.FMT_LOCALE, locale);

        if (referer.contains("Controller") || referer.equals("/")) {
            return new Page(Manager.getProperty("path.page.index"));
        } else {
            return new Page(referer);
        }
    }
}