package dev.matheuslf.desafio.inscritos.dto;

import java.time.LocalDate;
import java.util.UUID;

import dev.matheuslf.desafio.inscritos.entity.TaskPriority;

public record TaskCreateDTO(
        String title,
        String description,
        TaskPriority priority,
        LocalDate dueDate,
        UUID projectId
) {}