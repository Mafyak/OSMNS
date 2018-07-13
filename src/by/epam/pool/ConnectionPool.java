package by.epam.pool;

import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    private BlockingQueue<Connection> pool = new ArrayBlockingQueue<>(10, true);
    private ResourceBundle bundle = ResourceBundle.getBundle("resources/mysql");
    private Properties props = new Properties();
    private static volatile ConnectionPool instance;
    Logger LOG = Logger.getLogger(ConnectionPool.class);

    private ConnectionPool() {
    }

    public static ConnectionPool getInstance() {
        ConnectionPool localInstance = instance;
        if (localInstance == null) {
            synchronized (ConnectionPool.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ConnectionPool();
                    instance.createPool();
                }
            }
        }
        return localInstance;
    }

    private void createPool() {
        String url = bundle.getString("db.url");
        props.put("user", bundle.getString("db.user"));
        props.put("password", bundle.getString("db.pass"));
        props.put("characterEncoding", bundle.getString("db.encoding"));
        props.put("useUnicode", bundle.getString("db.useUnicode"));

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 10; i++) {
            try {
                pool.add(DriverManager.getConnection(url, props));
            } catch (SQLException e) {
                LOG.error("SQL Exception is thrown while filling Connection Pool" + e);
            }
        }
    }

    public Connection getConnection() {
        Connection conn = null;
        try {
            conn = pool.take();
        } catch (InterruptedException e) {
            LOG.error("SQL Exception is thrown while getting a connection from Connection Pool" + e);
        }
        return conn;
    }

    public void returnConnection(Connection conn) {
        if (conn != null) {
            try {
                pool.put(conn);
            } catch (InterruptedException e) {
                LOG.error("SQL Exception is thrown while getting a connection from Connection Pool" + e);
            }
        }
    }
}
