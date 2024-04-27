package com.example.SpringBootWebApp.mapper;

import com.example.SpringBootWebApp.DTO.TaskDTO;
import com.example.SpringBootWebApp.entity.Task;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    Set<TaskDTO> toSetDTO(Set<Task> task);

    TaskDTO toDTO(Task task);
}
