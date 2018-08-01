package by.epam.filter;

import by.epam.utils.manager.Manager;
import by.epam.entity.User;
import by.epam.entity.UserType;

import java.io.IOException;
import org.apache.log4j.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter(urlPatterns = {"/*"})
public class RoleFilter implements Filter {

    private static final Logger LOG = Logger.getLogger("LoginRequiredFilter");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String requestURI;
        HttpServletRequest req = (HttpServletRequest) request;
        User user = (User) req.getSession().getAttribute("user");
        try {
            if (user.getType() == null) {
                LOG.info("Role unknown, redirect to login page");
                req.getRequestDispatcher(Manager.getProperty("path.page.login")).forward(request, response);
                return;
            } else if (user.getType() == UserType.HR) {
                requestURI = req.getRequestURI();
                if (requestURI.contains("/jsp/adminJSP"))
                    req.getRequestDispatcher(Manager.getProperty("path.page.login")).forward(request, response);
            } else if (user.getType() == UserType.ADMIN) {
                requestURI = req.getRequestURI();
                if (requestURI.contains("/jsp/hrJSP"))
                    req.getRequestDispatcher(Manager.getProperty("path.page.login")).forward(request, response);
            }
        } catch (NullPointerException e) {
            LOG.info("user type is null");
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}

