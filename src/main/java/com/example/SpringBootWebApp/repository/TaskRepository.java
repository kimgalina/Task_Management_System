package com.example.SpringBootWebApp.repository;

import com.example.SpringBootWebApp.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
