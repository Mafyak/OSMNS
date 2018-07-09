package by.epam.command;

import by.epam.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.jstl.core.Config;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ChangeLanguageCommand implements Command {
    Logger LOG = Logger.getLogger("ChangeLanguageCommand");
    private final static ResourceBundle resourceBundle2 = ResourceBundle.getBundle("resources/config");

    @Override
    public String execute(HttpServletRequest request) {
    LOG.info("Current Locale: " + request.getLocale());

        String lang = request.getParameter("lang");

        Locale locale;
        switch (lang) {
            case "ru":
                locale = new Locale("ru", "RU");
                break;
            case "by":
                locale = new Locale("by", "BY");
                break;
            default:
                locale = new Locale("en", "US");
        }

        request.setAttribute("locale", locale);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("/resources/content", locale);
        Config.set(request.getSession(), Config.FMT_LOCALE, locale);
        LOG.info("Locale changed to: " + request.getLocale());
        LOG.info("Locale inside class: " + locale);
        return resourceBundle2.getString("path.page.index");
    }
}
