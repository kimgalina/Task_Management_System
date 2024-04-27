package com.example.SpringBootWebApp.service;

import com.example.SpringBootWebApp.DTO.TaskDTO;
import com.example.SpringBootWebApp.entity.Task;
import com.example.SpringBootWebApp.entity.User;

import java.util.List;
import java.util.Set;

public interface TaskService {
    Set<TaskDTO> filterTasks(User user, String filterType);
}
