package by.epam.tags;

import by.epam.service.UserService;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

public class HRTrustRateTag extends TagSupport{
    Logger LOG = Logger.getLogger("HRTrustRateTag");

    private int hrID;

    public void setHrID(int hrID) {
        this.hrID = hrID;
    }

    public int doStartTag() throws JspException {
        UserService userService = new UserService();
        int trustRate = 0;
        try {
            LOG.info("got to tag!");
            trustRate = userService.getHRTrustRate(hrID);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            pageContext.getOut().write(trustRate+"%");
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }
}
