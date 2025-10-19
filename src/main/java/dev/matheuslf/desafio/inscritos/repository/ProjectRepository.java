package dev.matheuslf.desafio.inscritos.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import dev.matheuslf.desafio.inscritos.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
}