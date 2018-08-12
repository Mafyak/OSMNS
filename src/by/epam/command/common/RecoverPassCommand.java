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

public class RecoverPassCommand implements Command {

    private static final Logger LOG = Logger.getLogger(RecoverPassCommand.class);

    @Override
    public Page execute(HttpServletRequest request) {
        SendMail sendMail = new SendMail();
        String email = request.getParameter("recovEm");

        UserService userService = new UserService();
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        String newPass = passwordGenerator.generate();
        try {
            userService.setNewPassword(email, newPass);
            String body = "Dear user, your password was changed based on \"Forgot Password\" request from main page!! " +
                    "Your temporary password is: " + newPass + ". Please, log in and change it to permanent at your earliest " +
                    "convenience!";
            sendMail.send(email, body);
            request.setAttribute("infoMessage", "Recover email has been sent");
        } catch (ServiceException e) {
            request.setAttribute("infoMessage", "Can't recover email... You sure you're registered?");
        }

        return new Page(Manager.getProperty("path.page.login"));
    }
}
