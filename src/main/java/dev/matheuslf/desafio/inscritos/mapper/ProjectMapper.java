package dev.matheuslf.desafio.inscritos.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import dev.matheuslf.desafio.inscritos.dto.ProjectCreateDTO;
import dev.matheuslf.desafio.inscritos.dto.ProjectDTO;
import dev.matheuslf.desafio.inscritos.entity.Project;

@Mapper(componentModel = "spring", uses = TaskMapper.class)
public interface ProjectMapper {

    ProjectDTO toDTO(Project project);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    Project toEntity(ProjectCreateDTO dto);
}
