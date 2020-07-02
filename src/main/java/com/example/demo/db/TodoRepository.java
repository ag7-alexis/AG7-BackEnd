package com.example.demo.db;

import com.example.demo.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface qui permet de créer l'entité Todo dans la base de données.
 */

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
}
