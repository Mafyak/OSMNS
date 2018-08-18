package by.epam.dao;

import by.epam.entity.Company;
import by.epam.entity.User;
import by.epam.exception.DAOException;
import by.epam.pool.ConnectionPool;
import by.epam.utils.service.CompanyService;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CompanyDAO extends AbstractDAO {

    private static final Logger LOG = Logger.getLogger(CompanyDAO.class);
    private final static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("resources/mysqlStatements");
    private String queryGetCompNameCollis = RESOURCE_BUNDLE.getString("GET_COMPANY_NAME_COLLISION");
    private String queryFind2ndcompId = RESOURCE_BUNDLE.getString("GET_DUPLICATE_COMPANY_ID");
    private String fixCompDuplInHistory = RESOURCE_BUNDLE.getString("FIX_DUPLICATE_COMPANY_HISTORY");
    private String fixCompDuplInHrhead = RESOURCE_BUNDLE.getString("FIX_DUPLICATE_COMPANY_HRHEAD");
    private String queryDeleteCompById = RESOURCE_BUNDLE.getString("DELETE_COMPANY_BY_ID");
    private String queryGetCompById = RESOURCE_BUNDLE.getString("GET_COMPANY_INFO");
    private String queryGetCompByOffId = RESOURCE_BUNDLE.getString("GET_COMPANY_BY_OFF_ID");
    private String updateCompanyInfo = RESOURCE_BUNDLE.getString("UPDATE_COMPANY_INFO");
    private String queryAddCompany = RESOURCE_BUNDLE.getString("ADD_COMPANY");
    private String querySetMyCompany = RESOURCE_BUNDLE.getString("SET_MY_COMPANY");
    private String queryGetCompanyId = RESOURCE_BUNDLE.getString("GET_COMPANY_ID_BY_NAME");
    private String queryGetCompanyIdByOffId = RESOURCE_BUNDLE.getString("GET_COMPANY_ID_BY_OFF_ID");

    public List<Company> getCompNameCollisions() throws DAOException {
        Connection conn = ConnectionPool.getInstance().getConnection();
        List<Company> list = new ArrayList<>();
        try (Statement ps = conn.createStatement();
             ResultSet rs = ps.executeQuery(queryGetCompNameCollis)) {
            while (rs.next()) {
                Company company = new Company();
                company.setId(rs.getInt("idCompany"));
                company.setName(rs.getString("name"));
                company.setNiche(rs.getString("niche"));
                company.setLocation(rs.getString("location"));
                company.setHeadcount(rs.getInt("headCount"));
                company.setCompanyOfficialId(rs.getInt("offCompId"));
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

    public void mergeCompByInnerId(int compId) throws DAOException {
        int duplId = (int) executeForSingleResult(queryFind2ndcompId, compId, compId);
        Connection conn = ConnectionPool.getInstance().getConnection();
        try {
            conn.setAutoCommit(false);
            updateQuery(fixCompDuplInHistory, compId, duplId);
            updateQuery(fixCompDuplInHrhead, compId, duplId);
            Company properComp = getCompById(compId);
            Company donorComp = getCompById(duplId);
            CompanyService companyService = new CompanyService();
            companyService.mergeCompanies(properComp, donorComp);
            updateQuery(updateCompanyInfo, properComp.getNiche(), properComp.getLocation(), properComp.getHeadcount(), compId);
            updateQuery(queryDeleteCompById, duplId);
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                LOG.info("Can't rollback changes");
                throw new DAOException("Can't rollback changes");
            }
            LOG.info("Can't add data changes. Rollback was made");
            throw new DAOException("Can't add data changes. Rollback was made");
        } finally {
            ConnectionPool.getInstance().returnConnection(conn);
        }
    }

    private Company getCompById(int compId) throws DAOException {
        Connection conn = ConnectionPool.getInstance().getConnection();
        Company company;
        try (PreparedStatement ps = conn.prepareStatement(queryGetCompById)) {
            ps.setInt(1, compId);
            try (ResultSet rs = ps.executeQuery()) {
                company = new Company();
                while (rs.next()) {
                    company.setId(compId);
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

    public Company getCompanyByOffId(int compOffId) throws DAOException {
        Connection conn = ConnectionPool.getInstance().getConnection();
        Company company;
        try (PreparedStatement ps = conn.prepareStatement(queryGetCompByOffId)) {
            ps.setInt(1, compOffId);
            try (ResultSet rs = ps.executeQuery()) {
                company = new Company();
                while (rs.next()) {
                    company.setId(rs.getInt("idCompany"));
                    company.setName(rs.getString("name"));
                    company.setNiche(rs.getString("niche"));
                    company.setLocation(rs.getString("location"));
                    company.setHeadcount(rs.getInt("headCount"));
                    company.setCompanyOfficialId(compOffId);
                }
            }
        } catch (SQLException e) {
            LOG.info("SQLException in AdminDAO method getCompById()");
            throw new DAOException("Detected SQLexception in AdminDAO method getCompById()", e);
        } finally {
            ConnectionPool.getInstance().returnConnection(conn);
        }
        return company;
    }

    //doesn't work properly - doesn't change company id in history.
    public void removeCompByInnerId(int compId) throws DAOException {
        int duplId = (int) executeForSingleResult(queryFind2ndcompId, compId, compId);
        LOG.info("Test 1");
        updateQuery(fixCompDuplInHistory, duplId, compId);
        LOG.info("Test 2");
        updateQuery(fixCompDuplInHrhead, duplId, compId);
        LOG.info("Test 3");
        updateQuery(queryDeleteCompById, compId);
    }

    int getCompanyIdByOfficialId(int companyOfficialId) throws DAOException {
        if (executeForSingleResult(queryGetCompanyIdByOffId, companyOfficialId) == null) {
            return 0;
        } else return (int) executeForSingleResult(queryGetCompanyIdByOffId, companyOfficialId);
    }

    public int getCompanyIdByName(String companyName) throws DAOException {
        return (int) executeForSingleResult(queryGetCompanyId, companyName);
    }

    public void addCompany(Company companyName) throws DAOException {
        executeQuery(queryAddCompany, companyName.getName(), companyName.getNiche(), companyName.getLocation(),
                companyName.getHeadcount(), companyName.getCompanyOfficialId());
        LOG.info("New company is added:" + companyName.getName());
    }

    public void setMyCompany(User hr, Company companyName) throws DAOException {
        updateQuery(querySetMyCompany, getCompanyByOffId(companyName.getCompanyOfficialId()).getId(), hr.getId());
    }

}
