package by.epam;

import by.epam.entity.User;
import by.epam.exception.ServiceException;
import by.epam.pool.ConnectionPool;
import by.epam.utils.security.Security;
import by.epam.utils.service.AdminService;
import by.epam.utils.service.UserService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class UserRegistrationTest {
    private String userInfo[] = {"TestUser", "Testpass123", "testfirstname",
            "testmiddlename", "testlastname"};
    private UserService userService = new UserService();
    private AdminService adminService = new AdminService();

    @Test
    public void testUserRegistration() {

        ConnectionPool.getInstance().createPool();

        try {
            userService.register(userInfo[0], userInfo[1], userInfo[2],
                    userInfo[3], userInfo[4]);
        } catch (ServiceException e) {
            Assert.fail();
        }

        User user;
        try {
            user = userService.login(userInfo[0], userInfo[1]);
            Assert.assertEquals(user.getfName(), userInfo[2]);
            Assert.assertEquals(user.getlName(), userInfo[4]);
        } catch (ServiceException e) {
            Assert.fail();
        }
    }

    @After
    public void removeUser() {

        Security security = new Security();
        User user = new User();
        user.setEmail(userInfo[0]);
        user.setPass(security.encryptData(userInfo[1]));

        try {
            userService.setUserIdByEmailAndPass(user);
            adminService.removeHrById(user.getId());
        } catch (ServiceException e) {
            Assert.fail();
        } finally {
            ConnectionPool.getInstance().closePool();
        }
    }
}
