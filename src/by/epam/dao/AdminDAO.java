package by.epam.dao;

import by.epam.entity.Company;
import by.epam.entity.User;
import by.epam.entity.UserHistory;
import by.epam.exception.DAOException;
import by.epam.pool.ConnectionPool;
import by.epam.service.AdminService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class AdminDAO extends AbstractDAO<User> {

    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("resources/mysqlStatements");
    private String getAllReviews = resourceBundle.getString("GET_ALL_REVIEWS");
    private String getUnconfirmedReviews = resourceBundle.getString("GET_UNCONFIRMED_REVIEWS");
    private String queryConfirmReview = resourceBundle.getString("CONFIRM_REVIEW_BY_ADMIN");
    private String queryDeleteReviewById = resourceBundle.getString("DELETE_REVIEW_BY_ID");
    private String queryDeleteHrById = resourceBundle.getString("DELETE_HR_BY_ID");
    private String queryGetCompNameCollis = resourceBundle.getString("GET_COMPANY_NAME_COLLISION");
    private String queryFind2ndcompId = resourceBundle.getString("GET_DUPLICATE_COMPANY_ID");
    private String fixCompDuplInHistory = resourceBundle.getString("FIX_DUPLICATE_COMPANY_HISTORY");
    private String fixCompDuplInHrhead = resourceBundle.getString("FIX_DUPLICATE_COMPANY_HRHEAD");
    private String queryDeleteCompById = resourceBundle.getString("DELETE_COMPANY_BY_ID");
    private String queryGetCompById = resourceBundle.getString("GET_COMPANY_INFO");
    private String updateCompanyInfo = resourceBundle.getString("UPDATE_COMPANY_INFO");



    private static Logger LOG = Logger.getLogger("AdminDAO");

    public List<UserHistory> getAllReviews() throws DAOException {
        Connection conn = ConnectionPool.getInstance().getConnection();
        List<UserHistory> userHistories = null;
        UserHistory userHistory = null;
        try (Statement ps = conn.createStatement();
             ResultSet rs = ps.executeQuery(getAllReviews)) {
            userHistories = new ArrayList<>();
            while (rs.next()) {
                userHistory = new UserHistory();
                userHistory.setIdCompany(rs.getInt(1));
                userHistory.setIdHR(rs.getInt(2));
                userHistory.setIdEmployee(rs.getInt(3));
                userHistory.setYearEmployed(rs.getInt(4));
                userHistory.setYearTerminated(rs.getInt(5));
                userHistory.setRating1(rs.getInt(6));
                userHistory.setRating2(rs.getInt(7));
                userHistory.setRating3(rs.getInt(8));
                userHistory.setRating4(rs.getInt(9));
                userHistory.setRating5(rs.getInt(10));
                userHistory.setHireAgain(rs.getInt(12));
                userHistory.setConfirmed(rs.getInt(13));
                userHistory.setRatingID(rs.getInt(14));
                userHistories.add(userHistory);
            }
        } catch (SQLException e) {
            LOG.info("SQLException in AdminDAO method getAllReviews()");
            throw new DAOException("Detected SQLException in AdminDAO method getAllReviews()", e);
        } finally {
            ConnectionPool.getInstance().returnConnection(conn);
        }
        return userHistories;
    }

    public List<Company> getCompNameCollisions() throws DAOException {
        Connection conn = ConnectionPool.getInstance().getConnection();
        List<Company> list = new ArrayList<>();
        try (Statement ps = conn.createStatement();
             ResultSet rs = ps.executeQuery(queryGetCompNameCollis)) {
            while (rs.next()) {
                Company company = new Company();
                company.setCompanyInnerId(rs.getInt(1));
                company.setName(rs.getString(2));
                company.setNiche(rs.getString(3));
                company.setLocation(rs.getString(4));
                company.setHeadcount(rs.getInt(5));
                company.setCompanyOfficialId(rs.getInt(6));
                list.add(company);
            }
        } catch (SQLException e) {
            LOG.info("SQLexception in AdminDAO method getCompNameCollisions()");
            throw new DAOException("Detected SQLexception in AdminDAO method getCompNameCollisions()", e);
        } finally {
            ConnectionPool.getInstance().returnConnection(conn);
        }
        return list;
    }

    public List<UserHistory> getUnconfirmedReviews() throws DAOException {
        Connection conn = ConnectionPool.getInstance().getConnection();
        List<UserHistory> userHistories = null;
        UserHistory userHistory = null;
        try (Statement ps = conn.createStatement();
             ResultSet rs = ps.executeQuery(getUnconfirmedReviews)) {
            userHistories = new ArrayList<>();
            while (rs.next()) {
                userHistory = new UserHistory();
                userHistory.setIdCompany(rs.getInt(1));
                userHistory.setIdHR(rs.getInt(2));
                userHistory.setIdEmployee(rs.getInt(3));
                userHistory.setYearEmployed(rs.getInt(4));
                userHistory.setYearTerminated(rs.getInt(5));
                userHistory.setRating1(rs.getInt(6));
                userHistory.setRating2(rs.getInt(7));
                userHistory.setRating3(rs.getInt(8));
                userHistory.setRating4(rs.getInt(9));
                userHistory.setRating5(rs.getInt(10));
                userHistory.setHireAgain(rs.getInt(12));
                userHistory.setConfirmed(rs.getInt(13));
                userHistories.add(userHistory);
            }
        } catch (SQLException e) {
            LOG.info("SQLexception in AdminDAO method getUnconfirmedReviews()");
            throw new DAOException("Detected SQLexception in AdminDAO method getUnconfirmedReviews()", e);
        } finally {
            ConnectionPool.getInstance().returnConnection(conn);
        }
        return userHistories;
    }

    public void confirmRating(int ratingIdToConfirm, int adminID) throws DAOException {
        updateQuery(queryConfirmReview, adminID, ratingIdToConfirm);
    }

    public void deleteReviewById(int reviewIdToDelete) throws DAOException {
        updateQuery(queryDeleteReviewById, reviewIdToDelete);
    }

    public void removeHrById(int hrId) throws DAOException {
        updateQuery(queryDeleteHrById, hrId);
    }

    //doesn't work properly - doesn't change company id in history.
    public void removeCompByInnerId(int compId) throws DAOException {
        int duplId = (int) executeForSingleResult(queryFind2ndcompId, compId, compId);
        updateQuery(fixCompDuplInHistory, duplId, compId);
        updateQuery(fixCompDuplInHrhead, duplId, compId);
        updateQuery(queryDeleteCompById, compId);
    }

    public void mergeCompByInnerId(int compId) throws DAOException {
        int duplId = (int) executeForSingleResult(queryFind2ndcompId, compId, compId);
        Connection conn = ConnectionPool.getInstance().getConnection();
        try{
            conn.setAutoCommit(false);
        updateQuery(fixCompDuplInHistory, compId, duplId);
        updateQuery(fixCompDuplInHrhead, compId, duplId);
        Company properComp = getCompById(compId);
        Company donorComp = getCompById(duplId);
        AdminService adminService = new AdminService();
        adminService.mergeCompanies(properComp, donorComp);
        updateQuery(updateCompanyInfo, properComp.getNiche(), properComp.getLocation(), properComp.getHeadcount(), compId);
        updateQuery(queryDeleteCompById, duplId);
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                LOG.info("Can't rollback changes");
                throw new  DAOException("Can't rollback changes");
            }
            LOG.info("Can't add data changes. Rollback was made");
            throw new  DAOException("Can't add data changes. Rollback was made");
        }
        finally {
            ConnectionPool.getInstance().returnConnection(conn);
        }
    }

    public Company getCompById(int compId) throws DAOException {
        Connection conn = ConnectionPool.getInstance().getConnection();
        Company company = null;
        try (PreparedStatement ps = conn.prepareStatement(queryGetCompById)) {
            ps.setInt(1, compId);
            try (ResultSet rs = ps.executeQuery()) {
                company = new Company();
                while (rs.next()) {
                    company.setCompanyInnerId(rs.getInt("idCompany"));
                    company.setName(rs.getString("name"));
                    company.setNiche(rs.getString("niche"));
                    company.setLocation(rs.getString("location"));
                    company.setHeadcount(rs.getInt("headCount"));
                    company.setCompanyOfficialId(rs.getInt("offCompId"));
                }
            }
        } catch (SQLException e) {
            LOG.info("SQLexception in AdminDAO method getCompById()");
            throw new DAOException("Detected SQLexception in AdminDAO method getCompById()", e);
        } finally {
            ConnectionPool.getInstance().returnConnection(conn);
        }
        return company;
    }
}