package com.example.SpringBootWebApp.repository;

import com.example.SpringBootWebApp.entity.Task;
import com.example.SpringBootWebApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByOwner(User owner);

    Set<Task> findByOwnerAndIsDone(User user, Boolean isDone);
}
