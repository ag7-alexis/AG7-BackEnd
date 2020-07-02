package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * Objet Utilisateur qui sera mémorisé en base de données.
 */

// Objet User renommé UserAcces car PostgreSQL n'autorise pas la création d'une entité User.
@Entity(name = "UserAcces")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long idUser;

    private String username;

    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Project> projects;

    private int active;

    private String roles = "";

    private String permissions = "";

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonIgnore
    private List<Transition> lesTransitions;


    @ManyToMany(mappedBy = "sharedUsers", fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    private Set<Project> sharedProjects;


    public User(String username, String password, String roles, String permissions, List<Project> projects) {
        this.username = username;
        this.password = password;
        this.projects = projects;
        this.roles = roles;
        this.permissions = permissions;
        this.active = 1;
    }

    public User(String username, String password, String roles, String permissions) {
        this.username = username;
        this.password = password;
        this.projects = new ArrayList<>();
        this.sharedProjects = new HashSet<>();
        this.roles = roles;
        this.permissions = permissions;
        this.active = 1;
    }

    public User() {
    }

    public User(long id, String username) {
        this.idUser = id;
        this.username = username;
    }

    public long getId() {
        return idUser;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public int getActive() {
        return active;
    }

    public List<Transition> getLesTransitions() {
        return lesTransitions;
    }

    public void setLesTransitions(List<Transition> lesTransitions) {
        this.lesTransitions = lesTransitions;
    }

    public Set<Project> getSharedProjects() {
        return sharedProjects;
    }

    public void setSharedProjects(Set<Project> sharedProjects) {
        this.sharedProjects = sharedProjects;
    }

    /**
     * Permet d'obtenir la liste des rôles car ils sont regroupés dans une seule chaine de caractère et séparés par des ','
     *
     * @return List<String>
     */

    public List<String> getRoleList() {
        if (this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    /**
     * Permet d'obtenir la liste des permissions car elles sont regroupées dans une seule chaine de caractère et séparées par des ','
     *
     * @return List<String>
     */

    public List<String> getPermissionList() {
        if (this.permissions.length() > 0) {
            return Arrays.asList(this.permissions.split(","));
        }
        return new ArrayList<>();
    }
}
