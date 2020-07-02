package com.example.demo.api;

import com.auth0.jwt.JWT;
import com.example.demo.Mapper;
import com.example.demo.api.error.ErrorMessage;
import com.example.demo.api.viewmodel.ProjectViewModel;
import com.example.demo.api.viewmodel.UserViewModel;
import com.example.demo.db.ProjectRepository;
import com.example.demo.db.UserRepository;
import com.example.demo.model.Project;
import com.example.demo.model.User;
import com.example.demo.security.JwtProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

/**
 * Controleur qui permet de réaliser des requêtes relatives à la gestion des projects.
 */

@RestController
@RequestMapping(value = "/api/project")
@CrossOrigin
public class ProjectController {
    ProjectRepository projectRepository;
    UserRepository userRepository;
    Mapper mapper;

    public ProjectController(ProjectRepository projectRepository, UserRepository userRepository, Mapper mapper) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    /**
     * Requête pour obtenir tous projects d'un utilisateur
     *
     * @param username User.username
     * @return List<ProjectViewModel>
     */

    @RequestMapping(value = "/allMyProject/{username}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllMyProject(@PathVariable String username, @RequestHeader(name = "Authorization") String token) {
        // Test Valid Stuff
        token = token.replace(JwtProperties.PREFIX, "");
        String testUsername = JWT.require(HMAC512(JwtProperties.SECRET.getBytes()))
                .build()
                .verify(token)
                .getSubject();
        User testUserFromToken = userRepository.findByUsername(testUsername);
        User user = userRepository.findByUsername(username);
        if (user == null) return new ResponseEntity<>(new ErrorMessage("username", "user not found"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);

        if (!testUserFromToken.equals(user)) return new ResponseEntity<>(new ErrorMessage("token", "invalid user"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);

        List<Project> projects = user.getProjects();
        List<ProjectViewModel> projectViewModels = projects.stream().map(project -> this.mapper.convertToProjectView(project)).collect(Collectors.toList());

        return ResponseEntity.ok(projectViewModels);
    }

    /**
     * Requête pour obtenir tous projects partagé d'un utilisateur
     *
     * @param username User.username
     * @return List<ProjectViewModel>
     */

    @RequestMapping(value = "/allMyProjectShared/{username}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllProjectSharedToUser(@PathVariable String username, @RequestHeader(name = "Authorization") String token) {
        // Test Valid Stuff
        token = token.replace(JwtProperties.PREFIX, "");
        String testUsername = JWT.require(HMAC512(JwtProperties.SECRET.getBytes()))
                .build()
                .verify(token)
                .getSubject();
        User testUserFromToken = userRepository.findByUsername(testUsername);
        User user = userRepository.findByUsername(username);
        if (user == null) return new ResponseEntity<>(new ErrorMessage("username", "user not found"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);

        if (!testUserFromToken.equals(user)) return new ResponseEntity<>(new ErrorMessage("token", "invalid user"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);

        List<Project> projects = new ArrayList<>(user.getSharedProjects());
        List<ProjectViewModel> projectViewModels = projects.stream().map(project -> this.mapper.convertToProjectView(project)).collect(Collectors.toList());

        return ResponseEntity.ok(projectViewModels);
    }

    /**
     * Requête pour obtenir tous projects partagé d'un utilisateur
     *
     * @param id idProject
     * @return List<ProjectViewModel>
     */

    @RequestMapping(value = "/allUserShared/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllUserSharedToProject(@PathVariable long id) {
        if (id < 0) return new ResponseEntity<>(new ErrorMessage("id", "wrong id"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        Optional<Project> testProject = projectRepository.findById(id);
        if (!testProject.isPresent()) return new ResponseEntity<>(new ErrorMessage("project", "project not found"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        Project project = testProject.get();

        List<User> users = new ArrayList<>(project.getSharedUsers());
        List<UserViewModel> finalUsers = users.stream().map(user -> this.mapper.convertToUserView(user)).collect(Collectors.toList());

        return ResponseEntity.ok(finalUsers);
    }

    /**
     * Requête pour obtenir un project
     *
     * @param id idProject
     * @return ProjectViewModel
     */

    @RequestMapping(value = "getProject/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getProject(@PathVariable long id) {
        if (id < 0) return new ResponseEntity<>(new ErrorMessage("id", "wrong id"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        Optional<Project> testProject = projectRepository.findById(id);

        if (!testProject.isPresent()) return new ResponseEntity<>(new ErrorMessage("project", "project not found"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        Project project = testProject.get();

        if (project == null) return new ResponseEntity<>(new ErrorMessage("project", "project not found"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);

        ProjectViewModel projectViewModel = this.mapper.convertToProjectView(project);

        return ResponseEntity.ok(projectViewModel);
    }

    /**
     * Requête pour obtenir la liste des utilisateurs ayant un username comprennant celui passez en parramètre.
     *
     * @param username User.username
     * @return List<UserViewModel>
     */

    @RequestMapping(value = "/getPotentialUsers/{username}", method = RequestMethod.GET)
    public ResponseEntity<?> getPotentialUsers(@PathVariable String username) {
        List<User> users = this.userRepository.findAllByUsernameContaining(username);

        List<UserViewModel> finalUsers = users.stream().map(user -> this.mapper.convertToUserView(user)).collect(Collectors.toList());

        return ResponseEntity.ok(finalUsers);
    }

    /**
     * Requete pour partager l'accès a un project a un utilisateur.
     *
     * @param username User.username
     * @param id       idProject
     * @return boolean
     */

    @RequestMapping(value = "/shareProject/{username}/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> shareProject(@PathVariable String username, @PathVariable long id) {
        if (id < 0) return new ResponseEntity<>(new ErrorMessage("id", "wrong id"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);

        Optional<Project> testProject = projectRepository.findById(id);
        if (!testProject.isPresent()) return new ResponseEntity<>(new ErrorMessage("project", "project not found"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        Project project = testProject.get();

        User user = userRepository.findByUsername(username);
        if (user == null) return new ResponseEntity<>(new ErrorMessage("username", "user not found"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);

        if (user.equals(project.getUser())) return new ResponseEntity<>(new ErrorMessage("user", "user shared = owner"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);

        int sizeBefore = project.getSharedUsers().size();

        project.getSharedUsers().add(user);
        projectRepository.save(project);

        int sizeAfter = project.getSharedUsers().size();

        if (sizeAfter == sizeBefore) return new ResponseEntity<>(new ErrorMessage("user", "user allready shared"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);

        UserViewModel userViewModel = this.mapper.convertToUserView(user);

        return ResponseEntity.ok(userViewModel);
    }

    /**
     * Requête pour modifier un project
     *
     * @param projectViewModel ProjectView
     * @param bindingResult bR
     * @return ProjectViewModel
     */

    @RequestMapping(value = "/saveProject", method = RequestMethod.POST)
    public ResponseEntity<?> updateProject(@RequestBody ProjectViewModel projectViewModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) throw new ValidationException();

        Project aProject = this.mapper.convertToProject(projectViewModel);

        projectRepository.save(aProject);

        ProjectViewModel leProject = this.mapper.convertToProjectView(aProject);

        return ResponseEntity.ok(leProject);
    }

    /**
     * Requête pour créer un nouveau project
     *
     * @param projectViewModel ProjectView
     * @param bindingResult bR
     * @return ProjectViewModel
     */

    @RequestMapping(value = "/newProject", method = RequestMethod.POST)
    public ResponseEntity<?> newProject(@RequestBody ProjectViewModel projectViewModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) throw new ValidationException();

        Project aProject = this.mapper.convertToProjectBis(projectViewModel);

        projectRepository.save(aProject);

        ProjectViewModel leProject = this.mapper.convertToProjectView(aProject);

        return ResponseEntity.ok(leProject);
    }

    /**
     * Supprimer l'accès d'un User partagé à un Project
     *
     * @param id idProject
     * @param username User.username
     * @return ok
     */

    @RequestMapping(value = "/deleteUserShared/{id}/{username}", method = RequestMethod.GET)
    public ResponseEntity<?> removeUserShared(@PathVariable long id, @PathVariable String username) {
        if (id < 0) return new ResponseEntity<>(new ErrorMessage("id", "wrong id"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);

        Optional<Project> testProject = projectRepository.findById(id);
        if (!testProject.isPresent()) return new ResponseEntity<>(new ErrorMessage("project", "project not found"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        Project project = testProject.get();

        User user = userRepository.findByUsername(username);
        if (user == null) return new ResponseEntity<>(new ErrorMessage("username", "user not found"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);

        project.getSharedUsers().remove(user);

        projectRepository.save(project);

        return ResponseEntity.ok(true);
    }


    /**
     * Requête pour supprimer un project
     *
     * @param id idProject
     */

    @RequestMapping(value = "/deleteProject/{id}", method = RequestMethod.GET)
    public void removeProject(@PathVariable long id) {
        projectRepository.deleteById(id);
    }
}
