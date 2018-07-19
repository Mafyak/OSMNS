package by.epam.dao;

import by.epam.entity.User;
import by.epam.exception.DAOException;
import by.epam.pool.ConnectionPool;
import sun.rmi.runtime.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public abstract class AbstractDAO<T> {

    //public abstract T getEntity(Object... params);
    //public abstract boolean add(T t);
    //public abstract T get(int id);
    //public abstract boolean delete(T t);
    //public abstract T remove(int id);

    private static Logger LOG = Logger.getLogger("AbstractDAO");

    public void updateQuery(String query, Object... param) throws SQLException {
        Connection conn = ConnectionPool.getInstance().getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(query);
            for (int i = 0; i < param.length; i++) {
                ps.setObject(i + 1, param[i]);
            }
            LOG.info("preExecute check");
            ps.executeUpdate();
        } finally {
            ConnectionPool.getInstance().returnConnection(conn);
        }
    }

    public void executeQuery(String query, Object... param) throws DAOException {
        Connection conn = ConnectionPool.getInstance().getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(query);
            for (int i = 0; i < param.length; i++) {
                ps.setObject(i + 1, param[i]);
            }
            ps.execute();
        } catch (SQLException e) {
            LOG.info("SQL Exception during registration command execution" + e);
            throw new DAOException("error during new user registrations", e);
        } finally {
            ConnectionPool.getInstance().returnConnection(conn);
        }
    }

    public Object executeForSingleResult(String query, Object... params) {
        Connection conn = ConnectionPool.getInstance().getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        Object result = null;
        try {

            ps = conn.prepareStatement(query);
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            ps.executeQuery();
            while (rs.next()) {
                result = rs.getObject(1);
            }
        } catch (SQLException e) {
            LOG.info("SQL Exception during registration command execution" + e);
        } finally {
            ConnectionPool.getInstance().returnConnection(conn);
        }
        return result;
    }
}
