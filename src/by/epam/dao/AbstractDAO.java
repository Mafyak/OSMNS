package by.epam.dao;

import by.epam.entity.Company;
import by.epam.entity.User;
import by.epam.entity.UserHistory;
import by.epam.exception.DAOException;
import by.epam.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public abstract class AbstractDAO<T> {

    //public abstract T getEntity(Object... params);
    //public abstract boolean add(T t);
    //public abstract T get(int id);
    //public abstract boolean delete(T t);
    //public abstract T remove(int id);

    private static final Logger LOG = Logger.getLogger(AbstractDAO.class);

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

    public List<UserHistory> getReviews(String query, Object... params) throws DAOException {
        Connection conn = ConnectionPool.getInstance().getConnection();
        List<UserHistory> userHistories;
        UserHistory userHistory;
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            try (ResultSet rs = ps.executeQuery()) {
                userHistories = new ArrayList<>();
                while (rs.next()) {
                    userHistory = new UserHistory();
                    userHistory.setIdCompany(rs.getInt("idCompany"));
                    userHistory.setIdHR(rs.getInt("idHR"));
                    userHistory.setIdEmployee(rs.getInt("idEmployee"));
                    userHistory.setYearEmployed(rs.getInt("yearEmployed"));
                    userHistory.setYearTerminated(rs.getInt("yearTerminated"));
                    userHistory.setRating1(rs.getInt("rating1"));
                    userHistory.setRating2(rs.getInt("rating2"));
                    userHistory.setRating3(rs.getInt("rating3"));
                    userHistory.setRating4(rs.getInt("rating4"));
                    userHistory.setRating5(rs.getInt("rating5"));
                    userHistory.setHireAgain(rs.getInt("hireAgain"));
                    userHistory.setConfirmed(rs.getInt("confirmed"));
                    userHistory.setRatingID(rs.getInt("ratingID"));
                    userHistories.add(userHistory);
                }
            }
        } catch (SQLException e) {
            LOG.info("SQLException in AdminDAO method getAllReviews()");
            throw new DAOException("Detected SQLException in AdminDAO method getAllReviews()", e);
        } finally {
            ConnectionPool.getInstance().returnConnection(conn);
        }
        return userHistories;
    }

    public List<User> getHrWithCompanyDataList(String query, Object... params) throws DAOException {
        Connection conn = ConnectionPool.getInstance().getConnection();
        List<User> userList = new ArrayList<>();
        User user;
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            try (ResultSet rs = ps.executeQuery()) {
                Company company;
                while (rs.next()) {
                    user = new User();
                    user.setId(rs.getInt("idHRhead"));
                    user.setfName(rs.getString("fName"));
                    user.setmName(rs.getString("mName"));
                    user.setlName(rs.getString("lName"));
                    company = new Company();
                    company.setName(rs.getString("name"));
                    company.setCompanyInnerId(rs.getInt("idCompany"));
                    company.setNiche(rs.getString("niche"));
                    company.setLocation(rs.getString("location"));
                    company.setHeadcount(rs.getInt("headcount"));
                    company.setCompanyOfficialId(rs.getInt("offCompId"));
                    company.setCompanyInnerId(rs.getInt("idCompany"));
                    user.setCompany(company);
                    userList.add(user);
                }
            }
        } catch (SQLException e) {
            LOG.info("SQL Exception during getHr method in UserDAO");
            throw new DAOException("SQL Exception during getHr command execution", e);
        } finally {
            ConnectionPool.getInstance().returnConnection(conn);
        }
        return userList;
    }
}
