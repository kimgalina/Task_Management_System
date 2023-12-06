package com.example.SpringBootWebApp.Controllers;
import com.example.SpringBootWebApp.DAO.UserDAO;
import com.example.SpringBootWebApp.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MyController {
    @Autowired
    private UserDAO userDAO;
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

    @GetMapping("/sign-in")
    public String signIn() {
       return "sign_in";
    }

    @PostMapping("/")
    public String create(@ModelAttribute("newUser") User user) {
        userDAO.addUser(user);
        boolean isRegistrationSuccess = true; // logic of creating user in db
        if(isRegistrationSuccess) {
            return "redirect:/registration-success";
        }
        return "redirect:/";
    }


}
