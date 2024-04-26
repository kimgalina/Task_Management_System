
/**
 * The `RegistrationController` class is a Spring MVC controller responsible for handling
 * requests related to user registration functionality in the Spring Boot web application.
 *
 * It is annotated with `@Controller` to indicate that this class serves as a
 * Spring MVC controller. The `@GetMapping("/registration")` annotation specifies that
 * this method will handle HTTP GET requests to the "/registration" endpoint.
 *
 * The `registerUser` method is mapped to the "/registration" endpoint and is responsible
 * for rendering the registration/signup page. It uses the `Model` object to add an
 * attribute named "user" of type `User` to the model. This attribute is used to
 * bind form data on the registration page.
 *
 *
 * @author [Galina Kim]
 * @version 1.0
 * @since [16.12.2023]
 */package com.example.SpringBootWebApp.Controller;

import com.example.SpringBootWebApp.model.UserCreate;
import com.example.SpringBootWebApp.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/registration")
@AllArgsConstructor
public class RegistrationController {
    private final UserService userService;

    @GetMapping()
    public String getRegistrationForm(Model model) {
        model.addAttribute("user",new UserCreate());
        return "signup";
    }

    @PostMapping
    public String register(@ModelAttribute("user") @Valid UserCreate newUser,
                           BindingResult bindingResult,
                           Model model) {
        System.out.println("в контроллере ");
        List<String> errorsMsg = new ArrayList<>();
        if(bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            for(ObjectError error: errors) {
                errorsMsg.add(error.getDefaultMessage());
            }
            model.addAttribute("errors", errorsMsg);
            return "signup";
        }
        Long createdUserId = userService.createUser(newUser, errorsMsg);
        if(createdUserId == null && !errorsMsg.isEmpty()) {
            model.addAttribute("errors", errorsMsg);
            return "signup";
        }
        return "redirect:/users/" + createdUserId;
    }

}
