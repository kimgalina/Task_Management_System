package com.example.SpringBootWebApp.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TaskCreate {

    @NotBlank(message = "В чем логика создавать пустую задачу ?")
    @Size(min = 30, max = 255, message = "Задача должна состоять из 30-255 символов = )")
    private String taskContent;
}
