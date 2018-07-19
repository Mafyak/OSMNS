package by.epam.service;

import by.epam.dao.UserDAO;
import by.epam.entity.Company;
import by.epam.entity.User;
import by.epam.entity.UserHistory;
import by.epam.exception.DAOException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UserService {

    Logger LOG = Logger.getLogger("UserService");
    private UserDAO userDAO;

    public UserService() {
        userDAO = new UserDAO();
    }

    public User login(String login, String pass) {
        Security security = new Security();
        return userDAO.getEntity(login, security.encryptData(pass));
    }

    public void register(String login, String pass, String fName, String mName, String lName) throws DAOException {
        Security security = new Security();
        User user = new User();
        user.setEmail(login);
        user.setPass(security.encryptData(pass));
        user.setfName(fName);
        user.setmName(mName);
        user.setlName(lName);
        userDAO.register(user);
    }

    public void addReview(User hrHead, User employee, UserHistory history) throws SQLException {
        userDAO.addReview(hrHead, employee, history);
    }

    public User getUserBySSN(int SSN) throws DAOException {
        return userDAO.getEntityBySSN(SSN);
    }

    public void addMyCompany(User user, Company userCompany) {
        userDAO.addMyCompany(user, userCompany);
    }

    public List<User> getHrByName(String fName, String lName) throws SQLException {
        return userDAO.getHrByName(fName, lName);
    }

    public int getHRTrustRate(int idHR) throws SQLException {
        LOG.info("before getting reviews");
         ArrayList<Integer> allReviewsList = userDAO.getReviewsRateForHrById(idHR);
         LOG.info("got allReviewsList");
         ArrayList<Double> avgReviewsList = userDAO.getAVGReviewsRateForHrById(idHR);
        LOG.info("got avgReviewsList");
        return TrustRateCalculator.generateTrustRate(allReviewsList, avgReviewsList);
    }

}
