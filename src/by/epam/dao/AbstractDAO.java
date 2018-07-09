package by.epam.dao;

import java.sql.Connection;

public abstract class AbstractDAO<T> {

    public abstract T getEntity(String query, Object... params);

    //public abstract boolean add(T t);
    //public abstract T get(int id);
    //public abstract boolean delete(T t);
    //public abstract T remove(int id);
    private Connection connection;

    AbstractDAO(Connection connection) {
        this.connection = connection;
    }

    Connection getConnection(){
        return connection;
    }
}
