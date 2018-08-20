package by.epam.command;

import by.epam.entity.Page;

import javax.servlet.http.HttpServletRequest;

/**
 * Main command interface.
 *
 * @author Siarhei Huba.
 */
public interface Command {
    Page execute(HttpServletRequest request);
}
