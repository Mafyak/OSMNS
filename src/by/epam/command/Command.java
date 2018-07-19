package by.epam.command;

import by.epam.entity.Page;
import javax.servlet.http.HttpServletRequest;

public interface Command {
    Page execute(HttpServletRequest request);
}
