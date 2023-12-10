package com.example.SpringBootWebApp.DAO;

import com.example.SpringBootWebApp.Models.User;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserDAO {
    /// logic of connecting and operating with database
    private static int USER_COUNT;
    private static final String URL = "jdbc:postgresql://localhost:5432/task_manager_db";
    private static final String USERNAME = "postrgres";
    private static final String PASSWORD = "123456";
    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllusers() {
        List<User> users = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM User";
            ResultSet resultSet = statement.executeQuery(SQL);

            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                int userId = resultSet.getInt("userId");
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");
                String status = resultSet.getString("status");
                String password = resultSet.getString("password");

                User user = new User(id, userId, firstName, lastName, status, password);
                users.add(user);
            }
        } catch (SQLException e) {
                e.printStackTrace();
        }

        return users;
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM Task";
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int userId = resultSet.getInt("userid");
                String deadline = resultSet.getString("deadline");
                String content = resultSet.getString("content");
                String status = resultSet.getString("status");

                Task task = new Task(id, userId, deadline, content, status);
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }


    public void addUser(User user) {

    }
}
