package by.epam.controller;

import by.epam.command.Command;
import by.epam.command.factory.ActionFactory;
import by.epam.utils.manager.Manager;
import by.epam.entity.Page;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Main and only servlet for this project.
 *
 * @author Siarhei Huba.
 */
public class Controller extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request,
                                HttpServletResponse response)
            throws ServletException, IOException {

        ActionFactory client = new ActionFactory();
        Command command = client.defineCommand(request);
        Page page = command.execute(request);
        if (page != null) {
            if (page.isToRedirect()) { // use redirect for form submission commands.
                response.sendRedirect(page.getPage());
            } else { // use forward for pages without forms.
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page.getPage());
                dispatcher.forward(request, response);
            }
        } else {
            page = new Page();
            page.setPage(Manager.getMan().getPage("index_page"));
            request.getSession().setAttribute("nullPage", "Page not found. Business logic error.");
            response.sendRedirect(request.getContextPath() + page);
        }
    }
}
