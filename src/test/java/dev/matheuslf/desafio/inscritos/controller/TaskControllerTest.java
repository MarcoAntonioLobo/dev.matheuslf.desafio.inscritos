package dev.matheuslf.desafio.inscritos.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import dev.matheuslf.desafio.inscritos.config.GlobalExceptionHandler;
import dev.matheuslf.desafio.inscritos.config.SecurityConfig;
import dev.matheuslf.desafio.inscritos.dto.TaskCreateDTO;
import dev.matheuslf.desafio.inscritos.dto.TaskDTO;
import dev.matheuslf.desafio.inscritos.enums.TaskPriority;
import dev.matheuslf.desafio.inscritos.service.TaskService;
import jakarta.persistence.EntityNotFoundException;

@WebMvcTest(TaskController.class)
@Import({SecurityConfig.class, GlobalExceptionHandler.class})
@ActiveProfiles("test")
class TaskControllerTest {

    @Autowired
    private MockMvc mvc;

    @SuppressWarnings("removal")
	@MockBean
    private TaskService taskService;

    @Test
    void listTasks_shouldReturnOk() throws Exception {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(UUID.randomUUID());
        taskDTO.setTitle("Tarefa 1");
        taskDTO.setDescription("Descrição");
        taskDTO.setPriority(TaskPriority.HIGH);
        taskDTO.setDueDate(LocalDate.now().plusDays(5));
        taskDTO.setProjectId(UUID.randomUUID());

        // ✅ Corrigido: o controller chama service.search(...), não findAll
        when(taskService.search(any(), any(), any(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(taskDTO)));

        mvc.perform(get("/tasks")
                .with(httpBasic("admin", "123"))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].title").value("Tarefa 1"));
    }

    @Test
    void createTask_shouldReturnCreated() throws Exception {
        UUID projectId = UUID.randomUUID();
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(UUID.randomUUID());
        taskDTO.setTitle("Nova Tarefa");
        taskDTO.setDescription("Descrição");
        taskDTO.setPriority(TaskPriority.MEDIUM);
        taskDTO.setDueDate(LocalDate.now().plusDays(3));
        taskDTO.setProjectId(projectId);

        when(taskService.create(any(TaskCreateDTO.class))).thenReturn(taskDTO);

        String payload = """
            {
                "title": "Nova Tarefa",
                "description": "Descrição",
                "priority": "MEDIUM",
                "dueDate": "%s",
                "projectId": "%s"
            }
        """.formatted(LocalDate.now().plusDays(3), projectId);

        mvc.perform(post("/tasks")
                .with(httpBasic("admin", "123"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.title").value("Nova Tarefa"));
    }

    @Test
    void createTask_projectNotFound_shouldReturnNotFound() throws Exception {
        when(taskService.create(any(TaskCreateDTO.class)))
                .thenThrow(new EntityNotFoundException("Projeto não encontrado"));

        String payload = """
            {
                "title": "Tarefa Inválida",
                "description": "Descrição",
                "priority": "LOW",
                "dueDate": "%s",
                "projectId": "%s"
            }
        """.formatted(LocalDate.now().plusDays(3), UUID.randomUUID());

        mvc.perform(post("/tasks")
                .with(httpBasic("admin", "123"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value("Projeto não encontrado"));
    }

    @Test
    void listTasks_withoutAuth_shouldReturnUnauthorized() throws Exception {
        mvc.perform(get("/tasks")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnauthorized());
    }
}
