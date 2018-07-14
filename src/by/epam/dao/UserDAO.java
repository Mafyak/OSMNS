package by.epam.dao;

import by.epam.entity.Company;
import by.epam.entity.User;
import by.epam.entity.UserHistory;
import by.epam.entity.UserType;
import by.epam.exception.DAOException;
import by.epam.pool.ConnectionPool;

import java.sql.*;
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
    private String queryAddEmployeeBySSN = resourceBundle.getString("ADD_EMPLOYEE_BY_SSN");
    private String queryGetCompanyId = resourceBundle.getString("GET_COMPANY_ID_BY_NAME");
    private String queryGetEmployeeIdBySSN = resourceBundle.getString("GET_EMPLOYEE_ID_BY_SSN");


    private String querySetCompany = resourceBundle.getString("SET_COMPANY_TO_USER");
    Logger LOG = Logger.getLogger("UserDAO");

    public UserDAO() {
    }

    @Override
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

    public void register(User user) throws DAOException {
        Connection conn = ConnectionPool.getInstance().getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        int id = 0;
        try {
            ps = conn.prepareStatement(queryRegister);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPass());
            ps.execute();

            ps = conn.prepareStatement(queryGetUserId);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPass());
            rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt(1);
            }

            ps = conn.prepareStatement(queryAddHRHEADInfo);
            ps.setInt(1, id);
            ps.setString(2, user.getfName());
            ps.setString(3, user.getmName());
            ps.setString(4, user.getlName());
            ps.executeUpdate();
            LOG.info("New user is added:" + user.getEmail());

        } catch (SQLException e) {
            LOG.info("SQL Exception during registration command execution" + e);
            throw new DAOException("error during new user registrations", e);
        } finally {
            ConnectionPool.getInstance().returnConnection(conn);
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

    public User getEntityBySSN(int SSN) {
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
                userHistory.setCompany(rs.getString(14));
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
            try {
                throw new DAOException("error during new user registrations", e);
            } catch (DAOException e1) {
                e1.printStackTrace();
            }
        } finally {
            ConnectionPool.getInstance().returnConnection(conn);
        }
        return employee;

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

    public void addMyCompany(User user, Company companyName) {
        int userId = user.getId();
        int companyId = 0;
        Connection conn = ConnectionPool.getInstance().getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(queryAddCompany);
            ps.setString(1, companyName.getName());
            ps.setString(2, companyName.getNiche());
            ps.setString(3, companyName.getLocation());
            ps.setInt(4, companyName.getHeadcount());
            ps.execute();
            LOG.info("New company is added:" + companyName.getName());

            companyId = getCompanyIdByName(companyName.getName());

            ps = conn.prepareStatement(querySetCompany);
            ps.setInt(1, companyId);
            ps.setInt(2, userId);
            ps.executeUpdate();

        } catch (SQLException e) {
            LOG.info("SQL Exception during company registration command execution" + e);
        } finally {
            ConnectionPool.getInstance().returnConnection(conn);
        }

    }

    public void registerEmployee(User employee, Connection conn) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        LOG.info("Step 1 registration");
        ps = conn.prepareStatement(queryAddEmployeeBySSN);
        LOG.info("Step 3 registration");
        ps.setString(1, employee.getfName());
        ps.setString(2, employee.getlName());
        ps.setInt(3, employee.getSSN());
        LOG.info("Step 5 registration");
        ps.executeUpdate();
    }

   public void addReview(User hrHead, User employee, UserHistory userHistory) throws SQLException {
        Connection conn = ConnectionPool.getInstance().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            LOG.info("Step 1");
            registerEmployee(employee, conn);
            employee.setId(getEmployeeBySSN(employee.getSSN(), conn).getId());
            int companyId = getCompanyIdByName(hrHead.getCompany());
            LOG.info("Step 2");
            ps = conn.prepareStatement(queryAddReview);
            LOG.info("Step 3");
            ps.setInt(1, companyId);
            ps.setInt(2, hrHead.getId());
            ps.setInt(3, employee.getId());
            ps.setInt(4, userHistory.getYearEmployed());
            ps.setInt(5, userHistory.getYearTerminated());
            ps.setInt(6, userHistory.getRating1());
            LOG.info("Step 4");
            ps.setInt(7, userHistory.getRating2());
            ps.setInt(8, userHistory.getRating3());
            ps.setInt(9, userHistory.getRating4());
            ps.setInt(10, userHistory.getRating5());
            LOG.info("Step 5");
            double aveRating = (userHistory.getRating1() + userHistory.getRating2() +
                    userHistory.getRating3() + userHistory.getRating4() + userHistory.getRating5()) / 5.0;
            ps.setDouble(11, aveRating);
            LOG.info("Step 6");
            ps.setInt(12, (userHistory.getHireAgain().equals("Yes") ? 1 : 0));
            LOG.info("Step 7");
            ps.executeUpdate();
            LOG.info("New review is added:" + userHistory.toString());
        } finally {
            ConnectionPool.getInstance().returnConnection(conn);
        }
    }
}
