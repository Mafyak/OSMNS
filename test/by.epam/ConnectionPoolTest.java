package by.epam;

import by.epam.pool.ConnectionPool;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class ConnectionPoolTest {

    @Test
    public void testConnectionPool(){
        ConnectionPool.getInstance().createPool();
        Assert.assertTrue(ConnectionPool.getInstance().isAlive());
    }

    @After
    public void closeConnection(){
        ConnectionPool.getInstance().closePool();
    }
}
