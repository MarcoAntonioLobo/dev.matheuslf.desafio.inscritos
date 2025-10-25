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
import dev.matheuslf.desafio.inscritos.dto.ProjectCreateDTO;
import dev.matheuslf.desafio.inscritos.dto.ProjectDTO;
import dev.matheuslf.desafio.inscritos.service.ProjectService;

@WebMvcTest(ProjectController.class)
@Import({SecurityConfig.class, GlobalExceptionHandler.class})
@ActiveProfiles("test")
class ProjectControllerTest {

    @Autowired
    private MockMvc mvc;

    @SuppressWarnings("removal")
	@MockBean
    private ProjectService projectService;

    @Test
    void listProjects_shouldReturnOk() throws Exception {
        ProjectDTO dto = new ProjectDTO();
        dto.setId(UUID.randomUUID());
        dto.setName("Projeto 1");
        dto.setDescription("Descrição");
        dto.setStartDate(LocalDate.now());
        dto.setEndDate(LocalDate.now().plusDays(10));
        dto.setTasks(List.of());

        when(projectService.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(dto)));

        mvc.perform(get("/projects")
                .with(httpBasic("admin", "123"))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].name").value("Projeto 1"));
    }

    @Test
    void createProject_shouldReturnCreated() throws Exception {
        ProjectDTO dto = new ProjectDTO();
        dto.setId(UUID.randomUUID());
        dto.setName("Projeto Criado");
        dto.setDescription("Descrição");

        when(projectService.create(any(ProjectCreateDTO.class))).thenReturn(dto);

        String payload = """
            {
                "name": "Projeto Criado",
                "description": "Descrição",
                "startDate": "%s",
                "endDate": "%s"
            }
        """.formatted(LocalDate.now(), LocalDate.now().plusDays(5));

        mvc.perform(post("/projects")
                .with(httpBasic("admin", "123"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("Projeto Criado"));
    }
}
