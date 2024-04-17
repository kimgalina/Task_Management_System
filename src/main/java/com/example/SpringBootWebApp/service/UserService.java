package com.example.SpringBootWebApp.service;

import com.example.SpringBootWebApp.model.UserCreate;

public interface UserService {
    public Long createUser(UserCreate newUser);
}
