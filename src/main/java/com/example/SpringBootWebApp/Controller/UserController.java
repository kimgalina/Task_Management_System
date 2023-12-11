package com.example.SpringBootWebApp.Controller;

import com.example.SpringBootWebApp.DAO.TaskDAO;
import com.example.SpringBootWebApp.DAO.UserDAO;
import com.example.SpringBootWebApp.Models.Task;
import com.example.SpringBootWebApp.Models.User;
import com.example.SpringBootWebApp.UserStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    private UserDAO userDAO;
    private TaskDAO taskDAO;

    public UserController(UserDAO userDAO, TaskDAO taskDAO) {

        this.userDAO = userDAO;
        this.taskDAO = taskDAO;
    }

    @PostMapping("/create-user")
    public String createUser(@ModelAttribute("user") User newUser, @RequestParam("status") UserStatus status){

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
        System.out.println("пришедший с формы user " + newUser.getFirstName() + " " + newUser.getStatus());
        User existedUser = userDAO.findUser(newUser);
        if(existedUser != null) {
            System.out.println(existedUser.getEmail() + " " + existedUser.getFirstName() + " " + existedUser.getLastName() + " " +
                    existedUser.getPassword() + " " + existedUser.getStatus() );// check
            return "redirect:/user/" + existedUser.getId();
        }
        return "redirect:/login";
    }
    @GetMapping("/user/{id}")
    public String getUserProfile(@PathVariable("id") int id, Model model) {
        User user = userDAO.findById(id);
        model.addAttribute("user", user);
//        List<Task> tasks = taskDAO.getTasksByUser(id);
//        for(Task task : tasks){
//            System.out.println(task.getTaskContent());
//        }
        model.addAttribute("tasks",taskDAO.getTasksByUser(id));
        System.out.println(user.getFirstName() + " " + user.getStatus());
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


    @PostMapping("/user/{id}/new-task")
    public String createTask(@PathVariable int id, @RequestParam("taskContent") String taskContent) {
        // Ваш код обработки данных, например, сохранение задачи в базе данных
        System.out.println("in new-task = " + taskContent);
        Task task = new Task(taskContent, id);
        taskDAO.addTask(task);
        // После успешного создания задачи, перенаправляем пользователя на какую-то страницу
        return "redirect:/user/" + id ; //
    }

    @PostMapping("/assign-task/{userId}/new-task")
    public String assignTask(@PathVariable int userId, @RequestParam("taskContent") String taskContent) {
        // Ваш код обработки данных, например, сохранение задачи в базе данных
        System.out.println("in new-task = " + taskContent);
        Task task = new Task(taskContent, userId);
        taskDAO.addTask(task);
        // После успешного создания задачи, перенаправляем пользователя на какую-то страницу
        return "redirect:/registration"; //
    }

    @GetMapping("/assign-task/{userId}")
    public String showUsersTasks(@PathVariable int userId,Model model) {
        model.addAttribute("user",userDAO.findById(userId));
        model.addAttribute("tasks",taskDAO.getTasksByUser(userId));
        return "user";
    }

}
