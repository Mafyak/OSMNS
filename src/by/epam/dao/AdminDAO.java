package by.epam.dao;

import by.epam.entity.User;
import by.epam.entity.UserHistory;
import by.epam.exception.DAOException;
import by.epam.pool.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.List;
import java.util.ResourceBundle;

public class AdminDAO extends AbstractDAO<User> {

    private static final Logger LOG = Logger.getLogger(AdminDAO.class);
    private final static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("resources/mysqlStatements");
    private String getAllReviews = RESOURCE_BUNDLE.getString("GET_ALL_REVIEWS");
    private String getPagedReviews = RESOURCE_BUNDLE.getString("GET_PAGED_REVIEWS");
    private String getUnconfirmedReviews = RESOURCE_BUNDLE.getString("GET_UNCONFIRMED_REVIEWS");
    private String queryConfirmReview = RESOURCE_BUNDLE.getString("CONFIRM_REVIEW_BY_ADMIN");
    private String queryGetReviewPoolSize = RESOURCE_BUNDLE.getString("GET_TOTAL_REVIEWS");
    private String queryDeleteReviewById = RESOURCE_BUNDLE.getString("DELETE_REVIEW_BY_ID");
    private String queryDeleteHrByIdFromHistory = RESOURCE_BUNDLE.getString("DELETE_HR_BY_ID_HISTORY");
    private String queryDeleteHrByIdFromHrhead = RESOURCE_BUNDLE.getString("DELETE_HR_BY_ID_HRHEAD");
    private String queryDeleteHrByIdFromLogin = RESOURCE_BUNDLE.getString("DELETE_HR_BY_ID_LOGIN");


    public List<UserHistory> getPagedReviews(int page) throws DAOException {
        return getReviews(getPagedReviews, (page - 1) * 10);
    }

    public List<UserHistory> getAllReviews() throws DAOException {
        return getReviews(getAllReviews);
    }

    public List<UserHistory> getUnconfirmedReviews() throws DAOException {
        return getReviews(getUnconfirmedReviews);
    }

    public Long getReviewsPoolSize() throws DAOException {
        return (Long) executeForSingleResult(queryGetReviewPoolSize);
    }

    public void confirmRating(int ratingIdToConfirm, int adminID) throws DAOException {
        updateQuery(queryConfirmReview, adminID, ratingIdToConfirm);
    }

    public void deleteReviewById(int reviewIdToDelete) throws DAOException {
        LOG.info("Processing Review deletion by Id #" + reviewIdToDelete);
        updateQuery(queryDeleteReviewById, reviewIdToDelete);
    }

    public void removeHrById(int hrId) throws DAOException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        Savepoint savepoint = null;
        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();
            updateQuery(connection, queryDeleteHrByIdFromHistory, hrId);
            updateQuery(connection, queryDeleteHrByIdFromHrhead, hrId);
            updateQuery(connection, queryDeleteHrByIdFromLogin, hrId);
            connection.commit();
            connection.setAutoCommit(true);
            connection.releaseSavepoint(savepoint);
        } catch (SQLException e) {
            LOG.info("DAO Exception in removeHrById command, rolling back to last savepoint");
            try {
                connection.rollback(savepoint);
            } catch (SQLException e1) {
                LOG.info("DAO Exception in removeHrById command during rollback");
            }
        }
    }
}