package by.epam.command.factory;

import by.epam.command.Command;
import by.epam.command.CommandType;
import by.epam.utils.manager.Manager;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class ActionFactory {

    private static final Logger LOG = Logger.getLogger(ActionFactory.class);

    public Command defineCommand(HttpServletRequest request) {
        Command current = null;
        String action = request.getParameter("command");
        LOG.info("Param is:" + action);
        try {
            CommandType commandType = CommandType.valueOf(action.toUpperCase());
            current = commandType.getCurrentCommand();
        } catch (IllegalArgumentException e) {
            request.setAttribute("wrongAction", Manager.message("cmd.cmd.notFound"));
            LOG.info("CommandType" + action + " unknown");
        }
        return current;
    }
}