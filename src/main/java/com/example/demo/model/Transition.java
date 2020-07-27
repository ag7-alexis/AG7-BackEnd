package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Transition {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long idTransition;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonIgnore
    private Todo todo;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonIgnore
    private State state;

    private Date dateDebut;
    private Date dateFin;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    public Transition() {
    }

    public Transition(long idTransition, Todo todo, State state, Date dateDebut, Date dateFin, User user) {
        this.idTransition = idTransition;
        this.todo = todo;
        this.state = state;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.user = user;
    }

    public Transition(Todo todo, State state, User user) {
        this.todo = todo;
        this.state = state;
        this.dateDebut = new Date();
        this.dateFin = null;
        this.user = user;
    }



    public long getIdTransition() {
        return idTransition;
    }

    public void setIdTransition(long idTransition) {
        this.idTransition = idTransition;
    }

    public Todo getTodo() {
        return todo;
    }

    public void setTodo(Todo todo) {
        this.todo = todo;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
