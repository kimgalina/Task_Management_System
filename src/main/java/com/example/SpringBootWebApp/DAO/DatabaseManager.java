package com.example.SpringBootWebApp.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String URL = "jdbc:postgresql://localhost:5432/task_management_system_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres"; ////// ИСА ИЗМЕНИ ПАРОЛЬ НА СВОЙ ТАК КАК Я ТЕСТИЛА У СЕБЯ

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
