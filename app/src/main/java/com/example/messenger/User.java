package com.example.messenger;

public class User {
    private String id;
    private String name;
    private String lastName;
    private boolean online;

    public User(String id, String name, String lastName, boolean isOnline) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.online = isOnline;
    }

    public User() {}

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean isOnline() {
        return online;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", isOnline=" + online +
                '}';
    }
}
