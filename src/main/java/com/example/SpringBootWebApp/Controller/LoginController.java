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

import com.example.SpringBootWebApp.Models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    /**
     * Handles HTTP GET requests to "/login" endpoint and renders the login/signin page.
     *
     * @param model The `Model` object used to add attributes to the view.
     * @return The logical view name "signin" associated with the login page.
     */
    @GetMapping("/login")
    public String registerUser(Model model) {
        model.addAttribute("user",new User());
        return "signin";
    }

}
