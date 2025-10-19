package dev.matheuslf.desafio.inscritos.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.matheuslf.desafio.inscritos.dto.TaskCreateDTO;
import dev.matheuslf.desafio.inscritos.entity.TaskPriority;
import dev.matheuslf.desafio.inscritos.repository.ProjectRepository;
import dev.matheuslf.desafio.inscritos.repository.TaskRepository;
import dev.matheuslf.desafio.inscritos.service.impl.TaskServiceImpl;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    TaskRepository taskRepository;

    @Mock
    ProjectRepository projectRepository;

    @InjectMocks
    TaskServiceImpl taskService;

    @Test
    void create_whenProjectNotFound_thenThrow() {
        UUID projectId = UUID.randomUUID();
        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        TaskCreateDTO dto = new TaskCreateDTO("Short", "desc", TaskPriority.MEDIUM, LocalDate.now(), projectId);

        assertThrows(EntityNotFoundException.class, () -> taskService.create(dto));
    }
}