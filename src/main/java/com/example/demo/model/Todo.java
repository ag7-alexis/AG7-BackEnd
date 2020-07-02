package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * Objet Todo qui sera mémorisé en base de données.
 */

@Entity
public class Todo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long idTodo;
    private String descTodo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Difficulty difficultyTodo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Project leProject;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "todo", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Transition> lesTransitions;

    public Todo(String descTodo, Difficulty difficultyTodo, Project leProject) {
        this.descTodo = descTodo;
        this.leProject = leProject;
        this.difficultyTodo = difficultyTodo;
    }

    public Todo(String idTodo, String descTodo, Difficulty difficultyTodo, Project leProject) {
        this.idTodo = Long.valueOf(idTodo);
        this.descTodo = descTodo;
        this.leProject = leProject;
        this.difficultyTodo = difficultyTodo;
    }

    public Todo(Long idTodo, String descTodo, Difficulty difficultyTodo, Project leProject) {
        this.idTodo = idTodo;
        this.descTodo = descTodo;
        this.leProject = leProject;
        this.difficultyTodo = difficultyTodo;
    }

    public Todo() {
    }

    public String getDescTodo() {
        return descTodo;
    }

    public void setDescTodo(String descTodo) {
        this.descTodo = descTodo;
    }

    public long getId() {
        return idTodo;
    }

    public void setId(long id) {
        this.idTodo = id;
    }

    public List<Transition> getLesTransitions() {
        return lesTransitions;
    }

    public void setLesTransitions(List<Transition> lesTransitions) {
        this.lesTransitions = lesTransitions;
    }

    public Difficulty getDifficultyTodo() {
        return difficultyTodo;
    }

    public void setDifficultyTodo(Difficulty difficultyTodo) {
        this.difficultyTodo = difficultyTodo;
    }

    @Override
    public String toString() {
        return "Todo{" +
                ", descTodo='" + descTodo + '\'' +
                '}';
    }


    public Project getProjects() {
        return leProject;
    }

    public void setProjects(Project projects) {
        this.leProject = projects;
    }

    public int getNbTransitions(){
        return lesTransitions.size();
    }

}
