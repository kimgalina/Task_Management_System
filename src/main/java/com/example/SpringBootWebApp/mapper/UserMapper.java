package com.example.SpringBootWebApp.mapper;

import com.example.SpringBootWebApp.entity.User;
import com.example.SpringBootWebApp.model.UserCreate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUserEntity(UserCreate model);
}
