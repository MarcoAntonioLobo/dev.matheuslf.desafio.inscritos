package dev.matheuslf.desafio.inscritos.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dev.matheuslf.desafio.inscritos.dto.ProjectCreateDTO;
import dev.matheuslf.desafio.inscritos.dto.ProjectDTO;
import dev.matheuslf.desafio.inscritos.entity.Project;
import dev.matheuslf.desafio.inscritos.repository.ProjectRepository;
import dev.matheuslf.desafio.inscritos.service.ProjectService;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public ProjectDTO create(ProjectCreateDTO dto) {
        if (dto.name() == null || dto.name().trim().length() < 3) {
            throw new IllegalArgumentException("Project name must be between 3 and 100 characters");
        }
        Project p = new Project(dto.name(), dto.description(), dto.startDate(), dto.endDate());
        p = projectRepository.save(p);
        return toDto(p);
    }

    @Override
    public Page<ProjectDTO> findAll(Pageable pageable) {
        return projectRepository.findAll(pageable).map(this::toDto);
    }

    @Override
    public ProjectDTO findById(java.util.UUID id) {
        Project p = projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project not found: " + id));
        return toDto(p);
    }

    private ProjectDTO toDto(Project p) {
        return new ProjectDTO(p.getId(), p.getName(), p.getDescription(), p.getStartDate(), p.getEndDate());
    }
}
