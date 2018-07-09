package by.epam.service;

import by.epam.dao.UserDAO;
import by.epam.entity.User;
import by.epam.pool.ConnectionPool;

import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class UserService {


    private static final Logger LOG = Logger.getLogger("UserService");
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("resources/mysqlStatements");
    private String entryQuery = resourceBundle.getString("GET_USER_ENTITY");
    private String queryRegister = resourceBundle.getString("REGISTER_COMMAND");
    private String queryAddInfo = resourceBundle.getString("ADD_INFO_COMMAND");
    private String queryGetId = resourceBundle.getString("GET_USER_ID");

    public User login(String login, String pass) {
        User user;
        Connection conn = ConnectionPool.getInstance().getConnection();
        UserDAO userDAO = new UserDAO(conn);
        user = userDAO.getEntity(entryQuery, login, pass);
        ConnectionPool.getInstance().closeConnection(conn);
        return user;
    }

    public boolean register(String login, String pass, String fName, String mName, String lName) {
        Connection conn = ConnectionPool.getInstance().getConnection();
        UserDAO userDAO = new UserDAO(conn);
        ResultSet rs = null;
        PreparedStatement ps = null;
        Statement st = null;
        int id = 0;
        boolean result = false;
        try {

            ps = conn.prepareStatement(queryRegister);
            ps.setString(1, login);
            ps.setString(2, pass);
            ps.execute();
            result = true;

            ps = conn.prepareStatement(queryGetId);
            ps.setString(1, login);
            ps.setString(2, pass);
            rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt(1);
            }

            ps = conn.prepareStatement(queryAddInfo);
            ps.setInt(1, id);
            ps.setString(2, fName);
            ps.setString(3, mName);
            ps.setString(4, lName);
            ps.executeUpdate();
            LOG.info("New user is added:" + login);

        } catch (SQLException e) {
            LOG.info("SQL Exception during registration command execution");
            e.printStackTrace();
            result = false;
        }
        ConnectionPool.getInstance().closeConnection(conn);
        return result;
    }
}
