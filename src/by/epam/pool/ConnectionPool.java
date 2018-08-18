package by.epam.pool;

import java.sql.*;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;

public class ConnectionPool {

    private BlockingQueue<Connection> pool = new ArrayBlockingQueue<>(10, true);
    private BlockingQueue<Connection> takenConnections = new ArrayBlockingQueue<>(10, true);
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("resources/mysql");
    private static final Logger LOG = Logger.getLogger("ConnectionPool");
    private static volatile ConnectionPool instance;
    private Properties props = new Properties();

    private ConnectionPool() {
    }

    public static ConnectionPool getInstance() {
        ConnectionPool localInstance = instance;
        if (localInstance == null) {
            synchronized (ConnectionPool.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ConnectionPool();
                }
            }
        }
        return localInstance;
    }

    public void closePool() {
        for (Connection conn : pool) {
            try {
                conn.close();
            } catch (SQLException e) {
                LOG.info("Connection " + conn + " can't be closed.");
            }
        }
        for (Connection conn : takenConnections) {
            try {
                conn.close();
            } catch (SQLException e) {
                LOG.info("Connection " + conn + " can't be closed.");
            }
        }
        LOG.info("Connection pool is closed.");
    }

    public void createPool() {
        String url = RESOURCE_BUNDLE.getString("db.url");
        props.put("user", RESOURCE_BUNDLE.getString("db.user"));
        props.put("password", RESOURCE_BUNDLE.getString("db.pass"));
        props.put("characterEncoding", RESOURCE_BUNDLE.getString("db.encoding"));
        props.put("useUnicode", RESOURCE_BUNDLE.getString("db.useUnicode"));
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            LOG.info("Error while creating connection pool");
        }
        for (int i = 0; i < 10; i++) {
            try {
                LOG.info("trying to add conn");
                LOG.info("pool size: " + pool.size() + ", # of tries:" + i);
                pool.add(DriverManager.getConnection(url, props));
            } catch (SQLException e) {
                LOG.info("SQL Exception is thrown while filling Connection Pool" + e);
            }
        }
        LOG.info("Connection pool is set and ready to go.");
    }

    public boolean isAlive() {
        return pool.size() == 10;
    }

    public Connection getConnection() {
        Connection conn = null;
        try {
            conn = pool.take();
            takenConnections.add(conn);
        } catch (InterruptedException e) {
            LOG.info("SQL Exception is thrown while getting a connection from Connection Pool" + e);
        }
        return conn;
    }

    public void returnConnection(Connection conn) {
        if (conn != null) {
            try {
                pool.put(conn);
                takenConnections.remove(conn);
            } catch (InterruptedException e) {
                LOG.info("SQL Exception is thrown while getting a connection from Connection Pool" + e);
            }
        }
    }
}