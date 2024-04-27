package com.example.SpringBootWebApp.Controller;


import com.example.SpringBootWebApp.DTO.TaskDTO;
import com.example.SpringBootWebApp.entity.Task;
import com.example.SpringBootWebApp.entity.User;
import com.example.SpringBootWebApp.model.TaskCreate;
import com.example.SpringBootWebApp.repository.TaskRepository;
import com.example.SpringBootWebApp.repository.UserRepository;
import com.example.SpringBootWebApp.service.TaskService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/tasks")
@AllArgsConstructor
@Slf4j
public class TaskController {
    private final TaskService taskService;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @GetMapping("/{userId}")
    public String getUserTasks(@PathVariable("userId") Long userId, Model model) {
        Optional<User> userEntity = userRepository.findById(userId);
        if(userEntity.isEmpty()) {
            return "user-not-found";
        }
        model.addAttribute("user", userEntity.get());
        return "userTasks";
    }


    @GetMapping("/{userId}/filter")
    @ResponseBody
    public ResponseEntity<Set<TaskDTO>> filterUserTasks(@PathVariable("userId") Long userId,
                                                        @RequestParam("filterType") String filterType) {
        Optional<User> userEntity = userRepository.findById(userId);
        if(userEntity.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Set<TaskDTO> filteredTasks = taskService.filterTasks(userEntity.get(), filterType);
        if(filteredTasks != null) {
            return new ResponseEntity<>(filteredTasks, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/{userId}/assign-task")
    @ResponseBody
    public ResponseEntity<List<String>> createTaskAndAssignToUser(@PathVariable("userId") Long userId,
                                                                  @RequestBody @Valid TaskCreate newTask,
                                                                  BindingResult bindingResult,
                                                                  Model model) {
        Optional<User> userEntity = userRepository.findById(userId);
        List<String> errors = new ArrayList<>();
        if(userEntity.isEmpty()) {
            errors.add("Такой пользователь не найден");
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        if(bindingResult.hasErrors()) {
            for(ObjectError error :  bindingResult.getAllErrors()) {
                errors.add(error.getDefaultMessage());
            }
            return  new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        Task taskEntity = new Task();
        taskEntity.setTaskContent(newTask.getTaskContent());
        taskEntity.setIsDone(false);
        taskEntity.setOwner(userEntity.get());
        taskRepository.save(taskEntity);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }


    @PatchMapping("/{taskId}/status")
    @ResponseBody
    public ResponseEntity<String> changeTaskStatus(@PathVariable("userId") int userId, @RequestParam("taskContent") String  taskContent,
                                                     @RequestParam("isDone") boolean isDone) {
//        taskDAO.changeTaskStatus(userId, taskContent, isDone);
        return ResponseEntity.ok("Changed task Status successfully");
    }




    @PutMapping("/taskId")
    @ResponseBody
    public ResponseEntity<String> editTask(@PathVariable int userId, @RequestParam("taskOldContent") String taskOldText,
                                                        @RequestParam("taskNewContent") String taskNewText) {
//        taskDAO.editTaskContentByUser(userId, taskOldText, taskNewText);

        return ResponseEntity.ok("Task updated successfully");
    }


    @DeleteMapping("/taskId")
    @ResponseBody
    public ResponseEntity<String> deleteTask(@PathVariable int userId, @PathVariable String taskContent) {

//        taskDAO.deleteUserTask(userId,taskContent);
        return ResponseEntity.ok("Task deleted successfully");
    }

}
