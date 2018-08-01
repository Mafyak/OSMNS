package by.epam.tags;

import by.epam.exception.ServiceException;
import by.epam.utils.service.UserService;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import org.apache.log4j.Logger;

public class HRTrustRateTag extends TagSupport {

    private static final Logger LOG = Logger.getLogger("HRTrustRateTag");
    private int hrID;

    public void setHrID(int hrID) {
        this.hrID = hrID;
    }

    public int doStartTag() throws JspException {
        UserService userService = new UserService();
        int trustRate = 0;
        try {
            trustRate = userService.getHRTrustRate(hrID);
        } catch (ServiceException e) {
            LOG.warn("SQL exception from HRTrustRate tag set up command");
        }

        try {
            pageContext.getOut().write(trustRate + "%");
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }
}
