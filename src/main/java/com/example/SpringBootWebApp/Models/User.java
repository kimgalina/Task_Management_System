package com.example.SpringBootWebApp.Models;

public class User {
    private int id;
    private int userId;
    private String firstName;
    private String lastName;
    private String status;
    private String password;

    public User(int id, int userId, String firstName, String lastName, String status, String password) {
        this.id = id;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
        this.password = password;
    }

    // Геттеры
    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getStatus() {
        return status;
    }

    public String getPassword() {
        return password;
    }

    // Сеттеры
    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
