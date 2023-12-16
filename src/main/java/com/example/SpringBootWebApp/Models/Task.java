/**
 * The `Task` class represents a task entity in the Spring Boot web application. It includes
 * attributes such as task content, completion status, user ID, and a unique identifier.
 *
 * The class provides methods to access and modify these attributes.
 */

package com.example.SpringBootWebApp.Models;

public class Task {

    private int id;
    private String taskContent;
    private boolean isDone;
    private int userId;

    /**
     * Constructs a new `Task` instance with the specified task content and user ID.
     *
     * @param taskContent The content of the task.
     * @param userId The ID of the user associated with the task.
     */
    public Task(String taskContent, int userId) {
        this.taskContent = taskContent;
        this.userId = userId;
    }

    /**
     * Gets the content of the task.
     *
     * @return The content of the task.
     */
    public String getTaskContent() {
        return taskContent;
    }

    /**
     * Sets the content of the task.
     *
     * @param taskContent The new content of the task.
     */
    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    /**
     * Checks if the task is marked as done.
     *
     * @return `true` if the task is done; otherwise, `false`.
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Sets the completion status of the task.
     *
     * @param done The new completion status of the task.
     */
    public void setDone(boolean done) {
        isDone = done;
    }

    /**
     * Gets the ID of the user associated with the task.
     *
     * @return The ID of the user.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the ID of the user associated with the task.
     *
     * @param userId The new ID of the user.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the unique identifier of the task.
     *
     * @return The ID of the task.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the task.
     *
     * @param id The new ID of the task.
     */
    public void setId(int id) {
        this.id = id;
    }
}
