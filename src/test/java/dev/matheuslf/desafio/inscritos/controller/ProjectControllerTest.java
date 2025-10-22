package dev.matheuslf.desafio.inscritos.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import dev.matheuslf.desafio.inscritos.config.SecurityConfig;
import dev.matheuslf.desafio.inscritos.dto.ProjectDTO;
import dev.matheuslf.desafio.inscritos.dto.TaskDTO;
import dev.matheuslf.desafio.inscritos.service.ProjectService;

@WebMvcTest(ProjectController.class)
@Import(SecurityConfig.class)
class ProjectControllerTest {

    @Autowired
    MockMvc mvc;

    @SuppressWarnings("removal")
    @MockBean
    ProjectService projectService;

    @Test
    void list_returnsOk() throws Exception {
        List<TaskDTO> tasks = List.of();

        ProjectDTO projectDTO = new ProjectDTO(
            UUID.randomUUID(),
            "P",
            "d",
            LocalDate.now(),
            null,
            tasks
        );

        Page<ProjectDTO> page = new PageImpl<>(List.of(projectDTO));
        Mockito.when(projectService.findAll(any(Pageable.class))).thenReturn(page);

        mvc.perform(get("/projects")
                .with(httpBasic("admin", "123"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
