package by.epam.service;

import by.epam.dao.UserDAO;
import by.epam.entity.Company;
import by.epam.entity.User;
import by.epam.exception.DAOException;

public class UserService {

    private UserDAO userDAO;

    public UserService() {
        userDAO = new UserDAO();
    }

    public User login(String login, String pass) {

        return userDAO.getEntity(login, pass);
    }

    public void register(String login, String pass, String fName, String mName, String lName) throws DAOException {
        User user = new User();
        user.setEmail(login);
        user.setPass(pass);
        user.setfName(fName);
        user.setmName(mName);
        user.setlName(lName);
        userDAO.register(user);
    }

    public User getUserBySSN(int SSN) {
        return userDAO.getEntityBySSN(SSN);
    }

    public void addMyCompany(User user, Company userCompany) {
        userDAO.addMyCompany(user, userCompany);
    }
}
