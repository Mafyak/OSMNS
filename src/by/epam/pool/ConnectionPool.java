package by.epam.pool;

import java.sql.*;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import by.epam.utils.manager.Manager;
import org.apache.log4j.Logger;

public class ConnectionPool {

    private static final int AMOUNT_OF_CONNECTIONS = Integer.parseInt(Manager.getMan().getConfig("conn.quant"));
    private static final BlockingQueue<Connection> pool = new ArrayBlockingQueue<>(AMOUNT_OF_CONNECTIONS, true);
    private static final BlockingQueue<Connection> takenConnections = new ArrayBlockingQueue<>(AMOUNT_OF_CONNECTIONS, true);
    private static final Logger LOG = Logger.getLogger("ConnectionPool");
    private static final Properties props = new Properties();
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
        String url = Manager.getMan().getConfig("db.url");
        props.put("user", Manager.getMan().getConfig("db.user"));
        props.put("password", Manager.getMan().getConfig("db.pass"));
        props.put("characterEncoding", Manager.getMan().getConfig("db.encoding"));
        props.put("useUnicode", Manager.getMan().getConfig("db.useUnicode"));
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            LOG.info("Error while creating connection pool");
        }
        LOG.info("trying to add connections...");
        int x = (pool.size() == 0) ? AMOUNT_OF_CONNECTIONS : (AMOUNT_OF_CONNECTIONS - pool.size());

        for (int i = 0; i < x; i++) {
            try {
                LOG.info("pool size: " + pool.size() + ", # of tries:" + i);
                pool.add(DriverManager.getConnection(url, props));
            } catch (SQLException e) {
                LOG.info("SQL Exception is thrown while filling Connection Pool" + e);
            }
        }
        if (isAlive())
            LOG.info("Connection pool is set and ready to go.");
    }

    public boolean isAlive() {
        return pool.size() == AMOUNT_OF_CONNECTIONS;
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