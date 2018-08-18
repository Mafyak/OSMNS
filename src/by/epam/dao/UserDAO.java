package by.epam.dao;

import by.epam.entity.Company;
import by.epam.entity.User;
import by.epam.entity.UserHistory;
import by.epam.entity.UserType;
import by.epam.exception.DAOException;
import by.epam.pool.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;

public class UserDAO extends AbstractDAO<User> {

    private final static Logger LOG = Logger.getLogger("UserDAO");
    private final static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("resources/mysqlStatements");
    private String entryQuery = RESOURCE_BUNDLE.getString("GET_USER_ENTITY");
    private String getAdminInfo = RESOURCE_BUNDLE.getString("GET_ADMIN_ENTITY");
    private String queryRegister = RESOURCE_BUNDLE.getString("REGISTER_COMMAND");
    private String queryAddHRHEADInfo = RESOURCE_BUNDLE.getString("ADD_HRHEAD_INFO_COMMAND");
    private String queryUpdateHrheadInfo = RESOURCE_BUNDLE.getString("UPDATE_HRHEAD_INFO");
    private String queryUpdateHrheadCred = RESOURCE_BUNDLE.getString("UPDATE_HRHEAD_CRED");
    private String queryGetUserId = RESOURCE_BUNDLE.getString("GET_USER_ID");
    private String querySetNewPassword = RESOURCE_BUNDLE.getString("SET_NEW_PASS");
    private String queryGetEmplBySSN = RESOURCE_BUNDLE.getString("GET_EMPLOYEE_BY_SSN");
    private String queryGetEmplIdBySSN = RESOURCE_BUNDLE.getString("GET_EMPLOYEE_ID_BY_SSN");
    private String queryGetEmplHistById = RESOURCE_BUNDLE.getString("GET_EMPLOYEE_HISTORY_BY_ID");
    private String queryAddReview = RESOURCE_BUNDLE.getString("ADD_REVIEW");
    private String queryGetHrByName = RESOURCE_BUNDLE.getString("GET_HR_BY_NAME");
    private String queryGetHrById = RESOURCE_BUNDLE.getString("GET_HR_BY_ID");
    private String queryAddEmployeeBySSN = RESOURCE_BUNDLE.getString("ADD_EMPLOYEE_BY_SSN");
    private String getReviewsOfSingleHrById = RESOURCE_BUNDLE.getString("GET_REVIEWS_OF_SINGLE_HR_BY_ID");
    private String getAVGReviewsOfSingleHrById = RESOURCE_BUNDLE.getString("GET_AVG_REVIEWS_OF_SINGLE_HR_BY_ID");
    private String querySetCompanyId = RESOURCE_BUNDLE.getString("SET_COMPANY_TO_USER");

    public UserDAO() {
    }

    public User login(Object... params) throws DAOException {
        Connection conn = ConnectionPool.getInstance().getConnection();
        User user;
        try (PreparedStatement ps = conn.prepareStatement(entryQuery)) {
            ps.setObject(1, params[0]); //login
            ps.setObject(2, params[1]); //password
            try (ResultSet rs = ps.executeQuery()) {
                user = new User();
                LOG.info("Login check point 1");
                Company company;
                while (rs.next()) {
                    UserType role = rs.getInt("role") == 0 ? UserType.ADMIN : UserType.HR;
                    user.setId(rs.getInt("idLogin"));
                    user.setType(role);
                    LOG.info("Login check point 2");
                    if (role == UserType.HR) {
                        user.setEmail(rs.getString("email"));
                        user.setPass(rs.getString("pass"));
                        user.setfName(rs.getString("fName"));
                        user.setlName(rs.getString("lName"));
                        LOG.info("Login check point 3");
                        company = new Company();
                        company.setName(rs.getString("name"));
                        company.setId(rs.getInt("idCompany"));
                        company.setNiche(rs.getString("niche"));
                        LOG.info("Login check point 4");
                        company.setLocation(rs.getString("location"));
                        company.setHeadcount(rs.getInt("headcount"));
                        company.setCompanyOfficialId(rs.getInt("offCompId"));
                        user.setCompany(company);
                        LOG.info("Login check point 5");
                    }
                    if (role == UserType.ADMIN) {
                        String fullName = executeForSingleResult(getAdminInfo, user.getId()).toString();
                        user.setfName(fullName);
                        LOG.info("Admin: " + user.getfName() + ", id:" + user.getId());
                    }
                }
                return user;
            }
        } catch (SQLException e) {
            LOG.info("SQL Exception during login entity retrieval from db" + e);
            throw new DAOException("User can't be found", e);
        } finally {
            ConnectionPool.getInstance().returnConnection(conn);
        }
    }


    public void setNewPassword(String login, String pass) throws DAOException {
        if ((updateQuery(querySetNewPassword, pass, login)) == 0)
            throw new DAOException("Such login does not exist");
    }

    public void addNewEmployee(User user) throws DAOException {
        executeQuery(queryAddEmployeeBySSN);
    }

    private void registerUser(User user) throws DAOException {
        executeQuery(queryRegister, user.getEmail(), user.getPass());
    }

    private void setUserIdByEmailAndPass(User user) throws DAOException {
        int userId = (int) executeForSingleResult(queryGetUserId, user.getEmail(), user.getPass());
        user.setId(userId);
    }

    public void updateUserInfo(User user) throws DAOException {
        updateQuery(queryUpdateHrheadInfo, user.getfName(), user.getlName(), user.getCompany().getCompanyOfficialId(), user.getId());
        updateQuery(queryUpdateHrheadCred, user.getEmail(), user.getId());
    }

    private void addHrHeadInfo(User user) throws DAOException {
        updateQuery(queryAddHRHEADInfo, user.getId(), user.getfName(), user.getmName(), user.getlName());
    }

    public void register(User user) throws DAOException {
        registerUser(user);
        setUserIdByEmailAndPass(user);
        addHrHeadInfo(user);
        LOG.info("New user is added:" + user.getEmail());
    }

    private int getEmployeeIdBySSN(int SSN) throws DAOException {
        if (executeForSingleResult(queryGetEmplIdBySSN, SSN) == null) {
            return 0;
        } else return (int) executeForSingleResult(queryGetEmplIdBySSN, SSN);
    }

    public User getEmployeeBySSN(int SSN) throws DAOException {
        Connection conn = ConnectionPool.getInstance().getConnection();
        User employee = new User();
        try (PreparedStatement ps = conn.prepareStatement(queryGetEmplBySSN)) {
            ps.setInt(1, SSN);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    employee.setId(rs.getInt("idEmployee"));
                    employee.setfName(rs.getString("fName"));
                    employee.setlName(rs.getString("lName"));
                    employee.setSSN(SSN);
                }
            }
            LOG.info("Employee " + employee.getfName() + " is retrieved from db.");
        } catch (SQLException e) {
            LOG.info("SQL Exception during getEmployeeBySSN method in UserDAO" + e);
            throw new DAOException("Can't get user Id by SSN", e);
        } finally {
            ConnectionPool.getInstance().returnConnection(conn);
        }
        return employee;
    }

    public User getEntityBySSN(int SSN) throws DAOException {
        User employee = getEmployeeBySSN(SSN);
        LOG.info("Passed getEntityBySSN in UserDAO, id: " + employee.getId());
        List<UserHistory> userHistory = getReviews(queryGetEmplHistById, employee.getId());
        for (UserHistory hist : userHistory)
            employee.addHistory(hist);
        return employee;
    }

    public void setCompanyId(Company company, User user) throws DAOException {
        updateQuery(querySetCompanyId, company.getId(), user.getId());
    }

    public void registerEmployee(User employee) throws DAOException {
        updateQuery(queryAddEmployeeBySSN, employee.getfName(), employee.getlName(), employee.getmName(), employee.getSSN());
        employee.setId(getEmployeeIdBySSN(employee.getSSN()));
    }

    public void addReview(User hrHead, User employee, UserHistory userHistory) throws DAOException {
        double aveRating = (userHistory.getRating1() + userHistory.getRating2() +
                userHistory.getRating3() + userHistory.getRating4() + userHistory.getRating5()) / 5.0;
        int getHireAgain = userHistory.getHireAgain();
        Company company = hrHead.getCompany();
        LOG.info("Company data: " + company);
        CompanyDAO companyDAO = new CompanyDAO();
        int companyId = companyDAO.getCompanyIdByOfficialId(company.getCompanyOfficialId());
        LOG.info("company id: " + companyId + ", hr head id: " + hrHead.getId());
        updateQuery(queryAddReview, companyId, hrHead.getId(), employee.getId(), userHistory.getYearEmployed(),
                userHistory.getYearTerminated(), userHistory.getRating1(), userHistory.getRating2(),
                userHistory.getRating3(), userHistory.getRating4(), userHistory.getRating5(), aveRating, getHireAgain);
    }

    public User getHrById(int hrId) throws DAOException {
        return getHrWithCompanyDataList(queryGetHrById, hrId).get(0);
    }

    public List<User> getHrByName(String fName, String lName) throws DAOException {
        return getHrWithCompanyDataList(queryGetHrByName, fName, lName);
    }

    public List<Integer> getReviewsRateForHrById(int idHR) throws DAOException {
        Connection conn = ConnectionPool.getInstance().getConnection();
        List<Integer> ratingList = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(getReviewsOfSingleHrById)) {
            ps.setInt(1, idHR);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ratingList.add(rs.getInt("rating1"));
                    ratingList.add(rs.getInt("rating2"));
                    ratingList.add(rs.getInt("rating3"));
                    ratingList.add(rs.getInt("rating4"));
                    ratingList.add(rs.getInt("rating5"));
                }
            }
        } catch (SQLException e) {
            LOG.info("SQL Exception during getReviewsRateForHrById method in UserDAO");
            throw new DAOException("SQL Exception during getReviewsRateForHrById command execution", e);
        } finally {
            ConnectionPool.getInstance().returnConnection(conn);
        }
        return ratingList;
    }

    public List<Double> getAVGReviewsRateForHrById(int idHR) throws DAOException {
        Connection conn = ConnectionPool.getInstance().getConnection();
        List<Double> ratingAVGSList = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(getAVGReviewsOfSingleHrById)) {
            ps.setInt(1, idHR);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ratingAVGSList.add(rs.getDouble("totalRating"));
                }
            }
        } catch (SQLException e) {
            LOG.info("SQL Exception during getAVGReviewsRateForHrById method in UserDAO");
            throw new DAOException("SQL Exception during getAVGReviewsRateForHrById command execution", e);
        } finally {
            ConnectionPool.getInstance().returnConnection(conn);
        }
        return ratingAVGSList;
    }
}