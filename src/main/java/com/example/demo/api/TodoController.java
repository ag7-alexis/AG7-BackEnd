package com.example.demo.api;

import com.example.demo.Mapper;
import com.example.demo.api.error.ErrorMessage;
import com.example.demo.api.viewmodel.todo.*;
import com.example.demo.db.*;
import com.example.demo.model.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controleur qui permet de réaliser des requetes relatives à la gestion des todos.
 */

@RestController
@RequestMapping(value = "/api/todo")
@CrossOrigin
public class TodoController {
    TodoRepository todoRepository;
    Mapper mapper;
    ProjectRepository projectRepository;
    TransitionRepository transitionRepository;
    DifficultyRepository difficultyRepository;
    StateRepository stateRepository;
    UserRepository userRepository;

    public TodoController(TodoRepository todoRepository, Mapper mapper, ProjectRepository projectRepository, TransitionRepository transitionRepository, DifficultyRepository difficultyRepository, StateRepository stateRepository, UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.mapper = mapper;
        this.projectRepository = projectRepository;
        this.difficultyRepository = difficultyRepository;
        this.stateRepository = stateRepository;
        this.transitionRepository = transitionRepository;
        this.userRepository = userRepository;
    }

    /**
     * Requête pour obtenir tous les todos
     *
     * @return List<TodoViewModel>
     */

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<TodoViewModel> getAllTodo() {
        List<Todo> todos = todoRepository.findAll();

        List<TodoViewModel> lesTodos = todos.stream().map(todo -> this.mapper.convertToTodoView(todo)).collect(Collectors.toList());

        return lesTodos;
    }

    /**
     * Requête pour obtenir tous les todos pour un project
     *
     * @param id idTodo
     * @return List<TodoViewModel>
     */

    @RequestMapping(value = "/allTodo/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllTodoFromProject(@PathVariable Long id) {
        if (id < 0) return new ResponseEntity<>(new ErrorMessage("id", "wrong id"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        ArrayList<Todo> finalTodos = new ArrayList<>();
        Optional<Project> testProject = projectRepository.findById(id);
        if (!testProject.isPresent()) return new ResponseEntity<>(new ErrorMessage("username", "user not found"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        Project project = testProject.get();
        List<Todo> todos = project.getLesTodo();
        for (Todo todo : todos) {
            List<Transition> transitions = todo.getLesTransitions();
            if (transitions.size() == 1) { //les todos avec 1 transitions sont à l'etat Todo
                finalTodos.add(todo);
            }
        }

        List<TodoViewModel> lesTodos = finalTodos.stream().map(todo -> this.mapper.convertToTodoView(todo)).collect(Collectors.toList());

        return ResponseEntity.ok(lesTodos);
    }

    /**
     * Requête pour obtenir tous les InProgress pour un project
     *
     * @param id idTodo
     * @return List<InProgressViewModel>
     */

    @RequestMapping(value = "/allInProgress/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllInProgressFromProject(@PathVariable Long id) {
        if (id < 0) return new ResponseEntity<>(new ErrorMessage("id", "wrong id"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        ArrayList<Todo> finalInProgress = new ArrayList<>();
        Optional<Project> testProject = projectRepository.findById(id);
        if (!testProject.isPresent()) return new ResponseEntity<>(new ErrorMessage("project", "project not found"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        Project project = testProject.get();
        List<Todo> todos = project.getLesTodo();
        for (Todo todo : todos) {
            if (todo.getLesTransitions().size() == 2) { //les todos avec 2 transitions sont à l'etat InProgress
                finalInProgress.add(todo);
            }
        }

        List<InProgressViewModel> lesInProgress = finalInProgress.stream().map(todo -> this.mapper.convertToInProgressView(todo)).collect(Collectors.toList());

        return ResponseEntity.ok(lesInProgress);
    }

    /**
     * Requête pour obtenir tous les Tests pour un project
     *
     * @param id idTodo
     * @return List<TestViewModel>
     */

    @RequestMapping(value = "/allTest/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllTestFromProject(@PathVariable Long id) {
        if (id < 0) return new ResponseEntity<>(new ErrorMessage("id", "wrong id"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        ArrayList<Todo> finalTests = new ArrayList<>();
        Optional<Project> testProject = projectRepository.findById(id);
        if (!testProject.isPresent()) return new ResponseEntity<>(new ErrorMessage("project", "project not found"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        Project project = testProject.get();
        List<Todo> todos = project.getLesTodo();
        for (Todo todo : todos) {
            if (todo.getLesTransitions().size() == 3) { //les todos avec 3 transitions sont à l'etat Test
                finalTests.add(todo);
            }
        }

        List<TestViewModel> lesTests = finalTests.stream().map(todo -> this.mapper.convertToTestView(todo)).collect(Collectors.toList());

        return ResponseEntity.ok(lesTests);
    }

    /**
     * Requête pour obtenir tous les Dones pour un project
     *
     * @param id idTodo
     * @return List<DoneViewModel>
     */

    @RequestMapping(value = "/allDone/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllDoneFromProject(@PathVariable Long id) {
        if (id < 0) return new ResponseEntity<>(new ErrorMessage("id", "wrong id"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        ArrayList<Todo> finalDones = new ArrayList<>();
        Optional<Project> testProject = projectRepository.findById(id);
        if (!testProject.isPresent()) return new ResponseEntity<>(new ErrorMessage("project", "project not found"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        Project project = testProject.get();
        List<Todo> todos = project.getLesTodo();
        for (Todo todo : todos) {
            if (todo.getLesTransitions().size() == 4) { //les todos avec 4 transitions sont à l'etat Done
                finalDones.add(todo);
            }
        }

        List<DoneViewModel> lesDones = finalDones.stream().map(todo -> this.mapper.convertToDoneView(todo)).collect(Collectors.toList());

        return ResponseEntity.ok(lesDones);
    }

    /**
     * Requête pour obtenir tous les Valids pour un project
     *
     * @param id idTodo
     * @return List<ValidViewModel>
     */

    @RequestMapping(value = "/allValid/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllValidFromProject(@PathVariable Long id) {
        if (id < 0) return new ResponseEntity<>(new ErrorMessage("id", "wrong id"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        ArrayList<Todo> finalValids = new ArrayList<>();
        Optional<Project> testProject = projectRepository.findById(id);
        if (!testProject.isPresent()) return new ResponseEntity<>(new ErrorMessage("project", "project not found"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        Project project = testProject.get();
        List<Todo> todos = project.getLesTodo();
        for (Todo todo : todos) {
            if (todo.getLesTransitions().size() == 5) { //les todos avec 5 transitions sont à l'etat Valid
                finalValids.add(todo);
            }
        }

        List<ValidViewModel> lesValids = finalValids.stream().map(todo -> this.mapper.convertToValidView(todo)).collect(Collectors.toList());

        return ResponseEntity.ok(lesValids);
    }

    /**
     * Requête pour obtenir un todo
     *
     * @param id idTodo
     * @return TodoViewModel
     */

    @RequestMapping(value = "/getTodo/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getTodo(@PathVariable long id) {
        if (id < 0) return new ResponseEntity<>(new ErrorMessage("id", "wrong id"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        Optional<Todo> testTodo = this.todoRepository.findById(id);
        if (!testTodo.isPresent()) return new ResponseEntity<>(new ErrorMessage("todo", "todo not found"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        Todo todo = testTodo.get();

        TodoViewModel todoViewModel = this.mapper.convertToTodoView(todo);

        return ResponseEntity.ok(todoViewModel);
    }

    /**
     * Requête pour modifier un todo
     *
     * @param todoViewModel todoView
     * @param bindingResult bR
     * @return TodoViewModel
     */

    @RequestMapping(value = "/saveTodo", method = RequestMethod.POST)
    public ResponseEntity<?> updateTodo(@RequestBody TodoViewModel todoViewModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) throw new ValidationException();

        Todo aTodo = this.mapper.convertToTodo(todoViewModel);

        todoRepository.save(aTodo);

        TodoViewModel leTodo = this.mapper.convertToTodoView(aTodo);

        return ResponseEntity.ok(leTodo);
    }

    /**
     * Requête pour créer un nouveau todo
     *
     * @param todoViewModel todoView
     * @param bindingResult bR
     * @return TodoViewModel
     */

    @RequestMapping(value = "/newTodo/{username}", method = RequestMethod.POST)
    public ResponseEntity<?> newProject(@RequestBody TodoViewModel todoViewModel, BindingResult bindingResult, @PathVariable String username) {
        if (bindingResult.hasErrors()) throw new ValidationException();

        Todo aTodo = this.mapper.convertToTodoBis(todoViewModel);

        User user = this.userRepository.findByUsername(username);
        if (user == null) return new ResponseEntity<>(new ErrorMessage("username", "user not found"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);


        Optional<State> testState = this.stateRepository.findById((long) 1); //State Todo
        if (!testState.isPresent()) return new ResponseEntity<>(new ErrorMessage("state", "state not found"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        State state = testState.get();

        Transition transition = new Transition(aTodo, state, user);

        todoRepository.save(aTodo);
        transitionRepository.save(transition);

        TodoViewModel leTodo = this.mapper.convertToTodoView(aTodo);

        return ResponseEntity.ok(leTodo);
    }

    /**
     * Fait passer un Todo à l'etat Todo vers l'etat InProgress
     *
     * @param id idTodo
     * @param username User.username
     * @return InProgressViewModel
     */

    @RequestMapping(value = "/upgradeTodoToInProgress/{username}/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> upgradeTodoToInProgress(@PathVariable String username, @PathVariable long id) {
        if (id < 0) return new ResponseEntity<>(new ErrorMessage("id", "wrong id"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);

        Optional<Todo> testTodo = this.todoRepository.findById(id);
        if (!testTodo.isPresent()) return new ResponseEntity<>(new ErrorMessage("todo", "todo not found"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        Todo todo = testTodo.get();

        User user = this.userRepository.findByUsername(username);
        if (user == null) return new ResponseEntity<>(new ErrorMessage("username", "user not found"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);

        Optional<State> testState = this.stateRepository.findById((long) 1); //State Todo
        if (!testState.isPresent()) return new ResponseEntity<>(new ErrorMessage("state", "state not found"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        State state = testState.get();

        // ajout de la date de fin
        Transition transition = this.transitionRepository.getTransitionByStateAndTodo(state, todo);
        transition.setDateFin(new Date());
        this.transitionRepository.save(transition);

        // création de la nouvelle transition
        Optional<State> testNewState = this.stateRepository.findById((long) 2); //State InProgress
        if (!testNewState.isPresent()) return new ResponseEntity<>(new ErrorMessage("state", "state not found"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        State newState = testNewState.get();
        Transition newTransition = new Transition(todo, newState, user);
        this.transitionRepository.save(newTransition);

        InProgressViewModel inProgressViewModel = this.mapper.convertToInProgressView(todo);

        return ResponseEntity.ok(inProgressViewModel);
    }

    /**
     * Fait passer un Todo à l'etat InProgress vers l'etat Test
     *
     * @param id idTodo
     * @param username User.username
     * @return TestViewModel
     */

    @RequestMapping(value = "/upgradeInProgressToTest/{username}/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> upgradeInProgressToTest(@PathVariable String username, @PathVariable long id) {
        if (id < 0) return new ResponseEntity<>(new ErrorMessage("id", "wrong id"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);

        Optional<Todo> testTodo = this.todoRepository.findById(id);
        if (!testTodo.isPresent()) return new ResponseEntity<>(new ErrorMessage("todo", "todo not found"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        Todo todo = testTodo.get();

        User user = this.userRepository.findByUsername(username);
        if (user == null) return new ResponseEntity<>(new ErrorMessage("username", "user not found"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);

        Optional<State> testState = this.stateRepository.findById((long) 2); //State InProgress
        if (!testState.isPresent()) return new ResponseEntity<>(new ErrorMessage("state", "state not found"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        State state = testState.get();

        // ajout de la date de fin
        Transition transition = this.transitionRepository.getTransitionByStateAndTodo(state, todo);
        transition.setDateFin(new Date());
        this.transitionRepository.save(transition);

        // création de la nouvelle transition
        Optional<State> testNewState = this.stateRepository.findById((long) 3); //State Test
        if (!testNewState.isPresent()) return new ResponseEntity<>(new ErrorMessage("state", "state not found"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        State newState = testNewState.get();
        Transition newTransition = new Transition(todo, newState, user);
        this.transitionRepository.save(newTransition);

        TestViewModel testViewModel = this.mapper.convertToTestView(todo);

        return ResponseEntity.ok(testViewModel);
    }

    /**
     * Fait passer un Todo à l'etat Test vers l'etat Done
     *
     * @param id idTodo
     * @param username User.username
     * @return DoneViewModel
     */

    @RequestMapping(value = "/upgradeTestToDone/{username}/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> upgradeTestToDone(@PathVariable String username, @PathVariable long id) {
        if (id < 0) return new ResponseEntity<>(new ErrorMessage("id", "wrong id"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);

        Optional<Todo> testTodo = this.todoRepository.findById(id);
        if (!testTodo.isPresent()) return new ResponseEntity<>(new ErrorMessage("todo", "todo not found"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        Todo todo = testTodo.get();

        User user = this.userRepository.findByUsername(username);
        if (user == null) return new ResponseEntity<>(new ErrorMessage("username", "user not found"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);

        Optional<State> testState = this.stateRepository.findById((long) 3); //State Test
        if (!testState.isPresent()) return new ResponseEntity<>(new ErrorMessage("state", "state not found"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        State state = testState.get();

        // ajout de la date de fin
        Transition transition = this.transitionRepository.getTransitionByStateAndTodo(state, todo);
        transition.setDateFin(new Date());
        this.transitionRepository.save(transition);

        // création de la nouvelle transition
        Optional<State> testNewState = this.stateRepository.findById((long) 4); //State Done
        if (!testNewState.isPresent()) return new ResponseEntity<>(new ErrorMessage("state", "state not found"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        State newState = testNewState.get();
        Transition newTransition = new Transition(todo, newState, user);
        this.transitionRepository.save(newTransition);

        DoneViewModel doneViewModel = this.mapper.convertToDoneView(todo);

        return ResponseEntity.ok(doneViewModel);
    }

    /**
     * Fait passer un Todo à l'etat Done vers l'etat Valid
     *
     * @param id idTodo
     * @param username User.username
     * @return ValidViewModel
     */

    @RequestMapping(value = "/upgradeDoneToValid/{username}/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> upgradeDoneToValid(@PathVariable String username, @PathVariable long id) {
        if (id < 0) return new ResponseEntity<>(new ErrorMessage("id", "wrong id"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);

        Optional<Todo> testTodo = this.todoRepository.findById(id);
        if (!testTodo.isPresent()) return new ResponseEntity<>(new ErrorMessage("todo", "todo not found"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        Todo todo = testTodo.get();

        User user = this.userRepository.findByUsername(username);
        if (user == null) return new ResponseEntity<>(new ErrorMessage("username", "user not found"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);

        Optional<State> testState = this.stateRepository.findById((long) 4); //State Done
        if (!testState.isPresent()) return new ResponseEntity<>(new ErrorMessage("state", "state not found"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        State state = testState.get();

        // ajout de la date de fin
        Transition transition = this.transitionRepository.getTransitionByStateAndTodo(state, todo);
        transition.setDateFin(new Date());
        this.transitionRepository.save(transition);

        // création de la nouvelle transition
        Optional<State> testNewState = this.stateRepository.findById((long) 5); //State Valid
        if (!testNewState.isPresent()) return new ResponseEntity<>(new ErrorMessage("state", "state not found"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        State newState = testNewState.get();
        Transition newTransition = new Transition(todo, newState, user);
        this.transitionRepository.save(newTransition);

        ValidViewModel validViewModel = this.mapper.convertToValidView(todo);

        return ResponseEntity.ok(validViewModel);
    }

    /**
     * Requête pour supprimer un todo
     *
     * @param id idTodo
     */

    @RequestMapping(value = "/deleteTodo/{id}", method = RequestMethod.GET)
    public void removeTodo(@PathVariable long id) {
        todoRepository.deleteById(id);
    }
}
