package com.example.SpringBootWebApp.Models;

public class Task {
    private int id;
    private int userId;
    private String deadline;
    private String content;
    private String status;

    public Task(int id, int userId, String deadline, String content, String status) {
        this.id = id;
        this.userId = userId;
        this.deadline = deadline;
        this.content = content;
        this.status = status;
    }

    // Геттеры
    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getContent() {
        return content;
    }

    public String getStatus() {
        return status;
    }

    // Сеттеры
    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
