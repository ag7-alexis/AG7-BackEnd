package com.example.demo.db;

import com.example.demo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class DatabaseSeeder implements CommandLineRunner {

    private UserRepository userRepository;
    private ProjectRepository projectRepository;
    private TodoRepository todoRepository;
    private DifficultyRepository difficultyRepository;
    private StateRepository stateRepository;
    private TransitionRepository transitionRepository;
    private PasswordEncoder passwordEncoder;


    @Autowired
    public DatabaseSeeder(ProjectRepository projectRepository, TodoRepository todoRepository, UserRepository userRepository, StateRepository stateRepository, TransitionRepository transitionRepository, DifficultyRepository difficultyRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.todoRepository = todoRepository;
        this.difficultyRepository = difficultyRepository;
        this.stateRepository = stateRepository;
        this.transitionRepository = transitionRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void run(String... args) throws Exception {
       Difficulty dif1 = new Difficulty(1, "Easy");
       Difficulty dif2 = new Difficulty(2, "Medium");
       Difficulty dif3 = new Difficulty(3, "Hard");
       difficultyRepository.save(dif1);
       difficultyRepository.save(dif2);
       difficultyRepository.save(dif3);

       State st1 = new State(1, "To do");
       State st2 = new State(2, "In progress");
       State st3 = new State(3, "Test");
       State st4 = new State(4, "Done");
       State st5 = new State(5, "Valid");
       stateRepository.save(st1);
       stateRepository.save(st2);
       stateRepository.save(st3);
       stateRepository.save(st4);
       stateRepository.save(st5);


       User user = new User("admin", passwordEncoder.encode("admin"), "USER", "RESTRICTED");
       User user1 = new User("test1", passwordEncoder.encode("test1"), "USER", "RESTRICTED");
       User user2 = new User("test2", passwordEncoder.encode("test2"), "USER", "RESTRICTED");
       userRepository.save(user);
       userRepository.save(user1);
       userRepository.save(user2);
       Project project = new Project("testProject", "testProject", user);

       project.getSharedUsers().add(user1);

       projectRepository.save(project);

       Todo todo = new Todo("testTodo", dif1, project);
       todoRepository.save(todo);

       Transition transition = new Transition(todo, st1, user);
       transitionRepository.save(transition);

       Todo todo2 = new Todo("testTodo2", dif2, project);
       todoRepository.save(todo2);

       Transition transition2 = new Transition(todo2, st1, user);
       Transition transition3 = new Transition(todo2, st2, user);
       Transition transition4 = new Transition(todo2, st3, user);
       Transition transition5 = new Transition(todo2, st4, user);
       Transition transition6 = new Transition(todo2, st5, user);
       transitionRepository.save(transition2);
       transitionRepository.save(transition3);
       transitionRepository.save(transition4);
       transitionRepository.save(transition5);
       transitionRepository.save(transition6);

    }
}
