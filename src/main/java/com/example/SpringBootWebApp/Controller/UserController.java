/**
 * The `UserController` class is a Spring MVC controller responsible for handling
 * requests related to user management and task assignment in the Spring Boot web application.
 *
 * It is annotated with `@Controller` to indicate that this class serves as a
 *
 *
 * @author [Galina Kim]
 * @version 1.0
 * @since [16.12.2023]
 */
package com.example.SpringBootWebApp.Controller;

import com.example.SpringBootWebApp.entity.Task;
import com.example.SpringBootWebApp.entity.User;
import com.example.SpringBootWebApp.enums.UserStatus;
import com.example.SpringBootWebApp.exception.NotFoundException;
import com.example.SpringBootWebApp.exception.PasswordException;
import com.example.SpringBootWebApp.model.UserCreate;
import com.example.SpringBootWebApp.repository.UserRepository;
import com.example.SpringBootWebApp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/create-user")
    public String createUser(@ModelAttribute("user") UserCreate newUser, Model model) {
        try {
            Long newUserId = userService.createUser(newUser);
            if(newUserId != null) {
                return "redirect:/user/" + newUserId;
            }
           return "redirect:/registration";
        } catch(PasswordException exc) {
            model.addAttribute("errorMessage", exc.getMessage());
            model.addAttribute("user", newUser);
            return "signup";
        }
    }


//    @PostMapping("/login-user")
//    public String login(@ModelAttribute("user") User newUser){
//        System.out.println("пришедший с формы user " + newUser.getFirstName() + " " + newUser.getPassword());
//        User existedUser = userDAO.findUser(newUser);
//        if(existedUser != null) {
//            return "redirect:/user/" + existedUser.getId();
//        }
//        return "redirect:/login";
//    }
//
//
//    @GetMapping("user/infinity.gif")
//    public ResponseEntity<byte[]> getInfinityGif() {
//        ClassPathResource resource = new ClassPathResource("static/infinity.gif");
//        try (InputStream inputStream = resource.getInputStream()) {
//            byte[] gifBytes = inputStream.readAllBytes();
//            return ResponseEntity.ok()
//                    .contentType(MediaType.IMAGE_GIF)
//                    .body(gifBytes);
//
//        } catch (IOException ioe) {
//            System.err.println("ERROR WHILE READING FILE WITH GIF");
//            ioe.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//
    @GetMapping("/user/{id}")
    public String getUserProfile(@PathVariable("id") Long id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Пользователя с таким id нет "));
        model.addAttribute("user", user);

        switch(user.getStatus()) {
            case WORKER:
                return "worker";
            case MANAGER:
                model.addAttribute("workers", userRepository.findByStatus(UserStatus.WORKER));
                return "manager";
            case DIRECTOR:
                model.addAttribute("workers", userRepository.findByStatus(UserStatus.WORKER));
                model.addAttribute("managers", userRepository.findByStatus(UserStatus.MANAGER));
                return "director";
        }
        return null;
    }
//
//    @GetMapping("/user/{userId}/get-tasks")
//    @ResponseBody
//    public List<Task> getUserTasks(@PathVariable("userId") int userId) {
//        List<Task> userTasks = taskDAO.getTasksByUser(userId);
//        // Возвращаем список задач в виде JSON
//        return userTasks;
//    }
//
//
//
//    @GetMapping("/user/{userId}/filter-tasks")
//    public ResponseEntity<List<Task>> filterUserTasks(@PathVariable("userId") int userId, @RequestParam("filterType") String filterType) {
//        List<Task> filteredTasks = taskDAO.filterTasks(userId, filterType);
//        return new ResponseEntity<>(filteredTasks, HttpStatus.OK);
//    }
//
//
//
//    @PostMapping("/user/{userId}/new-task")
//    public String createTask(@PathVariable int userId, @RequestParam("taskContent") String taskContent) {
//        Task task = new Task(taskContent, userId);
//        taskDAO.addTask(task);
//        // После успешного создания задачи, перенаправляем пользователя на какую-то страницу
//        return "redirect:/user/" + userId ; //
//    }
//
//
//    @PostMapping("/assign-task/{userId}/new-task")
//    public String assignTask(@PathVariable int userId, @RequestParam("taskContent") String taskContent) {
//        // Ваш код обработки данных, например, сохранение задачи в базе данных
//        Task task = new Task(taskContent, userId);
//        taskDAO.addTask(task);
//        // После успешного создания задачи, перенаправляем пользователя на какую-то страницу
//        return "redirect:/registration"; //
//    }
//
//
//    @GetMapping("/assign-task/{userId}/filter-tasks")
//    public ResponseEntity<List<Task>> filterSomeoneTasks(@PathVariable("userId") int userId, @RequestParam("filterType") String filterType) {
//        List<Task> filteredTasks = taskDAO.filterTasks(userId, filterType);
//        return new ResponseEntity<>(filteredTasks, HttpStatus.OK);
//    }
//
//
//    @PatchMapping("/user/{userId}/updateTaskStatus")
//    @ResponseBody
//    public ResponseEntity<String> changeMyTaskStatus(@PathVariable("userId") int userId, @RequestParam("taskContent") String  taskContent,
//                                                     @RequestParam("isDone") boolean isDone) {
//        taskDAO.changeTaskStatus(userId, taskContent, isDone);
//        return ResponseEntity.ok("Changed task Status successfully");
//    }
//
//
//    @PatchMapping("/assign-task/{userId}/updateTaskStatus")
//    @ResponseBody
//    public ResponseEntity<String> changeSomeoneTaskStatus(@PathVariable("userId") int userId, @RequestParam("taskContent") String  taskContent,
//                                                     @RequestParam("isDone") boolean isDone) {
//        taskDAO.changeTaskStatus(userId, taskContent, isDone);
//        return ResponseEntity.ok("Changed task Status successfully");
//    }
//
//
//    @GetMapping("/assign-task/{userId}")
//    public String showUsersTasks(@PathVariable int userId,Model model) {
//        model.addAttribute("user",userDAO.findById(userId));
//        model.addAttribute("tasks",taskDAO.getTasksByUser(userId));
//        return "userTasks";
//    }
//
//
//    @PatchMapping("/assign-task/{userId}/edit-task")
//    @ResponseBody
//    public ResponseEntity<String> editUserTaskBySomeone(@PathVariable int userId, @RequestParam("taskOldContent") String taskOldText,
//                                                        @RequestParam("taskNewContent") String taskNewText) {
//        taskDAO.editTaskContentByUser(userId, taskOldText, taskNewText);
//
//        return ResponseEntity.ok("Task updated successfully");
//    }
//
//
//    @PatchMapping("/user/{userId}/edit-task")
//    @ResponseBody
//    public ResponseEntity<String> editUserTaskByUser(@PathVariable int userId, @RequestParam("taskOldContent") String taskOldText,
//                                                     @RequestParam("taskNewContent") String taskNewText) {
//        taskDAO.editTaskContentByUser(userId, taskOldText, taskNewText);
//
//        return ResponseEntity.ok("Task updated successfully");
//    }
//
//
//    @DeleteMapping("/user/{userId}/delete-task/{taskContent}")
//    @ResponseBody
//    public ResponseEntity<String> deleteUserTaskByUser(@PathVariable int userId, @PathVariable String taskContent) {
//
//        taskDAO.deleteUserTask(userId,taskContent);
//        return ResponseEntity.ok("Task deleted successfully");
//    }
//
//
//    @DeleteMapping("/assign-task/{userId}/delete-task/{taskContent}")
//    @ResponseBody
//    public ResponseEntity<String> deleteUserTaskBySomeone(@PathVariable int userId, @PathVariable String taskContent) {
//
//        taskDAO.deleteUserTask(userId,taskContent);
//        return ResponseEntity.ok("Task deleted successfully");
//    }


}
