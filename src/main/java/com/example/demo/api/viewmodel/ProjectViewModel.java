package com.example.demo.api.viewmodel;

import java.util.Date;

/**
 * Classe permettant l'affichage simplifié d'un project.
 */

public class ProjectViewModel {
    private String id;
    private String titre;
    private String desc;
    private Date dateCrea;
    private String username;
    private int nbTodo;
    private int nbInProgress;
    private int nbTest;
    private int nbDone;
    private int nbValid;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getDateCrea() {
        return dateCrea;
    }

    public void setDateCrea(Date dateCrea) {
        this.dateCrea = dateCrea;
    }

    public int getNbTodo() {
        return nbTodo;
    }

    public void setNbTodo(int nbTodo) {
        this.nbTodo = nbTodo;
    }

    public int getNbInProgress() {
        return nbInProgress;
    }

    public void setNbInProgress(int nbInProgress) {
        this.nbInProgress = nbInProgress;
    }

    public int getNbTest() {
        return nbTest;
    }

    public void setNbTest(int nbTest) {
        this.nbTest = nbTest;
    }

    public int getNbDone() {
        return nbDone;
    }

    public void setNbDone(int nbDone) {
        this.nbDone = nbDone;
    }

    public int getNbValid() {
        return nbValid;
    }

    public void setNbValid(int nbValid) {
        this.nbValid = nbValid;
    }
}
