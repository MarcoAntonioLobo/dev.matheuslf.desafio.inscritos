package dev.matheuslf.desafio.inscritos.dto;

import java.time.LocalDate;
import java.util.UUID;

import dev.matheuslf.desafio.inscritos.enums.TaskPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskCreateDTO {
    @NotBlank
    @Size(min = 5, max = 150)
    private String title;
    private String description;
    private TaskPriority priority;
    private LocalDate dueDate;
    private UUID projectId;
}
