package by.epam;

import by.epam.entity.User;
import by.epam.service.UserService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class UserRegistrationTest {
    @Test
    public void testUserRegistration() {
        UserService userService = new UserService();
        String userInfo[] = {"TestUser", "Testpass123", "testfirstname",
                "testlastname", "testcompany"};
        userService.register(userInfo[0], userInfo[1], userInfo[2],
                userInfo[3], userInfo[4]);
        
        User user = userService.login(userInfo[0], userInfo[1]);
        
        Assert.assertEquals(user.getEmail(), userInfo[0]);
        Assert.assertEquals(user.getPass(), userInfo[1]);
        Assert.assertEquals(user.getCompany(), userInfo[4]);
    }
    @After
    public void removeUser(){
        //NEED USER REMOVAL CODE HERE!!!!
    }
}
