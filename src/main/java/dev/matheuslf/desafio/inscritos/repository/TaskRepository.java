package dev.matheuslf.desafio.inscritos.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import dev.matheuslf.desafio.inscritos.entity.Task;
import dev.matheuslf.desafio.inscritos.entity.TaskPriority;
import dev.matheuslf.desafio.inscritos.entity.TaskStatus;

public interface TaskRepository extends JpaRepository<Task, UUID> {
	Page<Task> findAllByStatusAndPriorityAndProjectId(TaskStatus status, TaskPriority priority, UUID projectId,
			Pageable pageable);

	Page<Task> findAllByStatusAndPriority(TaskStatus status, TaskPriority priority, Pageable pageable);

	Page<Task> findAllByStatusAndProjectId(TaskStatus status, UUID projectId, Pageable pageable);

	Page<Task> findAllByPriorityAndProjectId(TaskPriority priority, UUID projectId, Pageable pageable);

	Page<Task> findAllByStatus(TaskStatus status, Pageable pageable);

	Page<Task> findAllByPriority(TaskPriority priority, Pageable pageable);

	Page<Task> findAllByProjectId(UUID projectId, Pageable pageable);
}