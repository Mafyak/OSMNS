package by.epam.filter;

import by.epam.config.ConfigurationManager;
import by.epam.entity.User;
import by.epam.entity.UserType;
import by.epam.service.UserService;

import java.io.IOException;
import java.util.logging.Logger;
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
        String requestURI = "";
        HttpServletRequest req = (HttpServletRequest) request;
        User user = new User();
        try {
            user = (User) req.getSession().getAttribute("user");

        } catch (Exception e) {
            LOG.info("error " + e);
        }

        try {
            if (user.getType() == null) {
                LOG.info("Role unknown, redirect to login page");
                req.getRequestDispatcher(ConfigurationManager.getProperty("path.page.login")).forward(request, response);
                return;
            } else if (user.getType() == UserType.HR) {
                requestURI = req.getRequestURI();
                LOG.info("Checking user role. Role: " + user.getType() + ", requestedURI is: " + requestURI);
                if (requestURI.contains("/jsp/adminJSP"))
                    req.getRequestDispatcher(ConfigurationManager.getProperty("path.page.login")).forward(request, response);
            } else if (user.getType() == UserType.ADMIN) {
                requestURI = req.getRequestURI();
                LOG.info("Checking user role. Role: " + user.getType() + ", requestedURI is: " + requestURI);
                if (requestURI.contains("/jsp/hrJSP"))
                    req.getRequestDispatcher(ConfigurationManager.getProperty("path.page.login")).forward(request, response);
            }
        } catch (NullPointerException e) {
            LOG.info("user is null");
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}

