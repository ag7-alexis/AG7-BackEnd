package com.example.demo.api.viewmodel.todo;

import java.util.Date;

public class ValidViewModel {
    private String id;
    private String desc;
    private String difficulty;
    private String projectId;
    private String username;
    private Date dateDebInPr;
    private Date dateFinInPr;
    private Date dateDebTest;
    private Date dateFinTest;
    private Date dateDebDone;
    private Date dateFinDone;
    private String stateId;

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDateDebInPr() {
        return dateDebInPr;
    }

    public void setDateDebInPr(Date dateDebInPr) {
        this.dateDebInPr = dateDebInPr;
    }

    public Date getDateFinInPr() {
        return dateFinInPr;
    }

    public void setDateFinInPr(Date dateFinInPr) {
        this.dateFinInPr = dateFinInPr;
    }

    public Date getDateDebTest() {
        return dateDebTest;
    }

    public void setDateDebTest(Date dateDebTest) {
        this.dateDebTest = dateDebTest;
    }

    public Date getDateFinTest() {
        return dateFinTest;
    }

    public void setDateFinTest(Date dateFinTest) {
        this.dateFinTest = dateFinTest;
    }

    public Date getDateDebDone() {
        return dateDebDone;
    }

    public void setDateDebDone(Date dateDebDone) {
        this.dateDebDone = dateDebDone;
    }

    public Date getDateFinDone() {
        return dateFinDone;
    }

    public void setDateFinDone(Date dateFinDone) {
        this.dateFinDone = dateFinDone;
    }
}
