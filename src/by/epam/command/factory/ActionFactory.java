package by.epam.command.factory;

import by.epam.command.Command;
import by.epam.command.CommandType;
import by.epam.command.EmptyCommand;
import by.epam.logic.MessageManager;

import javax.servlet.http.HttpServletRequest;

public class ActionFactory {
    public Command defineCommand(HttpServletRequest request) {
        Command current = new EmptyCommand();
    // извлечение имени команды из запроса
        String action = request.getParameter("command");
        if (action == null || action.isEmpty()) {
            return current;
        }
    // получение объекта, соответствующего команде
        try {
            CommandType commandType = CommandType.valueOf(action.toUpperCase());
            current = commandType.getCurrentCommand();
        } catch (IllegalArgumentException e) {
            // request.setAttribute("wrongAction", action + MessageManager.getProperty("message.wrongaction"));
            request.setAttribute("wrongAction", action + "command not found or wrong!");
        }
        return current;
    }
}
