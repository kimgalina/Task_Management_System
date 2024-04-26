/**
 * The `LoginController` class is a Spring MVC controller responsible for handling
 * requests related to user login functionality in the Spring Boot web application.
 *
 * It is annotated with `@Controller` to indicate that this class serves as a
 * Spring MVC controller.
 *
 * The `registerUser` method is mapped to the "/login" endpoint and is responsible
 * for rendering the login/signin page. It uses the `Model` object to add an
 * attribute named "user" of type `User` to the model. This attribute is used to
 * bind form data on the login page.
 *
 *
 * @author [Galina Kim]
 * @version 1.0
 * @since [16.12.2023]
 */

package com.example.SpringBootWebApp.Controller;

import com.example.SpringBootWebApp.entity.User;
import com.example.SpringBootWebApp.model.UserSignIn;
import com.example.SpringBootWebApp.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final UserRepository userRepository;
    @GetMapping
    public String login(Model model) {
        model.addAttribute("user",new UserSignIn());
        return "signin";
    }

    @PostMapping
    public String login(@ModelAttribute("user") @Valid UserSignIn user, BindingResult bindingResult, Model model) {
        List<String> errorsMsg = new ArrayList<>();
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            for(ObjectError error: errors) {
                errorsMsg.add(error.getDefaultMessage());
            }
            model.addAttribute("errors", errorsMsg);
            return "signin";
        }

        Optional<User> existedUser = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
        if(existedUser.isPresent()) {
            return "redirect:/users/" + existedUser.get().getId();
        }
        model.addAttribute("user", user);
        errorsMsg.add("Неправильный email или пароль");
        model.addAttribute("errors", errorsMsg);
        return "signin";
    }



}
