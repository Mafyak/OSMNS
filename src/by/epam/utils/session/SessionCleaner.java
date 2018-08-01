package by.epam.utils.session;

import by.epam.entity.User;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;

public class SessionCleaner {
    public void cleanSession(HttpSession session) {

        User currentHR = (User) session.getAttribute("user");

        Enumeration e = session.getAttributeNames();
        while (e.hasMoreElements()) {
            String name = (String) e.nextElement();
            session.removeAttribute(name);
        }

        session.setAttribute("user", currentHR);
    }
}
