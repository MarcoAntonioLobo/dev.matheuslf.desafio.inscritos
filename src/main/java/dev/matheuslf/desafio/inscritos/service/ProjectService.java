package dev.matheuslf.desafio.inscritos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import dev.matheuslf.desafio.inscritos.dto.ProjectCreateDTO;
import dev.matheuslf.desafio.inscritos.dto.ProjectDTO;

public interface ProjectService {
    ProjectDTO create(ProjectCreateDTO dto);
    Page<ProjectDTO> findAll(Pageable pageable);
    ProjectDTO findById(java.util.UUID id);
}