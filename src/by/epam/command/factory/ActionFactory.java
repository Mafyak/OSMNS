package by.epam.command.factory;

import by.epam.command.Command;
import by.epam.command.CommandType;
import by.epam.service.ConfigManager;
import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

public class ActionFactory {

    private static Logger LOG = Logger.getLogger("ActionFactory");
    public Command defineCommand(HttpServletRequest request) {
        Command current = null;
        String action = request.getParameter("command");
        LOG.info("Param is:" + action);
        try {
            CommandType commandType = CommandType.valueOf(action.toUpperCase());
            current = commandType.getCurrentCommand();
        } catch (IllegalArgumentException e) {
            request.setAttribute("wrongAction", ConfigManager.message("cmd.cmd.notFound"));
            LOG.info("CommandType" + action + " unknown");
        }
        return current;
    }
}