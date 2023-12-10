package com.example.SpringBootWebApp.Controller;

import com.example.SpringBootWebApp.Models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String registerUser(Model model) {
        model.addAttribute("user",new User());
        return "signin";
    }

}
