package by.epam.command.factory;

import by.epam.command.Command;
import by.epam.command.CommandType;
import by.epam.command.EmptyCommand;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
public class ActionFactory {

    Logger LOG = Logger.getLogger(ActionFactory.class);
    public Command defineCommand(HttpServletRequest request) {
        Command current = new EmptyCommand();
        String action = request.getParameter("command");
        if (action == null || action.isEmpty()) {
            return current;
        }

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