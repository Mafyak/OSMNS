package by.epam.entity;

public abstract class Entity {
    private int id;
    private String name;

    public abstract int getId();

    public abstract String getName();

    public abstract void setId(int id);

    public abstract void setName(String name);
}
