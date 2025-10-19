package dev.matheuslf.desafio.inscritos.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.matheuslf.desafio.inscritos.dto.ProjectCreateDTO;
import dev.matheuslf.desafio.inscritos.dto.ProjectDTO;
import dev.matheuslf.desafio.inscritos.service.ProjectService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService service;
    public ProjectController(ProjectService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<ProjectDTO> create(@Valid @RequestBody ProjectCreateDTO dto) {
        ProjectDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<Page<ProjectDTO>> list(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }
}
