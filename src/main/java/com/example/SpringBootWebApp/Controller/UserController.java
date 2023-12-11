package com.example.SpringBootWebApp.Controller;

import com.example.SpringBootWebApp.DAO.UserDAO;
import com.example.SpringBootWebApp.Models.User;
import com.example.SpringBootWebApp.UserStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        // когда пользователь добавляется в базу его полю id присваеивается занчение
        if(userDAO.addUser(newUser)) {
            return "redirect:/user/" + newUser.getId();
        }

        return "redirect:/registration";
    }

    @PostMapping("/login-user")
    public String login(@ModelAttribute("user") User newUser){
        User existedUser = userDAO.findUser(newUser);
        if(existedUser != null) {
            System.out.println(newUser.getEmail() + " " + newUser.getFirstName() + " " + newUser.getLastName() + " " +
                    newUser.getPassword() + " " + newUser.getStatus());// check
            return "redirect:/user/" + existedUser.getId();
        }
        return "redirect:/login";
    }
    @GetMapping("/user/{id}")
    public String getUserProfile(@PathVariable("id") int id, Model model) {
        User user = userDAO.findById(id);
        model.addAttribute("user", user);
        switch(user.getStatus()) {
            case WORKER :
                return "worker";
            case MANAGER:
                model.addAttribute("workers",userDAO.getAllWorkers());
                return "manager";
            case DIRECTOR:
                model.addAttribute("workers",userDAO.getAllWorkers());
                model.addAttribute("managers",userDAO.getAllManagers());
                return "director";
        }
        return null;
    }





}
