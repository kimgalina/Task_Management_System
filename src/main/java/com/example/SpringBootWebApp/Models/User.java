package com.example.SpringBootWebApp.Models;

import com.example.SpringBootWebApp.DAO.TaskDAO;
import com.example.SpringBootWebApp.DAO.UserDAO;
import com.example.SpringBootWebApp.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.List;

public class User {
    private int id;
    private UserStatus status;

    private String firstName;
    private String lastName;

    private String password;
    private String email;
    private String phoneNumber;

    private File photo;


    public User() {

    }

    public User(String firstName, String lastName, UserStatus status, String password, int id) {
        this.firstName = firstName;
        this.status = status;
        this.lastName = lastName;
        this.password = password;
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public UserStatus getStatus() {
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

    public File getPhoto() {
        return photo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(UserStatus status) {
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

    public void setPhoto(File photo) {
        this.photo = photo;
    }

    public List<Task> getUserTasks() {
        return TaskDAO.getTasksByUser(id);
    }
}
