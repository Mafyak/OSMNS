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
import java.util.logging.Logger;

public class UserDAO extends AbstractDAO<User> {

    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("resources/mysqlStatements");
    private String entryQuery = resourceBundle.getString("GET_USER_ENTITY");
    private String queryRegister = resourceBundle.getString("REGISTER_COMMAND");
    private String queryAddHRHEADInfo = resourceBundle.getString("ADD_HRHEAD_INFO_COMMAND");
    private String queryGetUserId = resourceBundle.getString("GET_USER_ID");
    private String queryGetEmplBySSN = resourceBundle.getString("GET_EMPLOYEE_ID_BY_SSN");
    private String queryGetEmplHistById = resourceBundle.getString("GET_EMPLOYEE_HISTORY_BY_ID");
    private String queryAddCompany = resourceBundle.getString("ADD_COMPANY");
    private String queryAddReview = resourceBundle.getString("ADD_REVIEW");
    private String queryGetHrByName = resourceBundle.getString("GET_HR_BY_NAME");
    private String queryAddEmployeeBySSN = resourceBundle.getString("ADD_EMPLOYEE_BY_SSN");
    private String queryGetCompanyId = resourceBundle.getString("GET_COMPANY_ID_BY_NAME");
    private String queryGetCompanyIdByOffId = resourceBundle.getString("GET_COMPANY_ID_BY_OFF_ID");
    private String queryGetEmployeeIdBySSN = resourceBundle.getString("GET_EMPLOYEE_ID_BY_SSN");
    private String getReviewsOfSingleHrById = resourceBundle.getString("GET_REVIEWS_OF_SINGLE_HR_BY_ID");
    private String getAVGReviewsOfSingleHrById = resourceBundle.getString("GET_AVG_REVIEWS_OF_SINGLE_HR_BY_ID");
    private String querySetCompanyId = resourceBundle.getString("SET_COMPANY_TO_USER");

    private static Logger LOG = Logger.getLogger("UserDAO");

    public UserDAO() {
    }

    public User getEntity(Object... params) {
        Connection conn = ConnectionPool.getInstance().getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        User user = null;
        try {
            ps = conn.prepareStatement(entryQuery);
            ps.setObject(1, params[0]); //login
            ps.setObject(2, params[1]); //password
            rs = ps.executeQuery();
            user = new User();
            while (rs.next()) {
                UserType type = rs.getInt("role") == 0 ? UserType.ADMIN : UserType.HR;
                user.setEmail(rs.getString("email"));
                user.setPass(rs.getString("pass"));
                user.setId(rs.getInt("idLogin"));
                user.setType(type);
                user.setfName(rs.getString("fName"));
                user.setlName(rs.getString("lName"));
                user.setCompany(rs.getString("name"));
            }
            return user;
        } catch (SQLException e) {
            LOG.info("SQL Exception during login entity retrieval from db" + e);
            return user;
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

    public void addHrHeadInfo(User user) throws SQLException, DAOException {
        updateQuery(queryAddHRHEADInfo, user.getId(), user.getfName(), user.getmName(), user.getlName());
    }

    public void register(User user) throws DAOException {

        try {
            registerUser(user);
            setUserIdByEmailAndPass(user);
            addHrHeadInfo(user);
            LOG.info("New user is added:" + user.getEmail());
        } catch (SQLException e) {
            LOG.info("SQL Exception during registration command execution" + e);
            throw new DAOException("error during new user registrations", e);
        }
    }

    public User getEmployeeBySSN(int SSN, Connection conn) throws SQLException {
        ResultSet rs = null;
        PreparedStatement ps = null;
        User employee = new User();
        ps = conn.prepareStatement(queryGetEmplBySSN);
        ps.setInt(1, SSN);
        rs = ps.executeQuery();
        while (rs.next()) {
            employee.setId(rs.getInt(1));
            employee.setfName(rs.getString(2));
            employee.setlName(rs.getString(3));
        }
        return employee;
    }

    public User getEntityBySSN(int SSN) throws DAOException {
        Connection conn = ConnectionPool.getInstance().getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        User employee = null;
        UserHistory userHistory;
        try {
            employee = getEmployeeBySSN(SSN, conn);
            ps = conn.prepareStatement(queryGetEmplHistById);
            ps.setInt(1, employee.getId());
            rs = ps.executeQuery();
            while (rs.next()) {
                userHistory = new UserHistory();
                userHistory.setCompany(rs.getString(16));
                userHistory.setIdHR(rs.getInt(2));
                userHistory.setYearEmployed(rs.getInt(4));
                userHistory.setYearTerminated(rs.getInt(5));
                userHistory.setRating1(rs.getInt(6));
                userHistory.setRating2(rs.getInt(7));
                userHistory.setRating3(rs.getInt(8));
                userHistory.setRating4(rs.getInt(9));
                userHistory.setRating5(rs.getInt(10));
                userHistory.setHireAgain(rs.getInt(12));
                employee.addHistory(userHistory);
            }
        } catch (SQLException e) {
            LOG.info("SQL Exception during registration command execution" + e);
            throw new DAOException("error during new user registrations", e);
        } finally {
            ConnectionPool.getInstance().returnConnection(conn);
        }
        return employee;
    }

    public int getCompanyIdByOfficialId(int companyOfficialId) throws SQLException {
        int companyInnerId = 0;
        Connection conn = ConnectionPool.getInstance().getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(queryGetCompanyIdByOffId);
            ps.setInt(1, companyOfficialId);
            rs = ps.executeQuery();
            while (rs.next()) {
                companyInnerId = rs.getInt(1);
            }
        } finally {
            ConnectionPool.getInstance().returnConnection(conn);
        }
        LOG.info("company id inside DB: " + companyInnerId);
        return companyInnerId;
    }

    public int getCompanyIdByName(String companyName) throws SQLException {
        int companyId = 0;
        Connection conn = ConnectionPool.getInstance().getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(queryGetCompanyId);
            ps.setString(1, companyName);
            rs = ps.executeQuery();
            while (rs.next()) {
                companyId = rs.getInt(1);
            }
        } finally {
            ConnectionPool.getInstance().returnConnection(conn);
        }
        LOG.info("company id: " + companyId);
        return companyId;
    }


    public void addCompany(Company companyName) throws DAOException {
        executeQuery(queryAddCompany, companyName.getName(), companyName.getNiche(), companyName.getLocation(),
                companyName.getHeadcount(), companyName.getCompanyOfficialId());
        LOG.info("New company is added:" + companyName.getName());
    }

    public void setCompanyId(Company company, User user) throws SQLException {
        updateQuery(querySetCompanyId, company.getCompanyInnerId(), user.getId());
    }

    public void addMyCompany(User user, Company companyName) {
        try {
            addCompany(companyName);
            LOG.info("New company is added:" + companyName.getName());
            companyName.setCompanyInnerId(getCompanyIdByName(companyName.getName()));
            setCompanyId(companyName, user);
        } catch (SQLException e) {
            LOG.info("SQL Exception during company registration command execution" + e);
        } catch (DAOException e) {
            LOG.info("Exception during company registration command execution" + e);
        }
    }

    public void registerEmployee(User employee) throws SQLException {
        updateQuery(queryAddEmployeeBySSN, employee.getfName(), employee.getlName(), employee.getSSN());
    }

    public void addReview(User hrHead, User employee, UserHistory userHistory) throws SQLException {
        Connection conn = ConnectionPool.getInstance().getConnection();
        PreparedStatement ps = null;
        LOG.info("Adding new review...");
        try { //tries to find employee with this SSN
            employee.setId(getEmployeeBySSN(employee.getSSN(), conn).getId());
            if (employee.getId() == 0) {
                LOG.info("Employee info is not found in DB, creating new employee...");
                registerEmployee(employee);
                employee.setId(getEmployeeBySSN(employee.getSSN(), conn).getId());
                LOG.info("Employee registration is finished. Employee id is: " + employee.getId());
            }
        } catch (SQLException en) {
            LOG.info("Something went wrong with adding new employee during review add." + en);
        }
        int companyId = 0;
        try {
            companyId = getCompanyIdByOfficialId(userHistory.getIdCompany());
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
        } catch (SQLException en) {
            LOG.info("Something went wrong with getting company id." + en);
        } catch (DAOException e) {
            LOG.info("OMG! Something went wrong with getting company id." + e);
        }
        addReviewHelp(companyId, hrHead, employee, userHistory);
    }


    public void addReviewHelp(int companyId, User hrHead, User employee, UserHistory userHistory) throws SQLException {
        double aveRating = (userHistory.getRating1() + userHistory.getRating2() +
                userHistory.getRating3() + userHistory.getRating4() + userHistory.getRating5()) / 5.0;
        int getHireAgain = (userHistory.getHireAgain().equals("Yes") ? 1 : 0);

        updateQuery(queryAddReview, companyId, hrHead.getId(), employee.getId(), userHistory.getYearEmployed(),
                userHistory.getYearTerminated(), userHistory.getRating1(), userHistory.getRating2(),
                userHistory.getRating3(), userHistory.getRating4(), userHistory.getRating5(), aveRating, getHireAgain);
    }


    public List<User> getHrByName(String fName, String lName) throws SQLException {
        Connection conn = ConnectionPool.getInstance().getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        List<User> hrList = null;
        try {
            ps = conn.prepareStatement(queryGetHrByName);
            ps.setString(1, fName);
            ps.setString(2, lName);
            rs = ps.executeQuery();
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
        } finally {
            ConnectionPool.getInstance().returnConnection(conn);
        }
        return hrList;
    }

    public ArrayList<Integer> getReviewsRateForHrById(int idHR) throws SQLException {
        Connection conn = ConnectionPool.getInstance().getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        ArrayList<Integer> ratingList = new ArrayList<>();
        LOG.info("HR id: " + idHR);
        try {
            ps = conn.prepareStatement(getReviewsOfSingleHrById);
            ps.setInt(1, idHR);
            rs = ps.executeQuery();
            while (rs.next()) {
                ratingList.add(rs.getInt(1));
                ratingList.add(rs.getInt(2));
                ratingList.add(rs.getInt(3));
                ratingList.add(rs.getInt(4));
                ratingList.add(rs.getInt(5));
            }
        } finally {
            ConnectionPool.getInstance().returnConnection(conn);
        }
        return ratingList;
    }

    public ArrayList<Double> getAVGReviewsRateForHrById(int idHR) throws SQLException {
        Connection conn = ConnectionPool.getInstance().getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        ArrayList<Double> ratingAVGSList = new ArrayList<>();
        try {
            ps = conn.prepareStatement(getAVGReviewsOfSingleHrById);
            ps.setInt(1, idHR);
            rs = ps.executeQuery();
            while (rs.next()) {
                ratingAVGSList.add(rs.getDouble(1));
            }
        } finally {
            ConnectionPool.getInstance().returnConnection(conn);
        }
        return ratingAVGSList;
    }
}
