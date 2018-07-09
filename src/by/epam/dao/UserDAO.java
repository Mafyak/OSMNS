package by.epam.dao;

import by.epam.entity.User;
import by.epam.entity.UserType;

import java.sql.*;

public class UserDAO extends AbstractDAO<User> {

    public UserDAO(Connection connection) {
        super(connection);
    }

    @Override
    public User getEntity(String query, Object... params) {
        Connection conn = getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        User user = null;
        try {
            ps = conn.prepareStatement(query);
            ps.setObject(1, params[0]);
            ps.setObject(2, params[1]);
            rs = ps.executeQuery();
            while (rs.next()) {
                String email = rs.getString("email");
                String pass = rs.getString("pass");
                int id = rs.getInt("idLogin");
                UserType type = rs.getInt("role") == 0 ? UserType.ADMIN : UserType.HR;
                String fName = rs.getString("fName");
                String lName = rs.getString("lName");
                user = new User(email, pass, id, type, fName, lName);
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return user;
        }
    }

    public boolean register(String login, String pass, String fName, String lName, String company) {
        Connection conn = getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("INSERT INTO Login (idLogin, email, pass, role) VALUES (NULL, ?, ?, 1)");
            ps.setString(1, login);
            ps.setString(2, pass);
            return ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
