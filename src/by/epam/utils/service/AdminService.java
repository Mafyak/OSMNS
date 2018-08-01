package by.epam.utils.service;

import by.epam.dao.AdminDAO;
import by.epam.entity.UserHistory;
import by.epam.exception.DAOException;
import by.epam.exception.ServiceException;

import java.util.List;

import org.apache.log4j.Logger;

public class AdminService {

    private AdminDAO adminDAO = new AdminDAO();
    private static final Logger LOG = Logger.getLogger(AdminService.class);

    public List<UserHistory> getAllReviews() throws ServiceException {
        try {
            return adminDAO.getAllReviews();
        } catch (DAOException e) {
            LOG.info("DAOException in AdminService method getAllReviews()");
            throw new ServiceException("Detected DAOException in AdminService method getAllReviews()", e);
        }
    }

    public List<UserHistory> getPagedReviews(int pageNumber) throws ServiceException {
        try {
            return adminDAO.getPagedReviews(pageNumber);
        } catch (DAOException e) {
            LOG.info("DAOException in AdminService method getPagedReviews()");
            throw new ServiceException("Detected DAOException in AdminService method getPagedReviews()", e);
        }
    }

    public Long getReviewsPoolSize() throws ServiceException {
        try {
            return adminDAO.getReviewsPoolSize();
        } catch (DAOException e) {
            LOG.info("DAOException in AdminService method getReviewsPoolSize()");
            throw new ServiceException("Detected DAOException in AdminService method getReviewsPoolSize()", e);
        }
    }

    public List<UserHistory> getUnconfirmedReviews() throws ServiceException {
        try {
            return adminDAO.getUnconfirmedReviews();
        } catch (DAOException e) {
            LOG.info("DAOException in AdminService method getUnconfirmedReviews()");
            throw new ServiceException("Detected DAOException in AdminService method getUnconfirmedReviews()", e);
        }
    }

    public void confirmRating(int ratingIdToConfirm, int adminID) throws ServiceException {
        try {
            adminDAO.confirmRating(ratingIdToConfirm, adminID);
        } catch (DAOException e) {
            LOG.info("DAOException in AdminService method confirmRating()");
            throw new ServiceException("Detected DAOException in AdminService method confirmRating()", e);
        }
    }

    public void deleteReview(int reviewIdToDelete) throws ServiceException {
        try {
            adminDAO.deleteReviewById(reviewIdToDelete);
        } catch (DAOException e) {
            LOG.info("DAOException in AdminService method deleteReview()");
            throw new ServiceException("Detected DAOException in AdminService method deleteReview()", e);
        }
    }

    public void removeHrById(int hrId) throws ServiceException {
        try {
            adminDAO.removeHrById(hrId);
        } catch (DAOException e) {
            LOG.info("Error while removing HR:" + e);
        }
    }

}
