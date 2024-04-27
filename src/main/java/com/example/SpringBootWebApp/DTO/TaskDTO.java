package com.example.SpringBootWebApp.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskDTO {

    private Long id;


    private String taskContent;


    private Boolean isDone;
}
