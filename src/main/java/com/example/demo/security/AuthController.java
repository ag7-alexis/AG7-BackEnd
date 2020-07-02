package com.example.demo.security;

import com.example.demo.api.error.ErrorMessage;
import com.example.demo.db.ProjectRepository;
import com.example.demo.db.UserRepository;
import com.example.demo.model.Project;
import com.example.demo.model.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;

/**
 * Controleur qui permet de réaliser des requetes relatives à la gestion des utilisateurs.
 */

@RestController
@RequestMapping(value = "/")
@CrossOrigin
public class AuthController {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    ProjectRepository projectRepository;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, ProjectRepository projectRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.projectRepository = projectRepository;
    }

    /**
     * Requête d'enregistrement d'un utilisateur en base de données
     *
     * @param loginViewModel LoginView
     * @param bindingResult bR
     */

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@RequestBody LoginViewModel loginViewModel, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) throw new ValidationException();

        if (userRepository.findByUsername(loginViewModel.getUsername()) != null) return new ResponseEntity<>(new ErrorMessage("username", "username not available"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);

        User newUser = new User(loginViewModel.getUsername(), passwordEncoder.encode(loginViewModel.getPassword()), "USER", "RESTRICTED");
        userRepository.save(newUser);
        Project project = new Project("Votre premier projet !", "Ajouter lui une description.", newUser);
        projectRepository.save(project);

        return ResponseEntity.ok(true);
    }

}
