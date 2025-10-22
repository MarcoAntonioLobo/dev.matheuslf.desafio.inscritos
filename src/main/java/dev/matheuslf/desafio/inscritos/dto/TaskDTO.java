package dev.matheuslf.desafio.inscritos.dto;

import java.time.LocalDate;
import java.util.UUID;

import dev.matheuslf.desafio.inscritos.entity.TaskPriority;
import dev.matheuslf.desafio.inscritos.entity.TaskStatus;

public record TaskDTO(UUID id, String title, String description, TaskStatus status, TaskPriority priority,
		LocalDate dueDate, UUID projectId) {

}