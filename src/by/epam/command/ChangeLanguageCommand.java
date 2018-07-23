package by.epam.command;

import by.epam.service.ConfigManager;
import by.epam.entity.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.jstl.core.Config;
import java.util.Locale;
import java.util.logging.Logger;

public class ChangeLanguageCommand implements Command {
    private final static Logger LOG = Logger.getLogger("ChangeLanguageCommand");

    @Override
    public Page execute(HttpServletRequest request) {

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
        return new Page(ConfigManager.getProperty("path.page.index"));
    }
}