package by.epam.dao;

import by.epam.entity.Company;
import by.epam.entity.User;
import by.epam.entity.UserHistory;
import by.epam.entity.UserType;
import by.epam.exception.DAOException;
import by.epam.pool.ConnectionPool;
import oracle.jdbc.proxy.annotation.Pre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class UserDAO extends AbstractDAO<User> {

    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("resources/mysqlStatements");

    private String entryQuery = resourceBundle.getString("GET_USER_ENTITY");
    private String getAdminInfo = resourceBundle.getString("GET_ADMIN_ENTITY");
    private String queryRegister = resourceBundle.getString("REGISTER_COMMAND");
    private String queryAddHRHEADInfo = resourceBundle.getString("ADD_HRHEAD_INFO_COMMAND");
    private String queryGetUserId = resourceBundle.getString("GET_USER_ID");
    private String queryGetEmplBySSN = resourceBundle.getString("GET_EMPLOYEE_BY_SSN");
    private String queryGetEmplIdBySSN = resourceBundle.getString("GET_EMPLOYEE_ID_BY_SSN");
    private String queryGetEmplHistById = resourceBundle.getString("GET_EMPLOYEE_HISTORY_BY_ID");
    private String queryAddCompany = resourceBundle.getString("ADD_COMPANY");
    private String queryAddReview = resourceBundle.getString("ADD_REVIEW");
    private String queryGetHrByName = resourceBundle.getString("GET_HR_BY_NAME");
    private String queryGetHrById = resourceBundle.getString("GET_HR_BY_ID");
    private String queryAddEmployeeBySSN = resourceBundle.getString("ADD_EMPLOYEE_BY_SSN");
    private String queryGetCompanyId = resourceBundle.getString("GET_COMPANY_ID_BY_NAME");
    private String queryGetCompanyIdByOffId = resourceBundle.getString("GET_COMPANY_ID_BY_OFF_ID");
    private String getReviewsOfSingleHrById = resourceBundle.getString("GET_REVIEWS_OF_SINGLE_HR_BY_ID");
    private String getAVGReviewsOfSingleHrById = resourceBundle.getString("GET_AVG_REVIEWS_OF_SINGLE_HR_BY_ID");
    private String querySetCompanyId = resourceBundle.getString("SET_COMPANY_TO_USER");

    private static Logger LOG = Logger.getLogger("UserDAO");

    public UserDAO() {
    }

    public User login(Object... params) throws DAOException {
        Connection conn = ConnectionPool.getInstance().getConnection();
        User user = null;
        try (PreparedStatement ps = conn.prepareStatement(entryQuery)) {
            ps.setObject(1, params[0]); //login
            ps.setObject(2, params[1]); //password
            try (ResultSet rs = ps.executeQuery()) {
                user = new User();
                while (rs.next()) {
                    UserType role = rs.getInt("role") == 0 ? UserType.ADMIN : UserType.HR;
                    user.setId(rs.getInt("idLogin"));
                    user.setType(role);
                    if (role == UserType.HR) {
                        user.setEmail(rs.getString("email"));
                        user.setPass(rs.getString("pass"));
                        user.setfName(rs.getString("fName"));
                        user.setlName(rs.getString("lName"));
                        user.setCompany(rs.getString("name"));
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

    public void registerUser(User user) throws DAOException {
        executeQuery(queryRegister, user.getEmail(), user.getPass());
    }

    public void setUserIdByEmailAndPass(User user) throws DAOException {
        int userId = (int) executeForSingleResult(queryGetUserId, user.getEmail(), user.getPass());
        user.setId(userId);
    }

    public void addHrHeadInfo(User user) throws DAOException {
        updateQuery(queryAddHRHEADInfo, user.getId(), user.getfName(), user.getmName(), user.getlName());
    }

    public void register(User user) throws DAOException {
        registerUser(user);
        setUserIdByEmailAndPass(user);
        addHrHeadInfo(user);
        LOG.info("New user is added:" + user.getEmail());
    }

    public int getEmployeeIdBySSN(int SSN) throws DAOException {
        if (executeForSingleResult(queryGetEmplIdBySSN, SSN) == null) {
            return 0;
        } else return (int) executeForSingleResult(queryGetEmplIdBySSN, SSN);
    }

    public User getEmployeeBySSN(int SSN, Connection conn) throws DAOException {
        User employee = new User();
        try (PreparedStatement ps = conn.prepareStatement(queryGetEmplBySSN)) {
            ps.setInt(1, SSN);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    employee.setId(rs.getInt(1));
                    employee.setfName(rs.getString(2));
                    employee.setlName(rs.getString(3));
                }
            }
        } catch (SQLException e) {
            LOG.info("SQL Exception during getEmployeeBySSN method in UserDAO" + e);
            throw new DAOException("Can't get user Id by SSN", e);
        }
        return employee;
    }

    public User getEntityBySSN(int SSN) throws DAOException {
        Connection conn = ConnectionPool.getInstance().getConnection();
        User employee = null;
        UserHistory userHistory;
        try (PreparedStatement ps = conn.prepareStatement(queryGetEmplHistById)) {
            employee = getEmployeeBySSN(SSN, conn);
            ps.setInt(1, employee.getId());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    userHistory = new UserHistory();
                    userHistory.setCompany(rs.getString("name"));
                    userHistory.setIdHR(rs.getInt("idHR"));
                    userHistory.setYearEmployed(rs.getInt("yearEmployed"));
                    userHistory.setYearTerminated(rs.getInt("yearTerminated"));
                    userHistory.setRating1(rs.getInt("rating1"));
                    userHistory.setRating2(rs.getInt("rating2"));
                    userHistory.setRating3(rs.getInt("rating3"));
                    userHistory.setRating4(rs.getInt("rating4"));
                    userHistory.setRating5(rs.getInt("rating5"));
                    userHistory.setHireAgain(rs.getInt("hireAgain"));
                    employee.addHistory(userHistory);
                }
            }
        } catch (SQLException e) {
            LOG.info("SQL Exception during getEntityBySSN method in UserDAO");
            throw new DAOException("SQL Exception during getEntityBySSN command execution", e);
        } finally {
            ConnectionPool.getInstance().returnConnection(conn);
        }
        return employee;
    }

    public int getCompanyIdByOfficialId(int companyOfficialId) throws DAOException {
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

    public void setCompanyId(Company company, User user) throws DAOException {
        updateQuery(querySetCompanyId, company.getCompanyInnerId(), user.getId());
    }

    public void addMyCompany(User user, Company companyName) throws DAOException {
        addCompany(companyName);
        LOG.info("New company is added:" + companyName.getName());
        companyName.setCompanyInnerId(getCompanyIdByName(companyName.getName()));
        setCompanyId(companyName, user);
    }

    public void registerEmployee(User employee) throws DAOException {
        updateQuery(queryAddEmployeeBySSN, employee.getfName(), employee.getlName(), employee.getSSN());
    }

    public void addReview(User hrHead, User employee, UserHistory userHistory) throws DAOException {
        //tries to find employee with this SSN
        employee.setId(getEmployeeIdBySSN(employee.getSSN()));
        if (employee.getId() == 0) {
            LOG.info("Employee info is not found in DB, creating new employee...");
            //creates new employee if not found
            registerEmployee(employee);
            LOG.info("New employee is created");
            employee.setId(getEmployeeIdBySSN(employee.getSSN()));
            LOG.info("Employee id is:" + employee.getId());
        }
        int companyId = getCompanyIdByOfficialId(userHistory.getIdCompany());
        if (companyId == 0) {
            LOG.info("Company with official ID " + userHistory.getIdCompany() +
                    "can't be found. Creating new one...");
            Company company = new Company();
            company.setName(userHistory.getCompany());
            company.setLocation("");
            company.setHeadcount(0);
            company.setNiche("");
            company.setCompanyOfficialId(userHistory.getIdOfficialCompany());
            addCompany(company);
            LOG.info("Company " + company.getName() + " was added.");
            company.setCompanyInnerId(getCompanyIdByName(company.getName()));
            companyId = company.getCompanyInnerId();
        }
        addReviewHelp(companyId, hrHead, employee, userHistory);
    }

    public void addReviewHelp(int companyId, User hrHead, User employee, UserHistory userHistory) throws DAOException {
        double aveRating = (userHistory.getRating1() + userHistory.getRating2() +
                userHistory.getRating3() + userHistory.getRating4() + userHistory.getRating5()) / 5.0;
        int getHireAgain = (userHistory.getHireAgain().equals("Yes") ? 1 : 0);
        updateQuery(queryAddReview, companyId, hrHead.getId(), employee.getId(), userHistory.getYearEmployed(),
                userHistory.getYearTerminated(), userHistory.getRating1(), userHistory.getRating2(),
                userHistory.getRating3(), userHistory.getRating4(), userHistory.getRating5(), aveRating, getHireAgain);
    }

    public User getHrById(int hrId) throws DAOException {
        Connection conn = ConnectionPool.getInstance().getConnection();
        User user = null;
        try (PreparedStatement ps = conn.prepareStatement(queryGetHrById)) {
            ps.setInt(1, hrId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    user = new User();
                    user.setId(rs.getInt(1));
                    user.setfName(rs.getString(2));
                    user.setmName(rs.getString(3));
                    user.setlName(rs.getString(4));
                    user.setCompany(rs.getString(5));
                }
            }
        } catch (SQLException e) {
            LOG.info("SQL Exception during getHrById method in UserDAO");
            throw new DAOException("SQL Exception during getHrById command execution", e);
        } finally {
            ConnectionPool.getInstance().returnConnection(conn);
        }
        return user;
    }

    public List<User> getHrByName(String fName, String lName) throws DAOException {
        Connection conn = ConnectionPool.getInstance().getConnection();
        List<User> hrList = null;
        try (PreparedStatement ps = conn.prepareStatement(queryGetHrByName)) {
            ps.setString(1, fName);
            ps.setString(2, lName);
            try (ResultSet rs = ps.executeQuery()) {
                hrList = new ArrayList<>();
                User hr = null;
                while (rs.next()) {
                    hr = new User();
                    hr.setId(rs.getInt(1));
                    hr.setfName(rs.getString(2));
                    hr.setmName(rs.getString(3));
                    hr.setlName(rs.getString(4));
                    hr.setCompany(rs.getString(5));
                    hrList.add(hr);
                }
            }
        } catch (SQLException e) {
            LOG.info("SQL Exception during getHrByName method in UserDAO");
            throw new DAOException("SQL Exception during getHrByName command execution", e);
        } finally {
            ConnectionPool.getInstance().returnConnection(conn);
        }
        return hrList;
    }

    public ArrayList<Integer> getReviewsRateForHrById(int idHR) throws DAOException {
        Connection conn = ConnectionPool.getInstance().getConnection();
        ArrayList<Integer> ratingList = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(getReviewsOfSingleHrById)) {
            ps.setInt(1, idHR);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ratingList.add(rs.getInt(1));
                    ratingList.add(rs.getInt(2));
                    ratingList.add(rs.getInt(3));
                    ratingList.add(rs.getInt(4));
                    ratingList.add(rs.getInt(5));
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

    public ArrayList<Double> getAVGReviewsRateForHrById(int idHR) throws DAOException {
        Connection conn = ConnectionPool.getInstance().getConnection();
        ArrayList<Double> ratingAVGSList = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(getAVGReviewsOfSingleHrById)) {
            ps.setInt(1, idHR);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ratingAVGSList.add(rs.getDouble(1));
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