package dev.matheuslf.desafio.inscritos.service.impl;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dev.matheuslf.desafio.inscritos.dto.TaskCreateDTO;
import dev.matheuslf.desafio.inscritos.dto.TaskDTO;
import dev.matheuslf.desafio.inscritos.entity.Task;
import dev.matheuslf.desafio.inscritos.entity.TaskPriority;
import dev.matheuslf.desafio.inscritos.entity.TaskStatus;
import dev.matheuslf.desafio.inscritos.mapper.TaskMapper;
import dev.matheuslf.desafio.inscritos.repository.TaskRepository;
import dev.matheuslf.desafio.inscritos.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }


    @Override
    public TaskDTO create(TaskCreateDTO dto) {
        Task task = taskMapper.toEntity(dto);
        taskRepository.save(task);
        return taskMapper.toDTO(task);
    }

    @Override
    public Page<TaskDTO> search(TaskStatus status, TaskPriority priority, UUID projectId, Pageable pageable) {
        Page<Task> tasks = taskRepository.findByFilters(status, priority, projectId, pageable);
        return tasks.map(taskMapper::toDTO);
    }

    @Override
    public TaskDTO updateStatus(UUID id, TaskStatus status) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task não encontrada"));
        task.setStatus(status);
        taskRepository.save(task);
        return taskMapper.toDTO(task);
    }

    @Override
    public void delete(UUID taskId) {
        taskRepository.deleteById(taskId);
    }
}
