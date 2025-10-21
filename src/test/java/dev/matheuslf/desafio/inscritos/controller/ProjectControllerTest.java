package dev.matheuslf.desafio.inscritos.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import dev.matheuslf.desafio.inscritos.dto.ProjectDTO;
import dev.matheuslf.desafio.inscritos.service.ProjectService;

@WebMvcTest(ProjectController.class)
class ProjectControllerTest {

    @Autowired
    MockMvc mvc;

	@SuppressWarnings("removal")
	@MockBean
    ProjectService projectService;

    @Test
    void list_returnsOk() throws Exception {
        Page<ProjectDTO> page = new PageImpl<>(java.util.List.of(new ProjectDTO(UUID.randomUUID(), "P", "d", LocalDate.now(), null)));
        Mockito.when(projectService.findAll(any(Pageable.class))).thenReturn(page);

        mvc.perform(get("/projects")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}