package com.example.demo.db;

import com.example.demo.model.Difficulty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DifficultyRepository extends JpaRepository<Difficulty, Long> {
    Optional<Difficulty> findByLibDifficulty(String lib);
}
