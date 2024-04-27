package com.example.SpringBootWebApp.service.impl;

import com.example.SpringBootWebApp.entity.Task;
import com.example.SpringBootWebApp.repository.TaskRepository;
import com.example.SpringBootWebApp.service.TaskService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Transactional
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

}
