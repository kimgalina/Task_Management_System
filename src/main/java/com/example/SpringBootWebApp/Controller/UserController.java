package com.example.SpringBootWebApp.Controller;

import com.example.SpringBootWebApp.DAO.UserDAO;
import com.example.SpringBootWebApp.Models.User;
import com.example.SpringBootWebApp.UserStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private UserDAO userDAO;

    public UserController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @PostMapping("/create-user")
    public String create(@ModelAttribute("user") User newUser, @RequestParam("status") UserStatus status){

        newUser.setStatus(status);
        System.out.println(newUser.getEmail() + " " + newUser.getFirstName() + " " + newUser.getLastName() + " " +
                newUser.getPassword() + " " + newUser.getStatus());// check

        userDAO.addUser(newUser);

        return "redirect:/";
    }

}
