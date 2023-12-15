package com.example.SpringBootWebApp.Models;

import com.example.SpringBootWebApp.DAO.TaskDAO;

import java.util.List;

public class User {
    public static int USER_COUNT = 6;
    private int id;
    private String status;

    private String firstName;
    private String lastName;

    private String password;
    private String email;
    private String phoneNumber;



    public User() {
        USER_COUNT++;
    }

    public User(String firstName, String lastName, String status, String password, int id) {
        this.firstName = firstName;
        this.status = status;
        this.lastName = lastName;
        this.password = password;
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public List<Task> getUserTasks() {
        return TaskDAO.getTasksByUser(id);
    }
}
