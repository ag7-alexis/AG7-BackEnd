package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * Objet State qui sera mémorisé en base de données.
 */
@Entity
public class State {

    @Id
    private long idState;
    private String libState;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "state")
    @JsonIgnore
    private List<Transition> lesTransitions;

    public State() {
    }

    public State(long id, String libState) {
        this.idState = id;
        this.libState = libState;
    }

    public long getId() {
        return idState;
    }

    public void setId(long id) {
        this.idState = id;
    }

    public String getLibState() {
        return libState;
    }

    public void setLibState(String libState) {
        this.libState = libState;
    }


}
