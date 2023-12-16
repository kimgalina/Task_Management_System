package com.example.SpringBootWebApp.Controller;

import com.example.SpringBootWebApp.DAO.TaskDAO;
import com.example.SpringBootWebApp.DAO.UserDAO;
import com.example.SpringBootWebApp.Models.Task;
import com.example.SpringBootWebApp.Models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public String createUser(@ModelAttribute("user") User newUser, @RequestParam("status") String status){
        status = status.toLowerCase();
        newUser.setStatus(status);
        // когда пользователь добавляется в базу его полю id присваеивается занчение
        if(userDAO.addUser(newUser)) {
            return "redirect:/user/" + newUser.getId();
        }

        return "redirect:/registration";
    }

    @PostMapping("/login-user")
    public String login(@ModelAttribute("user") User newUser){
        System.out.println("пришедший с формы user " + newUser.getFirstName() + " " + newUser.getPassword());
        User existedUser = userDAO.findUser(newUser);
        if(existedUser != null) {
            return "redirect:/user/" + existedUser.getId();
        }
        return "redirect:/login";
    }
    @GetMapping("/user/{id}")
    public String getUserProfile(@PathVariable("id") int id, Model model) {
        User user = userDAO.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("tasks",taskDAO.getTasksByUser(id));

        switch(user.getStatus()) {
            case "worker" :
                return "worker";
            case "manager":
                model.addAttribute("workers",userDAO.getAllWorkers());
                return "manager";
            case "director":
                model.addAttribute("workers",userDAO.getAllWorkers());
                model.addAttribute("managers",userDAO.getAllManagers());
                return "director";
        }
        return null;
    }
    @GetMapping("/user/{userId}/filter-tasks")
    public ResponseEntity<List<Task>> filterUserTasks(@PathVariable("userId") int userId, @RequestParam("filterType") String filterType) {
        List<Task> filteredTasks = taskDAO.filterTasks(userId, filterType);
        return new ResponseEntity<>(filteredTasks, HttpStatus.OK);
    }

    @PostMapping("/user/{userId}/new-task")
    public String createTask(@PathVariable int userId, @RequestParam("taskContent") String taskContent) {
        Task task = new Task(taskContent, userId);
        taskDAO.addTask(task);
        // После успешного создания задачи, перенаправляем пользователя на какую-то страницу
        return "redirect:/user/" + userId ; //
    }

    @PostMapping("/assign-task/{userId}/new-task")
    public String assignTask(@PathVariable int userId, @RequestParam("taskContent") String taskContent) {
        // Ваш код обработки данных, например, сохранение задачи в базе данных
        Task task = new Task(taskContent, userId);
        taskDAO.addTask(task);
        // После успешного создания задачи, перенаправляем пользователя на какую-то страницу
        return "redirect:/registration"; //
    }

    @GetMapping("/assign-task/{userId}/filter-tasks")
    public ResponseEntity<List<Task>> filterSomeoneTasks(@PathVariable("userId") int userId, @RequestParam("filterType") String filterType) {
        List<Task> filteredTasks = taskDAO.filterTasks(userId, filterType);
        return new ResponseEntity<>(filteredTasks, HttpStatus.OK);
    }

    @PatchMapping("/user/{userId}/updateTaskStatus")
    @ResponseBody
    public ResponseEntity<String> changeMyTaskStatus(@PathVariable("userId") int userId, @RequestParam("taskContent") String  taskContent,
                                                     @RequestParam("isDone") boolean isDone) {
        taskDAO.changeTaskStatus(userId, taskContent, isDone);
        return ResponseEntity.ok("Changed task Status successfully");
    }

    @PatchMapping("/assign-task/{userId}/updateTaskStatus")
    @ResponseBody
    public ResponseEntity<String> changeSomeoneTaskStatus(@PathVariable("userId") int userId, @RequestParam("taskContent") String  taskContent,
                                                     @RequestParam("isDone") boolean isDone) {
        taskDAO.changeTaskStatus(userId, taskContent, isDone);
        return ResponseEntity.ok("Changed task Status successfully");
    }

    @GetMapping("/assign-task/{userId}")
    public String showUsersTasks(@PathVariable int userId,Model model) {
        model.addAttribute("user",userDAO.findById(userId));
        model.addAttribute("tasks",taskDAO.getTasksByUser(userId));
        return "userTasks";
    }

    @PatchMapping("/assign-task/{userId}/edit-task")
    @ResponseBody
    public ResponseEntity<String> editUserTaskBySomeone(@PathVariable int userId, @RequestParam("taskOldContent") String taskOldText,
                                                        @RequestParam("taskNewContent") String taskNewText) {
        // изменить таску с taskContent = taskText на taskContent = taskNewText
        taskDAO.editTaskContentByUser(userId, taskOldText, taskNewText);

        return ResponseEntity.ok("Task updated successfully");
    }

    @PatchMapping("/user/{userId}/edit-task")
    @ResponseBody
    public ResponseEntity<String> editUserTaskByUser(@PathVariable int userId, @RequestParam("taskOldContent") String taskOldText,
                                                     @RequestParam("taskNewContent") String taskNewText) {
        // изменить таску с taskContent = taskText на taskContent = taskNewText
        taskDAO.editTaskContentByUser(userId, taskOldText, taskNewText);

        return ResponseEntity.ok("Task updated successfully");
    }

    @DeleteMapping("/user/{userId}/delete-task/{taskContent}")
    @ResponseBody
    public ResponseEntity<String> deleteUserTaskByUser(@PathVariable int userId, @PathVariable String taskContent) {

        taskDAO.deleteUserTask(userId,taskContent);
        return ResponseEntity.ok("Task deleted successfully");
    }
    @DeleteMapping("/assign-task/{userId}/delete-task/{taskContent}")
    @ResponseBody
    public ResponseEntity<String> deleteUserTaskBySomeone(@PathVariable int userId, @PathVariable String taskContent) {

        taskDAO.deleteUserTask(userId,taskContent);
        return ResponseEntity.ok("Task deleted successfully");
    }


}
