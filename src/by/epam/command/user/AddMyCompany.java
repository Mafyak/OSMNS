package by.epam.command.user;

import by.epam.command.Command;
import by.epam.entity.Company;
import by.epam.entity.User;
import by.epam.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class AddMyCompany implements Command {
    Logger LOG = Logger.getLogger("AddMyCompany");
    private ResourceBundle resourceBundle = ResourceBundle.getBundle("resources/config");
    private static final String company = "companyName";
    private static final String niche = "niche";
    private static final String location = "location";
    private static final String headcount = "headcount";

    @Override
    public String execute(HttpServletRequest request) {
        String page = resourceBundle.getString("mainPage");
        HttpSession session = request.getSession();

        User currentUser = (User) session.getAttribute("user");
      //  User currentUser = (User) request.getAttribute("user");
        Company myCompany = new Company();

        LOG.info("user check:");
        LOG.info("user info: " + currentUser.toString());
        LOG.info("user check end");


        myCompany.setName(request.getParameter(company));
        myCompany.setNiche(request.getParameter(niche));
        myCompany.setLocation(request.getParameter(location));
        myCompany.setHeadcount(Integer.parseInt(request.getParameter(headcount)));
        UserService userService = new UserService();
        LOG.info("Processing new company creation: " + myCompany.toString() + ". Passing to service.");
        userService.addMyCompany(currentUser, myCompany);
        LOG.info("Processing new company creation. End of command.");
        return page;
    }
}
