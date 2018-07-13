package by.epam.controller;

import by.epam.command.Command;
import by.epam.command.factory.ActionFactory;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;



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
        String page = null;
        ActionFactory client = new ActionFactory();
        Command command = client.defineCommand(request);
        HttpSession session = request.getSession();
        page = command.execute(request);
        if (page != null) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            // вызов страницы ответа на запрос
            dispatcher.forward(request, response);
            //response.sendRedirect(page);
        } else {
            page = "/index.jsp";
         // page = ConfigurationManager.getProperty("path.page.index");
         // request.getSession().setAttribute("nullPage", MessageManager.getProperty("message.nullpage"));
            request.getSession().setAttribute("nullPage", "Page not found. Business logic error.");
            response.sendRedirect(request.getContextPath() + page);
        }
    }
}
