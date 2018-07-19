package by.epam.dao;

import by.epam.entity.User;
import by.epam.entity.UserHistory;
import by.epam.pool.ConnectionPool;

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
    Logger LOG = Logger.getLogger("AdminDAO");

    public List<UserHistory> getAllReviews() throws SQLException {
        Connection conn = ConnectionPool.getInstance().getConnection();
        Statement ps = null;
        ResultSet rs = null;
        List<UserHistory> userHistories = null;
        UserHistory userHistory = null;
        try {
            ps = conn.createStatement();
            rs = ps.executeQuery(getAllReviews);
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
        } finally {
            ConnectionPool.getInstance().returnConnection(conn);
        }
        return userHistories;
    }


    public List<UserHistory> getUnconfirmedReviews() throws SQLException {
        Connection conn = ConnectionPool.getInstance().getConnection();
        Statement ps = null;
        ResultSet rs = null;
        List<UserHistory> userHistories = null;
        UserHistory userHistory = null;
        try {
            ps = conn.createStatement();
            rs = ps.executeQuery(getUnconfirmedReviews);
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
        } finally {
            ConnectionPool.getInstance().returnConnection(conn);
        }
        return userHistories;
    }


    public void confirmRating(int ratingIdToConfirm, int adminID) throws SQLException {
        updateQuery(queryConfirmReview, adminID, ratingIdToConfirm);
    }

    public void deleteReviewById(int reviewIdToDelete) throws SQLException {
        updateQuery(queryDeleteReviewById, reviewIdToDelete);
    }
}