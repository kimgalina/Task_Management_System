package com.example.SpringBootWebApp.model;

import com.example.SpringBootWebApp.entity.User;
import com.example.SpringBootWebApp.enums.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class UserCreate {
    @NotBlank
    private String userStatus;

    @NotBlank(message = "Введите имя ")
    @Size(min = User.NAME_MIN_LENGTH,
            max = User.NAME_MAX_LENGTH,
            message = "Имя должно содержать " + User.NAME_MIN_LENGTH + " - " + User.NAME_MAX_LENGTH + " chars")
    private String firstName;

    @NotBlank(message = "Введите фамилию ")
    @Size(min = User.NAME_MIN_LENGTH,
            max = User.NAME_MAX_LENGTH,
            message = "Фамилия должна содержать " + User.NAME_MIN_LENGTH + " - " + User.NAME_MAX_LENGTH + " chars")
    private String lastName;

    @NotBlank(message = "Введите пароль ")
    @Size(min = User.PASSWORD_MIN_LENGTH, max = 255, message = "Password should be between " + User.PASSWORD_MIN_LENGTH + " - 255 chars")
    private String password;

    @NotBlank(message = "Введите пароль еще раз ")
    @Size(min = User.PASSWORD_MIN_LENGTH, max = 255, message = "Password should be between " + User.PASSWORD_MIN_LENGTH + " - 255 chars")
    private String confirmPassword;

    @NotBlank(message = "Введите ваш email- адрес")
    @Email(message = "Введите существующий адрес ")
    @Size(min = User.EMAIL_MIN_LENGTH,  max = User.EMAIL_MAX_LENGTH,
            message = "Email should be between " + User.EMAIL_MIN_LENGTH + " - " + User.EMAIL_MAX_LENGTH + " chars")
    private String email;
}
