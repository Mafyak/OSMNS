package by.epam.dao;

public abstract class AbstractDAO<T> {

    public abstract T getEntity(Object... params);
    //public abstract boolean add(T t);
    //public abstract T get(int id);
    //public abstract boolean delete(T t);
    //public abstract T remove(int id);
}
