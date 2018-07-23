package by.epam.dao;

import by.epam.exception.DAOException;
import by.epam.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public abstract class AbstractDAO<T> {

    //public abstract T getEntity(Object... params);
    //public abstract boolean add(T t);
    //public abstract T get(int id);
    //public abstract boolean delete(T t);
    //public abstract T remove(int id);

    private static Logger LOG = Logger.getLogger("AbstractDAO");

    public void updateQuery(String query, Object... param) throws DAOException {
        Connection conn = ConnectionPool.getInstance().getConnection();
        try {
            updateQuery(conn, query, param);
        } finally {
            ConnectionPool.getInstance().returnConnection(conn);
        }
    }

    public void updateQuery(Connection conn, String query, Object... param) throws DAOException {
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            for (int i = 0; i < param.length; i++) {
                ps.setObject(i + 1, param[i]);
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            LOG.info("SQL Exception during updateQuery from AbstractDAO" + e);
            throw new DAOException("Error during updateQuery", e);
        }
    }

    public void executeQuery(String query, Object... param) throws DAOException {
        Connection conn = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            for (int i = 0; i < param.length; i++) {
                ps.setObject(i + 1, param[i]);
            }
            ps.execute();
        } catch (SQLException e) {
            LOG.info("SQL Exception during executeQuery from AbstractDAO" + e);
            throw new DAOException("Error during executeQuery", e);
        } finally {
            ConnectionPool.getInstance().returnConnection(conn);
        }
    }

    public Object executeForSingleResult(String query, Object... params) throws DAOException {
        Connection conn = ConnectionPool.getInstance().getConnection();
        Object result = null;
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result = rs.getObject(1);
                }
            }
        } catch (SQLException e) {
            LOG.info("SQL Exception during registration command execution" + e);
            throw new DAOException("Error during new user registrations", e);
        } finally {
            ConnectionPool.getInstance().returnConnection(conn);
        }
        return result;
    }
}
