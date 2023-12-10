package com.example.SpringBootWebApp.Models;

import com.example.SpringBootWebApp.UserStatus;

import java.io.File;

public class User {
    private int id;
    private UserStatus status;

    private String firstName;
    private String lastName;

    private String password;
    private String email;
    private String phoneNumber;

    private File photo;

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
}
