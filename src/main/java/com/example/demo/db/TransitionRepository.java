package com.example.demo.db;

import com.example.demo.model.State;
import com.example.demo.model.Todo;
import com.example.demo.model.Transition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransitionRepository extends JpaRepository<Transition, Long> {
    Transition getTransitionByStateAndTodo(State state, Todo todo);
}
