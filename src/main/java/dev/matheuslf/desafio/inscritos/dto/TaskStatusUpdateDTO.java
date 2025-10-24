package dev.matheuslf.desafio.inscritos.dto;

import dev.matheuslf.desafio.inscritos.enums.TaskStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskStatusUpdateDTO {
    private TaskStatus status;
}
