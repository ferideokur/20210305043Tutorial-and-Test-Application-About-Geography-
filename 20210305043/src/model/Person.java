package model;

// Base class for all people in the system
public abstract class Person {

    private int id;
    private String name;

    protected Person(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Can be overridden for polymorphism
    public String getInfo() {
        return "ID: " + id + ", Name: " + name;
    }
}
