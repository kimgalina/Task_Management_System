package com.example.SpringBootWebApp.Controllers;
import com.example.SpringBootWebApp.Models.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MyController {
    @GetMapping()
    public String registerNewUser() {

        return "Registration";
    }
    @GetMapping("/dashboard")
    public String getMyProfile() {

        return "Dashboard";
    }
    @GetMapping("/registration-success")
    public String showCongratulations() {
        return "Congratulations";
    }

    @PostMapping("/")
    public String create(@ModelAttribute("newUser") User user) {
        boolean isRegistrationSuccess = true; // logic of creating user in db
        if(isRegistrationSuccess) {
            return "redirect:/registration-success";
        }
        return "redirect:/";
    }


}
