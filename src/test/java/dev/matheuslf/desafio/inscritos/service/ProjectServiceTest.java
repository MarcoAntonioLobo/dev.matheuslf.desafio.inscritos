package dev.matheuslf.desafio.inscritos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import dev.matheuslf.desafio.inscritos.dto.ProjectCreateDTO;
import dev.matheuslf.desafio.inscritos.dto.ProjectDTO;
import dev.matheuslf.desafio.inscritos.entity.Project;
import dev.matheuslf.desafio.inscritos.mapper.ProjectMapper;
import dev.matheuslf.desafio.inscritos.repository.ProjectRepository;
import dev.matheuslf.desafio.inscritos.service.impl.ProjectServiceImpl;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectMapper projectMapper;

    @InjectMocks
    private ProjectServiceImpl projectService;

    private Project project;
    private ProjectDTO projectDTO;
    private ProjectCreateDTO projectCreateDTO;

    @BeforeEach
    void setUp() {
        project = Project.builder()
                .id(UUID.randomUUID())
                .name("Projeto Teste")
                .description("Descrição de teste")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(5))
                .build();

        projectDTO = ProjectDTO.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .tasks(List.of())
                .build();

        projectCreateDTO = ProjectCreateDTO.builder()
                .name("Projeto Teste")
                .description("Descrição de teste")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(5))
                .build();
    }

    @Test
    void create_shouldReturnProjectDTO() {
        when(projectMapper.toEntity(projectCreateDTO)).thenReturn(project);
        when(projectRepository.save(project)).thenReturn(project);
        when(projectMapper.toDTO(project)).thenReturn(projectDTO);

        ProjectDTO result = projectService.create(projectCreateDTO);

        assertNotNull(result);
        assertEquals(projectDTO.getName(), result.getName());
        verify(projectRepository).save(project);
    }

    @Test
    void findAll_shouldReturnPagedProjects() {
        Page<Project> page = new PageImpl<>(List.of(project));
        when(projectRepository.findAll(any(PageRequest.class))).thenReturn(page);
        when(projectMapper.toDTO(project)).thenReturn(projectDTO);

        Page<ProjectDTO> result = projectService.findAll(PageRequest.of(0, 5));

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("Projeto Teste", result.getContent().get(0).getName());
        verify(projectRepository).findAll(any(PageRequest.class));
    }

    @Test
    void findById_shouldReturnProjectDTO() {
        when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));
        when(projectMapper.toDTO(project)).thenReturn(projectDTO);

        ProjectDTO result = projectService.findById(project.getId());

        assertNotNull(result);
        assertEquals(projectDTO.getId(), result.getId());
        verify(projectRepository).findById(project.getId());
    }

    @Test
    void findById_shouldThrowEntityNotFound_whenNotExists() {
        UUID id = UUID.randomUUID();
        when(projectRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> projectService.findById(id));
        verify(projectRepository).findById(id);
    }

    @Test
    void delete_shouldCallRepositoryDelete() {
        when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));

        projectService.delete(project.getId());

        verify(projectRepository).delete(project);
    }

    @Test
    void delete_shouldThrowEntityNotFound_whenNotExists() {
        UUID id = UUID.randomUUID();
        when(projectRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> projectService.delete(id));
        verify(projectRepository).findById(id);
        verify(projectRepository, never()).delete(any());
    }
}
