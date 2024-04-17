package com.example.SpringBootWebApp.model;

import com.example.SpringBootWebApp.enums.UserStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class UserCreate {
    private UserStatus status;

    private String firstName;

    private String lastName;

    private String password;
    private String confirmPassword;

    private String email;
}
