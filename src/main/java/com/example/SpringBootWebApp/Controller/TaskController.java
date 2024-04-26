package com.example.SpringBootWebApp.Controller;


import com.example.SpringBootWebApp.entity.Task;
import com.example.SpringBootWebApp.model.TaskCreate;
import com.example.SpringBootWebApp.service.TaskService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tasks")
@AllArgsConstructor
@Slf4j
public class TaskController {
    private final TaskService taskService;


    @PostMapping("/assign-to/{userId}")
    public String createTaskAndAssignToUser(@RequestBody TaskCreate newTask) {
//        taskService.createTask(newTask);
        log.info(newTask.getUserId().toString() + " " + newTask.getContent());
        return "redirect:/users/" + newTask.getUserId() ;
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
