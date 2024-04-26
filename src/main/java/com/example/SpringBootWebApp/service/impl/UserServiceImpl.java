package com.example.SpringBootWebApp.service.impl;

import com.example.SpringBootWebApp.entity.User;
import com.example.SpringBootWebApp.enums.UserStatus;
import com.example.SpringBootWebApp.mapper.UserMapper;
import com.example.SpringBootWebApp.model.UserCreate;
import com.example.SpringBootWebApp.repository.UserRepository;
import com.example.SpringBootWebApp.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Override
    public Long createUser(UserCreate newUser, List<String> errors) {
        if(!newUser.getPassword().equals(newUser.getConfirmPassword())) {
            errors.add("Пароли не совпадают");
            return null;
        }
        if(userRepository.existsByEmail(newUser.getEmail())) {
            errors.add("Пользователь с таким email- адресом уже существует");
            return null;
        }
        User userEntity = userMapper.toUserEntity(newUser);
        userEntity.setTasks(Collections.emptySet());
        userEntity.setStatus(UserStatus.valueOf(newUser.getUserStatus().toUpperCase()));
        userRepository.save(userEntity);
        log.info("Создали нового пользователя с Id = "  + userEntity.getId().toString());
        return userEntity.getId();
    }
}
