package by.epam.pool;


import java.sql.*;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

public class ConnectionPool {
    private BlockingQueue<Connection> pool = new ArrayBlockingQueue<>(10, true);
    private ResourceBundle bundle = ResourceBundle.getBundle("resources/mysql");
    private Properties props = new Properties();
    private static volatile ConnectionPool instance;
    private static Logger LOG = Logger.getLogger("ConnectionPool");


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
        LOG.info("Connection pool is closed.");
    }

    public void createPool() {
        String url = bundle.getString("db.url");
        props.put("user", bundle.getString("db.user"));
        props.put("password", bundle.getString("db.pass"));
        props.put("characterEncoding", bundle.getString("db.encoding"));
        props.put("useUnicode", bundle.getString("db.useUnicode"));
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            LOG.info("Error while creating connection pool");
        }
        for (int i = 0; i < 10; i++) {
            try {
                pool.add(DriverManager.getConnection(url, props));
            } catch (SQLException e) {
                LOG.info("SQL Exception is thrown while filling Connection Pool" + e);
            }
        }
        LOG.info("Connection pool is set and ready to go.");
    }

    public Connection getConnection() {
        Connection conn = null;
        try {
            conn = pool.take();
        } catch (InterruptedException e) {
            LOG.info("SQL Exception is thrown while getting a connection from Connection Pool" + e);
        }
        return conn;
    }

    public void returnConnection(Connection conn) {
        if (conn != null) {
            try {
                pool.put(conn);
            } catch (InterruptedException e) {
                LOG.info("SQL Exception is thrown while getting a connection from Connection Pool" + e);
            }
        }
    }
}