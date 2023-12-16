
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

import com.example.SpringBootWebApp.Models.User;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

@Controller
public class RegistrationController {
    /**
     * Handles HTTP GET requests to "/registration" endpoint and renders the registration/signup page.
     *
     * @param model The `Model` object used to add attributes to the view.
     * @return The logical view name "signup" associated with the registration page.
     */
    @GetMapping("/registration")
    public String registerUser(Model model) {
        model.addAttribute("user",new User());
        return "signup";
    }

}
