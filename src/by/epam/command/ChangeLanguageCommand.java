package by.epam.command;

import by.epam.config.ConfigurationManager;
import by.epam.entity.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.jstl.core.Config;
import java.util.Locale;
import java.util.logging.Logger;

public class ChangeLanguageCommand implements Command {
    private final static Logger LOG = Logger.getLogger("ChangeLanguageCommand");

    @Override
    public Page execute(HttpServletRequest request) {
        // HttpSession session = request.getSession();

        String lang = request.getParameter("lang");

        Locale locale;
        switch (lang) {
            case "ru":
                locale = new Locale("ru", "RU");
                break;
            default:
                locale = new Locale("en", "US");
        }

        Config.set(request.getSession(), Config.FMT_LOCALE, locale);
        return new Page(ConfigurationManager.getProperty("path.page.index"));
    }
}