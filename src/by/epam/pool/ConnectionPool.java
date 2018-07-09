package by.epam.pool;

import java.sql.*;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    private BlockingQueue<Connection> pool = new ArrayBlockingQueue<>(10, true);
    private ResourceBundle bundle = ResourceBundle.getBundle("resources/mysql");
    private Properties props = new Properties();
    // private static ConnectionPool instance = null;
    private static volatile ConnectionPool instance;

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

    /*
    public static ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
            instance.createPool();
        }
        return instance;
    }
    */

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
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        Connection conn = null;
        try {
            conn = pool.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                pool.put(conn);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
