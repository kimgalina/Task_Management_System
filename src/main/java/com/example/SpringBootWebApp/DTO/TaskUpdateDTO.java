package com.example.SpringBootWebApp.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskUpdateDTO {
    @NotBlank(message = "В чем логика создавать пустую задачу ?")
    @Size(min = 30, max = 255, message = "Задача должна состоять из 30-255 символов = )")
    private String taskOldContent;

    @NotBlank(message = "В чем логика создавать пустую задачу ?")
    @Size(min = 30, max = 255, message = "Задача должна состоять из 30-255 символов = )")
    private String taskNewContent;
}
