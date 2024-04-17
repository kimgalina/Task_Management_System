package com.example.SpringBootWebApp.service.impl;

import com.example.SpringBootWebApp.entity.User;
import com.example.SpringBootWebApp.exception.PasswordException;
import com.example.SpringBootWebApp.exception.UserAlreadyExistException;
import com.example.SpringBootWebApp.mapper.UserMapper;
import com.example.SpringBootWebApp.model.UserCreate;
import com.example.SpringBootWebApp.repository.UserRepository;
import com.example.SpringBootWebApp.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Override
    public Long createUser(UserCreate newUser) {
        if(!newUser.getPassword().equals(newUser.getConfirmPassword())) {
            throw new PasswordException("Пароли не совпадают");
        }
        if(userRepository.existsByEmail(newUser.getEmail())) {
            throw new UserAlreadyExistException("Пользователь с таким email- адресом уже существует");
        }
        User userEntity = userMapper.toUserEntity(newUser);
        userEntity.setTasks(Collections.emptySet());
        userRepository.save(userEntity);
        log.info(userEntity.getId().toString());
        return userEntity.getId();
    }
}
