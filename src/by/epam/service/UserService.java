package by.epam.service;

import by.epam.dao.UserDAO;
import by.epam.entity.Company;
import by.epam.entity.User;
import by.epam.entity.UserHistory;
import by.epam.exception.DAOException;
import by.epam.exception.ServiceException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UserService {

    private static Logger LOG = Logger.getLogger("UserService");
    private UserDAO userDAO;

    public UserService() {
        userDAO = new UserDAO();
    }

    public User login(String login, String pass) throws ServiceException {
        Security security = new Security();
        try {
            return userDAO.login(login, security.encryptData(pass));
        } catch (DAOException e) {
            LOG.info("DAOException in UserService method login()");
            throw new ServiceException("Detected DAOException in UserService method login()", e);
        }
    }

    public void register(String login, String pass, String fName, String mName, String lName) throws ServiceException {
        Security security = new Security();
        User user = new User();
        user.setEmail(login);
        user.setPass(security.encryptData(pass));
        user.setfName(fName);
        user.setmName(mName);
        user.setlName(lName);
        try {
            userDAO.register(user);
            LOG.info("New user is registered: " + user.getlName());
        } catch (DAOException e) {
            LOG.info("DAOException in UserService register() method");
            throw new ServiceException("Detected DAOException in UserService register() method", e);
        }
    }

    public void addReview(User hrHead, User employee, UserHistory history) throws ServiceException  {
        try {
            userDAO.addReview(hrHead, employee, history);
        } catch (DAOException e) {
            LOG.info("DAOException in UserService method addReview()");
            throw new ServiceException("Detected DAOException in UserService method addReview()", e);
        }
    }

    public User getUserBySSN(int SSN) throws ServiceException  {
        try {
            return userDAO.getEntityBySSN(SSN);
        } catch (DAOException e) {
            LOG.info("DAOException in UserService method getUserBySSN()");
            throw new ServiceException("Detected DAOException in UserService method getUserBySSN()", e);
        }
    }

    public void addMyCompany(User user, Company userCompany) throws ServiceException {
        try {
            userDAO.addMyCompany(user, userCompany);
        } catch (DAOException e) {
            LOG.info("DAOException in UserService method addMyCompany()");
            throw new ServiceException("Detected DAOException in UserService method addMyCompany()", e);
        }
    }

    public List<User> getHrByName(String fName, String lName) throws ServiceException  {
        try {
            return userDAO.getHrByName(fName, lName);
        } catch (DAOException e) {
            LOG.info("DAOException in UserService method getHrByName()");
            throw new ServiceException("Detected DAOException in UserService method getHrByName()", e);
        }
    }


    public User getHrById(int hrId) throws ServiceException  {
        try {
            return userDAO.getHrById(hrId);
        } catch (DAOException e) {
            LOG.info("DAOException in UserService method getHrById()");
            throw new ServiceException("Detected DAOException in UserService method getHrById()", e);
        }
    }

    public int getHRTrustRate(int idHR) throws ServiceException  {
        ArrayList<Integer> allReviewsList = null;
        try {
            allReviewsList = userDAO.getReviewsRateForHrById(idHR);
            ArrayList<Double> avgReviewsList = userDAO.getAVGReviewsRateForHrById(idHR);
            return TrustRateCalculator.generateTrustRate(allReviewsList, avgReviewsList);
        } catch (DAOException e) {
            LOG.info("DAOException in UserService method getHRTrustRate()");
            throw new ServiceException("Detected DAOException in UserService method getHRTrustRate()", e);
        }
    }
}
