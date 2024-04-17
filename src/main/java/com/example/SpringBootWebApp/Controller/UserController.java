/**
 * The `UserController` class is a Spring MVC controller responsible for handling
 * requests related to user management and task assignment in the Spring Boot web application.
 *
 * It is annotated with `@Controller` to indicate that this class serves as a
 * Spring MVC controller. This controller interacts with the `UserDAO` and `TaskDAO`
 * components to manage user information and tasks.
 *
 * @author [Galina Kim]
 * @version 1.0
 * @since [16.12.2023]
 */
package com.example.SpringBootWebApp.Controller;

import com.example.SpringBootWebApp.DAO.TaskDAO;
import com.example.SpringBootWebApp.DAO.UserDAO;
import com.example.SpringBootWebApp.Models.Task;
import com.example.SpringBootWebApp.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class UserController {

    private UserDAO userDAO;
    private TaskDAO taskDAO;

    /**
     * Constructor for UserController.
     *
     * @param userDAO The data access object for managing user information.
     * @param taskDAO The data access object for managing tasks.
     */
    @Autowired
    public UserController(UserDAO userDAO, TaskDAO taskDAO) {
        this.userDAO = userDAO;
        this.taskDAO = taskDAO;
    }

    /**
     * Handles HTTP POST requests to "/create-user" endpoint for user registration.
     *
     * @param newUser The user object populated from the registration form.
     * @param status The status of the user (e.g., worker, manager).
     * @return A redirect to the user profile page if registration is successful, otherwise to the registration page.
     */
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

    /**
     * Handles HTTP POST requests to "/login-user" endpoint for user login.
     *
     * @param newUser The user object populated from the login form.
     * @return A redirect to the user profile page if login is successful, otherwise to the login page.
     */
    @PostMapping("/login-user")
    public String login(@ModelAttribute("user") User newUser){
        System.out.println("пришедший с формы user " + newUser.getFirstName() + " " + newUser.getPassword());
        User existedUser = userDAO.findUser(newUser);
        if(existedUser != null) {
            return "redirect:/user/" + existedUser.getId();
        }
        return "redirect:/login";
    }


    @GetMapping("user/infinity.gif")
    public ResponseEntity<byte[]> getInfinityGif() {
        ClassPathResource resource = new ClassPathResource("static/infinity.gif");
        try (InputStream inputStream = resource.getInputStream()) {
            byte[] gifBytes = inputStream.readAllBytes();
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_GIF)
                    .body(gifBytes);

        } catch (IOException ioe) {
            System.err.println("ERROR WHILE READING FILE WITH GIF");
            ioe.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    /**
     * Handles HTTP GET requests to "/user/{id}" endpoint to display user profile.
     *
     * @param id The unique identifier of the user.
     * @param model The `Model` object used to add attributes to the view.
     * @return The logical view name based on the user's status (e.g., "worker", "manager", "director").
     */
    @GetMapping("/user/{id}")
    public String getUserProfile(@PathVariable("id") int id, Model model) {
        User user = userDAO.findById(id);
        model.addAttribute("user", user);

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

    @GetMapping("/user/{userId}/get-tasks")
    @ResponseBody
    public List<Task> getUserTasks(@PathVariable("userId") int userId) {
        List<Task> userTasks = taskDAO.getTasksByUser(userId);
        // Возвращаем список задач в виде JSON
        return userTasks;
    }


    /**
     * Handles HTTP GET requests to "/user/{userId}/filter-tasks" endpoint to filter tasks for a specific user.
     *
     * @param userId The unique identifier of the user.
     * @param filterType The type of filtering to apply to tasks.
     * @return A ResponseEntity containing a list of filtered tasks and an HTTP status code.
     */
    @GetMapping("/user/{userId}/filter-tasks")
    public ResponseEntity<List<Task>> filterUserTasks(@PathVariable("userId") int userId, @RequestParam("filterType") String filterType) {
        List<Task> filteredTasks = taskDAO.filterTasks(userId, filterType);
        return new ResponseEntity<>(filteredTasks, HttpStatus.OK);
    }


    /**
     * Handles HTTP POST requests to "/user/{userId}/new-task" endpoint to create a new task for a user.
     *
     * @param userId The unique identifier of the user.
     * @param taskContent The content of the new task.
     * @return A redirect to the user profile page after creating the task.
     */
    @PostMapping("/user/{userId}/new-task")
    public String createTask(@PathVariable int userId, @RequestParam("taskContent") String taskContent) {
        Task task = new Task(taskContent, userId);
        taskDAO.addTask(task);
        // После успешного создания задачи, перенаправляем пользователя на какую-то страницу
        return "redirect:/user/" + userId ; //
    }

    /**
     * Handles HTTP POST requests to "/assign-task/{userId}/new-task" endpoint to create a new task for a user.
     *
     * @param userId The unique identifier of the user.
     * @param taskContent The content of the new task.
     * @return A redirect to the user profile page after creating the task.
     */
    @PostMapping("/assign-task/{userId}/new-task")
    public String assignTask(@PathVariable int userId, @RequestParam("taskContent") String taskContent) {
        // Ваш код обработки данных, например, сохранение задачи в базе данных
        Task task = new Task(taskContent, userId);
        taskDAO.addTask(task);
        // После успешного создания задачи, перенаправляем пользователя на какую-то страницу
        return "redirect:/registration"; //
    }

    /**
     * Handles HTTP GET requests to "/assign-task/{userId}/filter-tasks" endpoint to filter tasks for a specific user.
     *
     * @param userId The unique identifier of the user.
     * @param filterType The type of filtering to apply to tasks.
     * @return A ResponseEntity containing a list of filtered tasks and an HTTP status code.
     */
    @GetMapping("/assign-task/{userId}/filter-tasks")
    public ResponseEntity<List<Task>> filterSomeoneTasks(@PathVariable("userId") int userId, @RequestParam("filterType") String filterType) {
        List<Task> filteredTasks = taskDAO.filterTasks(userId, filterType);
        return new ResponseEntity<>(filteredTasks, HttpStatus.OK);
    }

    /**
     * Handles HTTP PATCH requests to "/user/{userId}/updateTaskStatus" endpoint to change the status of a user's task.
     *
     * @param userId The unique identifier of the user.
     * @param taskContent The content of the task.
     * @param isDone The new status of the task.
     * @return A ResponseEntity with a success message and an HTTP status code.
     */
    @PatchMapping("/user/{userId}/updateTaskStatus")
    @ResponseBody
    public ResponseEntity<String> changeMyTaskStatus(@PathVariable("userId") int userId, @RequestParam("taskContent") String  taskContent,
                                                     @RequestParam("isDone") boolean isDone) {
        taskDAO.changeTaskStatus(userId, taskContent, isDone);
        return ResponseEntity.ok("Changed task Status successfully");
    }

    /**
     * Handles HTTP PATCH requests to "/assign-task/{userId}/updateTaskStatus" endpoint to change the status of a user's task.
     *
     * @param userId The unique identifier of the user.
     * @param taskContent The content of the task.
     * @param isDone The new status of the task.
     * @return A ResponseEntity with a success message and an HTTP status code.
     */
    @PatchMapping("/assign-task/{userId}/updateTaskStatus")
    @ResponseBody
    public ResponseEntity<String> changeSomeoneTaskStatus(@PathVariable("userId") int userId, @RequestParam("taskContent") String  taskContent,
                                                     @RequestParam("isDone") boolean isDone) {
        taskDAO.changeTaskStatus(userId, taskContent, isDone);
        return ResponseEntity.ok("Changed task Status successfully");
    }

    /**
     * Handles HTTP GET requests to "/assign-task/{userId}" endpoint to display tasks assigned to a user.
     *
     * @param userId The unique identifier of the user.
     * @param model The `Model` object used to add attributes to the view.
     * @return The logical view name for displaying user tasks.
     */
    @GetMapping("/assign-task/{userId}")
    public String showUsersTasks(@PathVariable int userId,Model model) {
        model.addAttribute("user",userDAO.findById(userId));
        model.addAttribute("tasks",taskDAO.getTasksByUser(userId));
        return "userTasks";
    }

    /**
     * Handles HTTP PATCH requests to "/assign-task/{userId}/edit-task" endpoint to edit a task assigned to a user.
     *
     * @param userId The unique identifier of the user.
     * @param taskOldText The old content of the task.
     * @param taskNewText The new content of the task.
     * @return A ResponseEntity with a success message and an HTTP status code.
     */
    @PatchMapping("/assign-task/{userId}/edit-task")
    @ResponseBody
    public ResponseEntity<String> editUserTaskBySomeone(@PathVariable int userId, @RequestParam("taskOldContent") String taskOldText,
                                                        @RequestParam("taskNewContent") String taskNewText) {
        taskDAO.editTaskContentByUser(userId, taskOldText, taskNewText);

        return ResponseEntity.ok("Task updated successfully");
    }

    /**
     * Handles HTTP PATCH requests to "/user/{userId}/edit-task" endpoint to edit a task assigned to a user.
     *
     * @param userId The unique identifier of the user.
     * @param taskOldText The old content of the task.
     * @param taskNewText The new content of the task.
     * @return A ResponseEntity with a success message and an HTTP status code.
     */
    @PatchMapping("/user/{userId}/edit-task")
    @ResponseBody
    public ResponseEntity<String> editUserTaskByUser(@PathVariable int userId, @RequestParam("taskOldContent") String taskOldText,
                                                     @RequestParam("taskNewContent") String taskNewText) {
        taskDAO.editTaskContentByUser(userId, taskOldText, taskNewText);

        return ResponseEntity.ok("Task updated successfully");
    }

    /**
     * Handles HTTP DELETE requests to "/user/{userId}/delete-task/{taskContent}" endpoint to delete a user's own task.
     *
     * @param userId The unique identifier of the user.
     * @param taskContent The content of the task to be deleted.
     * @return A ResponseEntity with a success message and an HTTP status code.
     */
    @DeleteMapping("/user/{userId}/delete-task/{taskContent}")
    @ResponseBody
    public ResponseEntity<String> deleteUserTaskByUser(@PathVariable int userId, @PathVariable String taskContent) {

        taskDAO.deleteUserTask(userId,taskContent);
        return ResponseEntity.ok("Task deleted successfully");
    }

    /**
     * Handles HTTP DELETE requests to "/assign-task/{userId}/delete-task/{taskContent}" endpoint to delete a task assigned to a user.
     *
     * @param userId The unique identifier of the user.
     * @param taskContent The content of the task to be deleted.
     * @return A ResponseEntity with a success message and an HTTP status code.
     */
    @DeleteMapping("/assign-task/{userId}/delete-task/{taskContent}")
    @ResponseBody
    public ResponseEntity<String> deleteUserTaskBySomeone(@PathVariable int userId, @PathVariable String taskContent) {

        taskDAO.deleteUserTask(userId,taskContent);
        return ResponseEntity.ok("Task deleted successfully");
    }


}
