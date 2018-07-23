package by.epam.service;

import by.epam.dao.AdminDAO;
import by.epam.entity.Company;
import by.epam.entity.UserHistory;
import by.epam.exception.DAOException;
import by.epam.exception.ServiceException;

import java.util.List;
import java.util.logging.Logger;

public class AdminService {

    private AdminDAO adminDAO = new AdminDAO();
    private static Logger LOG = Logger.getLogger("AdminService");

    public List<UserHistory> getAllReviews() throws ServiceException {
        try {
            return adminDAO.getAllReviews();
        } catch (DAOException e) {
            LOG.info("DAOException in AdminService method getAllReviews()");
            throw new ServiceException("Detected DAOException in AdminService method getAllReviews()", e);
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

    public List<Company> getCompNameCollisions() throws ServiceException {
        try {
            return adminDAO.getCompNameCollisions();
        } catch (DAOException e) {
            LOG.info("DAOException in AdminService method getCompNameCollisions()");
            throw new ServiceException("Detected DAOException in AdminService method getCompNameCollisions()", e);
        }
    }

    public void mergeCompByInnerId(int companyId) throws ServiceException {
        try {
            adminDAO.mergeCompByInnerId(companyId);
        } catch (DAOException e) {
            LOG.info("DAOException in AdminService method mergeCompByInnerId()");
            throw new ServiceException("Detected DAOException in AdminService method mergeCompByInnerId()", e);
        }
    }

    public void removeCompByInnerId(int companyId) throws ServiceException {
        try {
            adminDAO.removeCompByInnerId(companyId);
        } catch (DAOException e) {
            LOG.info("DAOException in AdminService method removeCompByInnerId()");
            throw new ServiceException("Detected DAOException in AdminService method removeCompByInnerId()", e);
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

    public void mergeCompanies(Company properComp, Company donorComp) {
        if (properComp.getHeadcount() < 1)
            properComp.setHeadcount(donorComp.getHeadcount());
        if (properComp.getLocation().isEmpty())
            properComp.setLocation(donorComp.getLocation());
        if (properComp.getNiche().isEmpty())
            properComp.setNiche(donorComp.getNiche());
    }
}
