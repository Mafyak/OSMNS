package by.epam.command.common;

import by.epam.command.Command;
import by.epam.entity.Page;
import by.epam.exception.ServiceException;
import by.epam.utils.common.SendMail;
import by.epam.utils.manager.Manager;
import by.epam.utils.security.PasswordGenerator;
import by.epam.utils.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.jstl.core.Config;
import java.util.Locale;
/**
 * Password recover command. Sends user an email with new, randomly created password.
 *
 * @author Siarhei Huba.
 */
public class RecoverPassCommand implements Command {

    private static final Logger LOG = Logger.getLogger(RecoverPassCommand.class);

    @Override
    public Page execute(HttpServletRequest request) {
        SendMail sendMail = new SendMail();
        String email = request.getParameter("recovEm");
        Locale locale = (Locale) Config.get(request.getSession(), Config.FMT_LOCALE);

        UserService userService = new UserService();
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        String newPass = passwordGenerator.generate();
        try {
            userService.setNewPassword(email, newPass);
            String body = Manager.getMan().message("cmn.emailLetterP1", locale) + newPass +
                    Manager.getMan().message("cmn.emailLetterP2", locale);
            sendMail.send(email, body);
            LOG.info("Recover email for user email " + email + " has been sent");
            request.setAttribute("infoMessage", Manager.getMan().message("cmn.email.passwRecEmailSent", locale));
        } catch (ServiceException e) {
            request.setAttribute("infoMessage", Manager.getMan().message("cmn.email.passwRecEmailNOTSent", locale));
        }

        return new Page(Manager.getMan().getPage("login_page"));
    }
}
