package by.epam.listener;

import by.epam.pool.ConnectionPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class StartupAndShutdownListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        while (!ConnectionPool.getInstance().isAlive()) {
            ConnectionPool.getInstance().createPool();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ConnectionPool.getInstance().closePool();
    }
}
