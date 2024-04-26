package com.example.SpringBootWebApp.model;

import com.example.SpringBootWebApp.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserSignIn {
    @NotBlank
    @Size(min = User.EMAIL_MIN_LENGTH,  max = User.EMAIL_MAX_LENGTH,
            message = "Email should be between " + User.EMAIL_MIN_LENGTH + " - " + User.EMAIL_MAX_LENGTH + " chars")
    @Email(message = "Enter valid email address")
    private String email;

    @NotBlank
    @Size(min = User.PASSWORD_MIN_LENGTH, max = 255, message = "Password should be between " + User.PASSWORD_MIN_LENGTH + " - 255 chars")
    private String password;
}
