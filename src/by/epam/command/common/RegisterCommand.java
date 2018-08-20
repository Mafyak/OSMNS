package by.epam.command.common;

import by.epam.command.Command;
import by.epam.exception.ServiceException;
import by.epam.utils.manager.Manager;
import by.epam.entity.Page;
import by.epam.utils.service.ContentValidator;
import by.epam.utils.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.util.Locale;
/**
 * Registration command.
 *
 * @author Siarhei Huba
 */
public class RegisterCommand implements Command {

    private static final String PARAM_NAME_LOGIN = "regLogin";
    private static final String PARAM_NAME_PASSWORD = "regPassword";
    private static final String PARAM_NAME_FIRST_NAME = "fName";
    private static final String PARAM_NAME_LAST_NAME = "lName";

    public Page execute(HttpServletRequest request) {
        Page page;
        String login = request.getParameter(PARAM_NAME_LOGIN);
        HttpSession session = request.getSession();
        Locale locale = (Locale) Config.get(session, Config.FMT_LOCALE);
        if (locale == null) {
            locale = new Locale("en");
            Config.set(session, Config.FMT_LOCALE, locale);
        }

        if (!ContentValidator.validate(login)) {
            session.setAttribute("infoMessage", Manager.getMan().message("cmd.email.error", locale));
            page = new Page(Manager.getMan().getPage("index_page"), true);
            return page;
        }

        String pass = request.getParameter(PARAM_NAME_PASSWORD);
        String fName = request.getParameter(PARAM_NAME_FIRST_NAME);
        String mName = "";
        String lName = request.getParameter(PARAM_NAME_LAST_NAME);

        UserService userService = new UserService();
        try {
            userService.register(login, pass, fName, mName, lName);
            session.setAttribute("infoMessage", Manager.getMan().message("cmd.reg.success", locale));
            page = new Page(Manager.getMan().getPage("index_page"), true);
        } catch (ServiceException e) {
            session.setAttribute("infoMessage", Manager.getMan().message("cmd.reg.failure", locale));
            page = new Page(Manager.getMan().getPage("index_page"), true);
        }
        return page;
    }
}
