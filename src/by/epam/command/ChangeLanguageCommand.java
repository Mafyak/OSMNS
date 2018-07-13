package by.epam.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.jstl.core.Config;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ChangeLanguageCommand implements Command {
    private final static Logger LOG = Logger.getLogger("ChangeLanguageCommand");
    private final static ResourceBundle resourceBundle2 = ResourceBundle.getBundle("resources/config");

    @Override
    public String execute(HttpServletRequest request) {
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
        return resourceBundle2.getString("path.page.index");
    }
}