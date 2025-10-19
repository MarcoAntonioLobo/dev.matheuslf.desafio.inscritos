package dev.matheuslf.desafio.inscritos.service.impl;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dev.matheuslf.desafio.inscritos.dto.TaskCreateDTO;
import dev.matheuslf.desafio.inscritos.dto.TaskDTO;
import dev.matheuslf.desafio.inscritos.entity.Project;
import dev.matheuslf.desafio.inscritos.entity.Task;
import dev.matheuslf.desafio.inscritos.entity.TaskPriority;
import dev.matheuslf.desafio.inscritos.entity.TaskStatus;
import dev.matheuslf.desafio.inscritos.repository.ProjectRepository;
import dev.matheuslf.desafio.inscritos.repository.TaskRepository;
import dev.matheuslf.desafio.inscritos.service.TaskService;
import jakarta.persistence.EntityNotFoundException;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    public TaskServiceImpl(TaskRepository taskRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public TaskDTO create(TaskCreateDTO dto) {
        if (dto.title() == null || dto.title().trim().length() < 5) {
            throw new IllegalArgumentException("Task title must be between 5 and 150 characters");
        }
        UUID projectId = dto.projectId();
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found: " + projectId));
        Task t = new Task(dto.title(), dto.description(), dto.priority() == null ? TaskPriority.MEDIUM : dto.priority(), dto.dueDate());
        t.setProject(project);
        t = taskRepository.save(t);
        return toDto(t);
    }

    @Override
    public Page<TaskDTO> search(TaskStatus status, TaskPriority priority, UUID projectId, Pageable pageable) {
        Page<Task> page;
        if (status != null && priority != null && projectId != null) {
            page = taskRepository.findAllByStatusAndPriorityAndProjectId(status, priority, projectId, pageable);
        } else if (status != null && priority != null) {
            page = taskRepository.findAllByStatusAndPriority(status, priority, pageable);
        } else if (status != null && projectId != null) {
            page = taskRepository.findAllByStatusAndProjectId(status, projectId, pageable);
        } else if (priority != null && projectId != null) {
            page = taskRepository.findAllByPriorityAndProjectId(priority, projectId, pageable);
        } else if (status != null) {
            page = taskRepository.findAllByStatus(status, pageable);
        } else if (priority != null) {
            page = taskRepository.findAllByPriority(priority, pageable);
        } else if (projectId != null) {
            page = taskRepository.findAllByProjectId(projectId, pageable);
        } else {
            page = taskRepository.findAll(pageable);
        }
        return page.map(this::toDto);
    }

    @Override
    public TaskDTO updateStatus(UUID id, TaskStatus newStatus) {
        Task t = taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Task not found: " + id));
        t.setStatus(newStatus);
        t = taskRepository.save(t);
        return toDto(t);
    }

    @Override
    public void delete(UUID id) {
        Task t = taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Task not found: " + id));
        taskRepository.delete(t);
    }

    private TaskDTO toDto(Task t) {
        return new TaskDTO(t.getId(), t.getTitle(), t.getDescription(), t.getStatus(), t.getPriority(), t.getDueDate(), t.getProject().getId());
    }
}