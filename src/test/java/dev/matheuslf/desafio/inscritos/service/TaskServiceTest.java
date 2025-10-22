package dev.matheuslf.desafio.inscritos.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import dev.matheuslf.desafio.inscritos.mapper.ProjectMapper;
import dev.matheuslf.desafio.inscritos.repository.ProjectRepository;
import dev.matheuslf.desafio.inscritos.service.impl.ProjectServiceImpl;
import jakarta.persistence.EntityNotFoundException;

class TaskServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectMapper projectMapper;

    private ProjectServiceImpl projectService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        projectService = new ProjectServiceImpl(projectRepository, projectMapper);
    }

    @Test
    void create_whenProjectNotFound_thenThrow() {
        UUID fakeId = UUID.randomUUID();

        when(projectRepository.findById(any(UUID.class)))
            .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            projectService.findById(fakeId);
        });
    }
}

