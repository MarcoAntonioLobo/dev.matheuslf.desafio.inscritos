package dev.matheuslf.desafio.inscritos.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import dev.matheuslf.desafio.inscritos.dto.TaskCreateDTO;
import dev.matheuslf.desafio.inscritos.dto.TaskDTO;
import dev.matheuslf.desafio.inscritos.entity.Project;
import dev.matheuslf.desafio.inscritos.entity.Task;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(source = "project.id", target = "projectId")
    TaskDTO toDTO(Task task);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", expression = "java(dev.matheuslf.desafio.inscritos.enums.TaskStatus.TODO)")
    @Mapping(source = "projectId", target = "project", qualifiedByName = "mapProjectFromId")
    Task toEntity(TaskCreateDTO dto);

    @Named("mapProjectFromId")
    default Project mapProjectFromId(java.util.UUID projectId) {
        if (projectId == null) return null;
        Project project = new Project();
        project.setId(projectId);
        return project;
    }
}
