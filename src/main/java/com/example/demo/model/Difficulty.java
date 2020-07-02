package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Difficulty {

    @Id
    private long idDifficulty;
    private String libDifficulty;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "difficultyTodo")
    @JsonIgnore
    private List<Todo> lesTodo;

    public Difficulty() {
    }

    public Difficulty(String libDifficulty) {
        this.libDifficulty = libDifficulty;
        this.lesTodo = new ArrayList<>();
    }

    public Difficulty(long id, String libDifficulty) {
        this.idDifficulty = id;
        this.libDifficulty = libDifficulty;
        this.lesTodo = new ArrayList<>();
    }

    public long getId() {
        return idDifficulty;
    }

    public void setId(long id) {
        this.idDifficulty = id;
    }

    public String getLibDifficulty() {
        return libDifficulty;
    }

    public void setLibDifficulty(String libDifficulty) {
        this.libDifficulty = libDifficulty;
    }

    public List<Todo> getLesTodo() {
        return lesTodo;
    }

    public void setLesTodo(List<Todo> lesTodo) {
        this.lesTodo = lesTodo;
    }
}
