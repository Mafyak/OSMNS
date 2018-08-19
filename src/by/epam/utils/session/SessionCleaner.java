package by.epam.utils.session;

import by.epam.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;

public class SessionCleaner {

    private static final Logger LOG = Logger.getLogger(SessionCleaner.class);
    private static volatile SessionCleaner instance;

    public static SessionCleaner getCleaner() {
        SessionCleaner localInstance = instance;
        if (localInstance == null) {
            synchronized (SessionCleaner.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new SessionCleaner();
                }
            }
        }
        return localInstance;
    }

    public void cleanSession(HttpSession session) {
        User currentHR = (User) session.getAttribute("user");
        Enumeration e = session.getAttributeNames();
        while (e.hasMoreElements()) {
            String name = (String) e.nextElement();
            if (!name.contains("servlet.jsp.jstl") && !name.contains("page") && !name.contains("user")) {
                LOG.info("Attribute: " + name + " is getting cleaned.");
                session.removeAttribute(name);
            }
        }
        session.setAttribute("user", currentHR);
    }
}
