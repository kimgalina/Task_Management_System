/**
 * The `UserDAO` class is responsible for interacting with the database to perform CRUD operations
 * related to user management. It uses PostgreSQL as the underlying database and is designed as a
 * Spring component.
 *
 * This class provides methods for adding, retrieving, and finding users in the database, as well
 * as obtaining lists of workers and managers. Additionally, it includes static initialization to
 * set up the database connection during class loading.
 *  @author [Isa Kasymbek uulu]
 *  @version 1.0
 */

package com.example.SpringBootWebApp.DAO;

import com.example.SpringBootWebApp.Models.User;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserDAO {

    private final DatabaseManager databaseManager;
    private final Connection connection;

    public UserDAO(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        connection = databaseManager.getConnection();
    }


    /**
     * Adds a new user to the database.
     *
     * @param user The user to be added.
     * @return `true` if the user is successfully added; otherwise, `false`.
     */
    public boolean addUser(User user) {
        try {
            String SQL = "INSERT INTO Users (status, first_name, last_name, password, email, phone_number) VALUES (?, ?, ?, ?, ?, ?) RETURNING id";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, user.getStatus());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getPhoneNumber());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Получаем автоинкрементное значение из базы данных
                int userId = resultSet.getInt("id");
                user.setId(userId);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    /**
     * Retrieves a user by their ID from the database.
     *
     * @param id The ID of the user.
     * @return The user with the specified ID; `null` if not found.
     */
    public User findById(int id) {
        User user = null;

        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM Users WHERE id = " + id;
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                if(resultSet.getInt("id") == id) {
                    user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setStatus(resultSet.getString("status"));
                    user.setFirstName(resultSet.getString("first_name"));
                    user.setLastName(resultSet.getString("last_name"));
                    user.setPassword(resultSet.getString("password"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPhoneNumber(resultSet.getString("phone_number"));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    /**
     * Finds a user in the database based on the provided user's first name and password.
     *
     * @param user The user with first name and password for searching.
     * @return The found user; `null` if not found or password doesn't match.
     */
    public User findUser(User user) {
        User result = null;

        try {
            String SQL = "SELECT * FROM Users WHERE first_name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, user.getFirstName());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getString("password").equals(user.getPassword())) {
                    result = new User();
                    result.setId(resultSet.getInt("id"));
                    result.setStatus(resultSet.getString("status"));
                    result.setFirstName(resultSet.getString("first_name"));
                    result.setLastName(resultSet.getString("last_name"));
                    result.setPassword(resultSet.getString("password"));
                    result.setEmail(resultSet.getString("email"));
                    result.setPhoneNumber(resultSet.getString("phone_number"));
                }
            }
            System.out.println("User found: " + result);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    /**
     * Retrieves a list of all workers from the database.
     *
     * @return A list of worker users.
     */
    public List<User> getAllWorkers() {
        List<User> workers = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM Users";
            ResultSet resultSet = statement.executeQuery(SQL);

            while(resultSet.next()) {
                if(resultSet.getString("status").equals("worker")) {
                    User user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setStatus(resultSet.getString("status"));
                    user.setFirstName(resultSet.getString("first_name"));
                    user.setLastName(resultSet.getString("last_name"));
                    user.setPassword(resultSet.getString("password"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPhoneNumber(resultSet.getString("phone_number"));

                    workers.add(user);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return workers;
    }

    /**
     * Retrieves a list of all managers from the database.
     *
     * @return A list of manager users.
     */
    public List<User> getAllManagers() {
        List<User> managers = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM Users";
            ResultSet resultSet = statement.executeQuery(SQL);

            while(resultSet.next()) {
                if(resultSet.getString("status").equals("manager")){
                    User user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setStatus(resultSet.getString("status"));
                    user.setFirstName(resultSet.getString("first_name"));
                    user.setLastName(resultSet.getString("last_name"));
                    user.setPassword(resultSet.getString("password"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPhoneNumber(resultSet.getString("phone_number"));

                    managers.add(user);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return managers;
    }
}
