package by.epam.tags;

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
                welcomeMessage = " HR-Panel";
                break;
            case ADMIN:
                welcomeMessage = " Admin-Panel";
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