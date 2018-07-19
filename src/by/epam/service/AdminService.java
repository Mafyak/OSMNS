package by.epam.service;

import by.epam.dao.AdminDAO;
import by.epam.entity.UserHistory;

import java.sql.SQLException;
import java.util.List;

public class AdminService {

    private AdminDAO adminDAO = new AdminDAO();

    public List<UserHistory> getAllReviews() throws SQLException {
        return adminDAO.getAllReviews();
    }

    public List<UserHistory> getUnconfirmedReviews() throws SQLException {
        return adminDAO.getUnconfirmedReviews();
    }

    public void confirmRating(int ratingIdToConfirm, int adminID) throws SQLException {
        adminDAO.confirmRating(ratingIdToConfirm, adminID);
    }

    public void deleteReview(int reviewIdToDelete) throws SQLException {
        adminDAO.deleteReviewById(reviewIdToDelete);
    }
}
