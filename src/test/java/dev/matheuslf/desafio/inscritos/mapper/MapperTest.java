package dev.matheuslf.desafio.inscritos.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import dev.matheuslf.desafio.inscritos.dto.ProjectDTO;
import dev.matheuslf.desafio.inscritos.dto.TaskDTO;
import dev.matheuslf.desafio.inscritos.entity.Project;
import dev.matheuslf.desafio.inscritos.entity.Task;
import dev.matheuslf.desafio.inscritos.enums.TaskPriority;
import dev.matheuslf.desafio.inscritos.enums.TaskStatus;

@SpringJUnitConfig
@ContextConfiguration(classes = { TaskMapperImpl.class, ProjectMapperImpl.class })
class MapperTest {

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private ProjectMapper projectMapper;

    @Test
    void taskMapper_shouldWork() {
        Task task = Task.builder()
                .id(UUID.randomUUID())
                .title("Tarefa Mapper")
                .description("Descrição Mapper")
                .status(TaskStatus.TODO)
                .priority(TaskPriority.MEDIUM)
                .dueDate(LocalDate.now().plusDays(5))
                .build();

        TaskDTO dto = taskMapper.toDTO(task);

        assertEquals(task.getId(), dto.getId());
        assertEquals(task.getTitle(), dto.getTitle());
        assertEquals(task.getStatus(), dto.getStatus());
        assertEquals(task.getPriority(), dto.getPriority());
        assertEquals(task.getDueDate(), dto.getDueDate());
    }

    @Test
    void projectToDTO_andBack_shouldWork() {
        Task task = Task.builder()
                .id(UUID.randomUUID())
                .title("Tarefa Mapper")
                .description("Descrição Mapper")
                .status(TaskStatus.TODO)
                .priority(TaskPriority.MEDIUM)
                .dueDate(LocalDate.now().plusDays(5))
                .build();

        Project project = Project.builder()
                .id(UUID.randomUUID())
                .name("Projeto Teste")
                .description("Descrição Teste")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(10))
                .tasks(List.of(task))
                .build();

        ProjectDTO dto = projectMapper.toDTO(project);

        assertEquals(project.getId(), dto.getId());
        assertEquals(project.getName(), dto.getName());
        assertEquals(project.getDescription(), dto.getDescription());
        assertEquals(project.getStartDate(), dto.getStartDate());
        assertEquals(project.getEndDate(), dto.getEndDate());
        assertEquals(project.getTasks().size(), dto.getTasks().size());

        TaskDTO taskDTO = dto.getTasks().get(0);
        Task taskOriginal = project.getTasks().get(0);

        assertEquals(taskOriginal.getId(), taskDTO.getId());
        assertEquals(taskOriginal.getTitle(), taskDTO.getTitle());
        assertEquals(taskOriginal.getStatus(), taskDTO.getStatus());
        assertEquals(taskOriginal.getPriority(), taskDTO.getPriority());
        assertEquals(taskOriginal.getDueDate(), taskDTO.getDueDate());
    }
}


