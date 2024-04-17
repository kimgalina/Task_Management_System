/**
 * The `User` class represents a user entity in the Spring Boot web application. It includes
 * attributes such as user status, first name, last name, password, email, phone number, and a unique identifier.
 *
 * The class provides methods to access and modify these attributes, as well as retrieve tasks associated with the user.
 * The `getUserTasks` method utilizes the `TaskDAO` to fetch tasks based on the user's ID.
 */

package com.example.SpringBootWebApp.Models;

import com.example.SpringBootWebApp.DAO.TaskDAO;

import java.util.List;

public class User {

    private int id;
    private String status;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String phoneNumber;

    /**
     * Default constructor for the `User` class.
     */
    public User() {
    }

    /**
     * Constructs a new `User` instance with the specified attributes.
     *
     * @param firstName The first name of the user.
     * @param lastName  The last name of the user.
     * @param status    The status of the user.
     * @param password  The password of the user.
     * @param id        The unique identifier of the user.
     */
    public User(String firstName, String lastName, String status, String password, int id) {
        this.firstName = firstName;
        this.status = status;
        this.lastName = lastName;
        this.password = password;
        this.id = id;
    }

    /**
     * Gets the unique identifier of the user.
     *
     * @return The ID of the user.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the status of the user.
     *
     * @return The status of the user.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Gets the first name of the user.
     *
     * @return The first name of the user.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Gets the last name of the user.
     *
     * @return The last name of the user.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Gets the password of the user.
     *
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the email of the user.
     *
     * @return The email of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the phone number of the user.
     *
     * @return The phone number of the user.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the unique identifier of the user.
     *
     * @param id The new ID of the user.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the status of the user.
     *
     * @param status The new status of the user.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Sets the first name of the user.
     *
     * @param firstName The new first name of the user.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Sets the last name of the user.
     *
     * @param lastName The new last name of the user.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Sets the password of the user.
     *
     * @param password The new password of the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the email of the user.
     *
     * @param email The new email of the user.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the phone number of the user.
     *
     * @param phoneNumber The new phone number of the user.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
