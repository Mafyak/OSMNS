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

    private static final Logger LOG = Logger.getLogger(AbstractDAO.class);

    public int updateQuery(String query, Object... param) throws DAOException {
        Connection conn = ConnectionPool.getInstance().getConnection();
        try {
           return updateQuery(conn, query, param);
        } finally {
            ConnectionPool.getInstance().returnConnection(conn);
        }
    }

    public int updateQuery(Connection conn, String query, Object... param) throws DAOException {
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            for (int i = 0; i < param.length; i++) {
                ps.setObject(i + 1, param[i]);
                LOG.info("i: " + i + ", param: " + param[i]);
            }
            return ps.executeUpdate();
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
        LOG.info("Param size: " + params.length);
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
                LOG.info("Param # " + i + 1 + " equals " + params[i]);
            }
            LOG.info("Before try block");
            try (ResultSet rs = ps.executeQuery()) {
                LOG.info("Test 0");
                userHistories = new ArrayList<>();
                LOG.info("Test 1");
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
                    try {
                        userHistory.setCompany(rs.getString("name"));
                    } catch (SQLException e) {
                        LOG.info("Company name is not found in sql query, processing further");
                    }
                    userHistories.add(userHistory);
                }
            }
        } catch (SQLException e) {
            LOG.info("SQLException in AbstractDAO method getReviews()");
            throw new DAOException("Detected SQLException in AbstractDAO method getReviews()", e);
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
                    company.setId(rs.getInt("idCompany"));
                    company.setNiche(rs.getString("niche"));
                    company.setLocation(rs.getString("location"));
                    company.setHeadcount(rs.getInt("headcount"));
                    company.setCompanyOfficialId(rs.getInt("offCompId"));
                    company.setId(rs.getInt("idCompany"));
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
