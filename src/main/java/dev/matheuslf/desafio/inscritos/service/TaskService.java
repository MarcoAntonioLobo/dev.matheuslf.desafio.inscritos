package dev.matheuslf.desafio.inscritos.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import dev.matheuslf.desafio.inscritos.dto.TaskCreateDTO;
import dev.matheuslf.desafio.inscritos.dto.TaskDTO;
import dev.matheuslf.desafio.inscritos.enums.TaskPriority;
import dev.matheuslf.desafio.inscritos.enums.TaskStatus;

public interface TaskService {
    TaskDTO create(TaskCreateDTO dto);
    Page<TaskDTO> search(TaskStatus status, TaskPriority priority, UUID projectId, Pageable pageable);
    TaskDTO updateStatus(UUID id, TaskStatus status);
    void delete(UUID id);
	Object findAll(Pageable pageable);
}