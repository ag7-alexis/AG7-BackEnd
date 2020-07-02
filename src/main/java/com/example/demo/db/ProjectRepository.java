package com.example.demo.db;

import com.example.demo.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Interface qui permet de créer l'entité Project dans la base de données.
 */

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project findByNameProject(String nameProject);
    Optional<Project> findById(Long aLong);
}
