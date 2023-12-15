package com.example.SpringBootWebApp.Models;

public class Task {
    public static int TASK_COUNT = 0;
    private int id;
    private String taskContent;
//    private String deadline;
    private boolean isDone;
    private int userId;

//    private String taskStatus;

    public Task(String taskContent, int userId) {
        TASK_COUNT++;
        this.taskContent = taskContent;
        this.userId = userId;
    }
    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }
}
