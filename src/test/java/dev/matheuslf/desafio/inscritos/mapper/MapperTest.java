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

@SpringJUnitConfig
@ContextConfiguration(classes = { TaskMapperImpl.class, ProjectMapperImpl.class })
class MapperTest {

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private ProjectMapper projectMapper;

    @Test
    void taskMapper_shouldWork() {
        Task task = new Task();
        task.setId(UUID.randomUUID());
        task.setTitle("Tarefa Mapper");

        TaskDTO dto = taskMapper.toDTO(task);

        assertEquals(task.getTitle(), dto.title());
        assertEquals(task.getId(), dto.id());
    }

    @Test
    void projectToDTO_andBack_shouldWork() {
        Task task = new Task();
        task.setId(UUID.randomUUID());
        task.setTitle("Tarefa Teste");

        Project project = new Project();
        project.setId(UUID.randomUUID());
        project.setName("Projeto Teste");
        project.setDescription("Descrição Teste");
        project.setStartDate(LocalDate.now());
        project.setEndDate(LocalDate.now().plusDays(10));
        project.setTasks(List.of(task));

        ProjectDTO dto = projectMapper.toDTO(project);

        assertEquals(project.getId(), dto.id());
        assertEquals(project.getName(), dto.name());
        assertEquals(project.getTasks().size(), dto.tasks().size());
        assertEquals(project.getTasks().get(0).getTitle(), dto.tasks().get(0).title());
    }
}
