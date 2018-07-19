package by.epam.command.factory;

import by.epam.command.Command;
import by.epam.command.CommandType;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
public class ActionFactory {

    private static Logger LOG = Logger.getLogger(ActionFactory.class);
    public Command defineCommand(HttpServletRequest request) {
        Command current = null;
        String action = request.getParameter("command");

        try {
            CommandType commandType = CommandType.valueOf(action.toUpperCase());
            current = commandType.getCurrentCommand();
        } catch (IllegalArgumentException e) {
            // request.setAttribute("wrongAction", action + MessageManager.getProperty("message.wrongaction"));
            request.setAttribute("wrongAction", action + "command not found or wrong!");
            LOG.info("CommandType" + action + " unknown");
        }
        return current;
    }
}