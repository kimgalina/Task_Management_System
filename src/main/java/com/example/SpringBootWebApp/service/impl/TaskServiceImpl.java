package com.example.SpringBootWebApp.service.impl;

import com.example.SpringBootWebApp.DTO.TaskDTO;
import com.example.SpringBootWebApp.entity.Task;
import com.example.SpringBootWebApp.entity.User;
import com.example.SpringBootWebApp.mapper.TaskMapper;
import com.example.SpringBootWebApp.repository.TaskRepository;
import com.example.SpringBootWebApp.service.TaskService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
@Transactional
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    public Set<TaskDTO> filterTasks(User user, String filterType) {
        switch (filterType) {
            case "all":
                return taskMapper.toSetDTO(user.getTasks());
            case "completed":
                return taskMapper.toSetDTO(taskRepository.findByOwnerAndIsDone(user, true));
            case "uncompleted":
                return taskMapper.toSetDTO(taskRepository.findByOwnerAndIsDone(user, false));
        }
        return null;
    }

    @Override
    public ResponseEntity<Void> changeStatus(Long taskId, Boolean status) {
        Optional<Task> task =  taskRepository.findById(taskId);
        if(task.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        task.get().setIsDone(status);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
