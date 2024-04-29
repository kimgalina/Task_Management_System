/**
 * The `UserController` class is a Spring MVC controller responsible for handling
 * requests related to user management and task assignment in the Spring Boot web application.
 *
 * It is annotated with `@Controller` to indicate that this class serves as a
 *
 *
 * @author [Galina Kim]
 * @version 1.0
 * @since [16.12.2023]
 */
package com.example.SpringBootWebApp.Controller;

import com.example.SpringBootWebApp.entity.User;
import com.example.SpringBootWebApp.enums.UserStatus;
import com.example.SpringBootWebApp.exception.NotFoundException;
import com.example.SpringBootWebApp.model.UserUpdate;
import com.example.SpringBootWebApp.repository.UserRepository;
import com.example.SpringBootWebApp.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Controller
@RequestMapping("/users")
@AllArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;


    @GetMapping("user/infinity.gif")
    public ResponseEntity<byte[]> getInfinityGif() {
        ClassPathResource resource = new ClassPathResource("static/infinity.gif");
        try (InputStream inputStream = resource.getInputStream()) {
            byte[] gifBytes = inputStream.readAllBytes();
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_GIF)
                    .body(gifBytes);

        } catch (IOException ioe) {
            System.err.println("ERROR WHILE READING FILE WITH GIF");
            ioe.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public String getUserProfile(@PathVariable("id") Long id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Пользователя с таким id нет "));
        model.addAttribute("user", user);

        switch(user.getStatus()) {
            case ROLE_WORKER:WORKER:
                return "worker";
            case ROLE_MANAGER:MANAGER:
                model.addAttribute("workers", userRepository.findByStatus(UserStatus.ROLE_WORKER));
                return "manager";
            case ROLE_DIRECTOR:
                model.addAttribute("workers", userRepository.findByStatus(UserStatus.ROLE_WORKER));
                model.addAttribute("managers", userRepository.findByStatus(UserStatus.ROLE_MANAGER));
                return "director";
        }
        return null;
    }

    @GetMapping("/update/{id}")
    public String getUpdateUserForm(@PathVariable("id") Long id,
                             Model model) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) {
            return "user-not-found";
        }
        User userEntity = user.get();
        model.addAttribute("user",
                new UserUpdate(
                        userEntity.getFirstName(),
                        userEntity.getLastName(),
                        userEntity.getEmail()
                )
        );
        return "update-user";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") String id,
                             @ModelAttribute("user") @Valid UserUpdate updatedUser,
                             BindingResult bindingResult,
                             Model model) {
        System.out.println(updatedUser.getFirstName());
        System.out.println(updatedUser.getLastName());
        System.out.println(updatedUser.getEmail());
        // осталось сохранить изменения , провалидировать
        return "redirect: /login";
    }

    @DeleteMapping("/{id}")
    public String deleteAccount() {
        return null;
    }

}
