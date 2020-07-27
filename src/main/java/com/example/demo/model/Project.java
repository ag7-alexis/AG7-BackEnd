package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;

/**
 * Objet Project qui sera mémorisé en base de données.
 */

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long idProject;

    private Date datecreaProject;

    private String nameProject;

    private String descProject;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "leProject", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Todo> lesTodo;


    @ManyToOne(optional = false)
    @JsonIgnore
    private User user;


    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    private Set<User> sharedUsers;


    public Project() {
        this.datecreaProject = new Date();
        this.lesTodo = new ArrayList<>();
    }

    public Project(String nameProject, String descProject, User user, List<Todo> lesTodo) {
        this.nameProject = nameProject;
        this.descProject = descProject;
        this.datecreaProject = new Date();
        this.user = user;
        this.lesTodo = lesTodo;
    }

    public Project(String nameProject, String descProject, User user) {
        this.nameProject = nameProject;
        this.descProject = descProject;
        this.user = user;
        this.datecreaProject = new Date();
        this.lesTodo = new ArrayList<>();
        this.sharedUsers = new HashSet<>();
    }

    public Project(String id, String nameProject, String descProject, User user) {
        this.idProject = Long.valueOf(id);
        this.nameProject = nameProject;
        this.descProject = descProject;
        this.user = user;
        this.datecreaProject = new Date();
        this.lesTodo = new ArrayList<>();
        //this.sharedUsers = new HashSet<>();
    }

    public Project(long id, String nameProject, String descProject, Date datecreaProject, User user) {
        this.idProject = id;
        this.nameProject = nameProject;
        this.descProject = descProject;
        this.user = user;
        this.datecreaProject = datecreaProject;
        this.lesTodo = new ArrayList<>();
    }

    public long getId() {
        return idProject;
    }

    public String getNameProject() {
        return nameProject;
    }

    public void setNameProject(String nameProject) {
        this.nameProject = nameProject;
    }

    public String getDescProject() {
        return descProject;
    }

    public void setDescProject(String descProject) {
        this.descProject = descProject;
    }

    public void setId(long id) {
        this.idProject = id;
    }

    public Date getDatecreaProject() {
        return datecreaProject;
    }

    public void setDatecreaProject(Date datecreaProject) {
        this.datecreaProject = datecreaProject;
    }

    public List<Todo> getLesTodo() {
        return lesTodo;
    }

    public void setLesTodo(ArrayList<Todo> lesTodo) {
        this.lesTodo = lesTodo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getIdProject() {
        return idProject;
    }

    public void setIdProject(long idProject) {
        this.idProject = idProject;
    }

    public void setLesTodo(List<Todo> lesTodo) {
        this.lesTodo = lesTodo;
    }

    public Set<User> getSharedUsers() {
        return sharedUsers;
    }

    public void setSharedUsers(Set<User> sharedUsers) {
        this.sharedUsers = sharedUsers;
    }

    public HashMap<String, Integer> getNbTasks() {
        HashMap<String, Integer> detailsNbTasks = new HashMap<>();
        int nbTodo = 0;
        int nbInProgress = 0;
        int nbTest = 0;
        int nbDone = 0;
        int nbValid = 0;

        for (Todo todo : lesTodo) {
            if (todo.getNbTransitions() == 1) nbTodo++;
            if (todo.getNbTransitions() == 2) nbInProgress++;
            if (todo.getNbTransitions() == 3) nbTest++;
            if (todo.getNbTransitions() == 4) nbDone++;
            if (todo.getNbTransitions() == 5) nbValid++;
        }

        detailsNbTasks.put("Todo", nbTodo);
        detailsNbTasks.put("InProgress", nbInProgress);
        detailsNbTasks.put("Test", nbTest);
        detailsNbTasks.put("Done", nbDone);
        detailsNbTasks.put("Valid", nbValid);
        return detailsNbTasks;
    }


    @Override
    public String toString() {
        return "Project{" +
                "id=" + idProject +
                ", datecreaProject=" + datecreaProject +
                ", nameProject='" + nameProject + '\'' +
                ", descProject='" + descProject + '\'' +
                '}';
    }


}
