package com.example.SpringBootWebApp.DAO;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
@Component
public class DatabaseManager {
    @Value("${app.database.url}")
    private String URL;

    @Value("${app.database.user}")
    private String USER;

    @Value("${app.database.password}")
    private String PASSWORD;

    private Connection connection;

//    public DatabaseManager() {
//        try {
//            connection = DriverManager.getConnection(URL, USER, PASSWORD);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @PostConstruct
    public void init() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public String getURL() {
        return URL;
    }

    public String getUSER() {
        return USER;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public Connection getConnection() {
        return connection;
    }
}
