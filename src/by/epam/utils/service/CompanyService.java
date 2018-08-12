package by.epam.utils.service;

import by.epam.dao.CompanyDAO;
import by.epam.entity.Company;
import by.epam.entity.User;
import by.epam.exception.DAOException;
import by.epam.exception.ServiceException;
import org.apache.log4j.Logger;

import java.util.List;

public class CompanyService {

    private static final Logger LOG = Logger.getLogger(CompanyService.class);

    private CompanyDAO companyDAO = new CompanyDAO();

    public Company getCompanyByOffId(int companyOffId) throws ServiceException {
        Company company;
        try {
            company = companyDAO.getCompanyByOffId(companyOffId);
        } catch (DAOException e) {
            LOG.info("DAOException in AdminService method getCompanyByOffId()");
            throw new ServiceException("Detected DAOException in AdminService method getCompanyByOffId()", e);
        }
        return company;
    }

    public void addCompany(Company userCompany) throws ServiceException {
        try {
            companyDAO.addCompany(userCompany);
        } catch (DAOException e) {
            LOG.info("DAOException in UserService method addCompany()");
            throw new ServiceException("Detected DAOException in UserService method addCompany()", e);
        }
    }


    public void setMyCompany(User user, Company userCompany) throws ServiceException {
        try {
            companyDAO.setMyCompany(user, userCompany);
        } catch (DAOException e) {
            LOG.info("DAOException in UserService method setMyCompany()");
            throw new ServiceException("Detected DAOException in UserService method setMyCompany()", e);
        }
    }


    public List<Company> getCompNameCollisions() throws ServiceException {
        try {
            return companyDAO.getCompNameCollisions();
        } catch (DAOException e) {
            LOG.info("DAOException in AdminService method getCompNameCollisions()");
            throw new ServiceException("Detected DAOException in AdminService method getCompNameCollisions()", e);
        }
    }

    public void mergeCompByInnerId(int companyId) throws ServiceException {
        try {
            companyDAO.mergeCompByInnerId(companyId);
        } catch (DAOException e) {
            LOG.info("DAOException in AdminService method mergeCompByInnerId()");
            throw new ServiceException("Detected DAOException in AdminService method mergeCompByInnerId()", e);
        }
    }

    public void removeCompByInnerId(int companyId) throws ServiceException {
        try {
            companyDAO.removeCompByInnerId(companyId);
        } catch (DAOException e) {
            LOG.info("DAOException in AdminService method removeCompByInnerId()");
            throw new ServiceException("Detected DAOException in AdminService method removeCompByInnerId()", e);
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
