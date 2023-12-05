package com.example.SpringBootWebApp.Controllers;
import com.example.SpringBootWebApp.Models.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MyController {
    @GetMapping()
    public String sayHello() {
        return "Registration";
    }
    @GetMapping("/dashboard")
    public String getMyProfile() {
        return "Dashboard";
    }
    @PostMapping()
    public String create(@ModelAttribute("newUser") User user) {
        return "redirect:/users";
    }


}
