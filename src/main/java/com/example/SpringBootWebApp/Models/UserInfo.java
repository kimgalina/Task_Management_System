package com.example.SpringBootWebApp.Models;

public class UserInfo {
    private int userId;
    private String photo;
    private String email;
    private int phoneNumber;

    public UserInfo(int userId, String photo, String email, int phoneNumber) {
        this.userId = userId;
        this.photo = photo;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // Геттеры
    public int getUserId() {
        return userId;
    }

    public String getPhoto() {
        return photo;
    }

    public String getEmail() {
        return email;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    // Сеттеры
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
