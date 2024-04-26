package com.example.SpringBootWebApp.service;

import com.example.SpringBootWebApp.model.UserCreate;

import java.util.List;

public interface UserService {
    public Long createUser(UserCreate newUser, List<String> errors);
}
