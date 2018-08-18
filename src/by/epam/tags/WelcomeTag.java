package by.epam.tags;

import by.epam.utils.manager.Manager;
import by.epam.entity.UserType;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class WelcomeTag extends TagSupport {

    private UserType userType;

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public int doStartTag() throws JspException {

        String welcomeMessage = null;
        switch (userType) {
            case HR:
                welcomeMessage = Manager.getMan().message("tag.hr.welcome");
                break;
            case ADMIN:
                welcomeMessage = Manager.getMan().message("tag.admin.welcome");
                break;
        }

        try {
            pageContext.getOut().write(welcomeMessage);
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }
}