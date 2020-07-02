package com.example.demo;

import com.example.demo.api.viewmodel.ProjectViewModel;
import com.example.demo.api.viewmodel.UserViewModel;
import com.example.demo.api.viewmodel.todo.*;
import com.example.demo.db.*;
import com.example.demo.model.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Optional;

/**
 * Classe qui permet la conversion entre des objets et des vues simplifiées correspondantes à ces objets.
 */

@Component
public class Mapper {

    private ProjectRepository projectRepository;
    private UserRepository userRepository;
    private DifficultyRepository difficultyRepository;
    private TransitionRepository transitionRepository;
    private StateRepository stateRepository;

    public Mapper(ProjectRepository projectRepository, UserRepository userRepository,DifficultyRepository difficultyRepository, TransitionRepository transitionRepository, StateRepository stateRepository ) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.difficultyRepository = difficultyRepository;
        this.transitionRepository = transitionRepository;
        this.stateRepository =stateRepository;
    }

    /**
     * Converti la vue d'un todo en objet todo.
     *
     * @param todoViewModel todoView
     * @return Todo
     */

    public Todo convertToTodo(TodoViewModel todoViewModel) {
        Optional<Project> testProject = this.projectRepository.findById(Long.valueOf(todoViewModel.getProjectId()));
        if (!testProject.isPresent()) return null;
        Project project = testProject.get();

        Optional<Difficulty> testDifficulty = this.difficultyRepository.findByLibDifficulty(todoViewModel.getDifficulty());
        if (!testDifficulty.isPresent()) return null;
        Difficulty difficulty = testDifficulty.get();

        Todo leTodo = new Todo(todoViewModel.getId(), todoViewModel.getDesc(), difficulty, project);

        return leTodo;
    }


    /**
     * Converti la vue d'un todo en objet todo.
     * Variante de convertToTodo()
     *
     * @param todoViewModel todoView
     * @return Todo
     */

    public Todo convertToTodoBis(TodoViewModel todoViewModel) {
        Optional<Project> testProject = this.projectRepository.findById(Long.valueOf(todoViewModel.getProjectId()));
        if (!testProject.isPresent()) return null;
        Project project = testProject.get();

        Optional<Difficulty> testDifficulty = this.difficultyRepository.findByLibDifficulty(todoViewModel.getDifficulty());
        if (!testDifficulty.isPresent()) return null;
        Difficulty difficulty = testDifficulty.get();

        Todo leTodo = new Todo(todoViewModel.getDesc(), difficulty, project);

        return leTodo;
    }

    /**
     * Converti un objet todo en la vue d'un todo.
     *
     * @param todo Todo
     * @return TodoViewModel
     */

    public TodoViewModel convertToTodoView(Todo todo) {
        TodoViewModel todoViewModel = new TodoViewModel();
        todoViewModel.setId(String.valueOf(todo.getId()));
        todoViewModel.setDesc(todo.getDescTodo());
        todoViewModel.setDifficulty(String.valueOf(todo.getDifficultyTodo().getLibDifficulty()));
        todoViewModel.setProjectId(String.valueOf(todo.getProjects().getId()));

        Optional<State> testState = stateRepository.findById((long) 1); // State Todo
        if (!testState.isPresent()) return null;
        State state = testState.get();

        Transition transition = transitionRepository.getTransitionByStateAndTodo(state, todo);
        todoViewModel.setStateId(String.valueOf(transition.getState().getId()));
        todoViewModel.setUsername(String.valueOf(transition.getUser().getUsername()));

        return todoViewModel;
    }

    /**
     * Converti un objet todo en la vue d'un InProgress.
     *
     * @param todo Todo
     * @return InProgressViewModel
     */

    public InProgressViewModel convertToInProgressView(Todo todo) {
        InProgressViewModel inProgressViewModel = new InProgressViewModel();
        inProgressViewModel.setId(String.valueOf(todo.getId()));
        inProgressViewModel.setDesc(todo.getDescTodo());
        inProgressViewModel.setDifficulty(String.valueOf(todo.getDifficultyTodo().getLibDifficulty()));
        inProgressViewModel.setProjectId(String.valueOf(todo.getProjects().getId()));

        Optional<State> testState = stateRepository.findById((long) 2); // State InProgress
        if (!testState.isPresent()) return null;
        State state = testState.get();

        Transition transition = transitionRepository.getTransitionByStateAndTodo(state, todo);
        inProgressViewModel.setStateId(String.valueOf(transition.getState().getId()));
        inProgressViewModel.setDateDebut(transition.getDateDebut());
        inProgressViewModel.setUsername(String.valueOf(transition.getUser().getUsername()));

        return inProgressViewModel;
    }

    /**
     * Converti un objet todo en la vue d'un Test.
     *
     * @param todo Todo
     * @return TestViewModel
     */

    public TestViewModel convertToTestView(Todo todo) {
        TestViewModel testViewModel = new TestViewModel();
        testViewModel.setId(String.valueOf(todo.getId()));
        testViewModel.setDesc(todo.getDescTodo());
        testViewModel.setDifficulty(String.valueOf(todo.getDifficultyTodo().getLibDifficulty()));
        testViewModel.setProjectId(String.valueOf(todo.getProjects().getId()));

        Optional<State> testState = stateRepository.findById((long) 3); // State Test
        if (!testState.isPresent()) return null;
        State state = testState.get();

        Transition transition = transitionRepository.getTransitionByStateAndTodo(state, todo);
        testViewModel.setStateId(String.valueOf(transition.getState().getId()));
        testViewModel.setDateDebut(transition.getDateDebut());
        testViewModel.setUsername(String.valueOf(transition.getUser().getUsername()));

        return testViewModel;
    }

    /**
     * Converti un objet todo en la vue d'un Done.
     *
     * @param todo Todo
     * @return DoneViewModel
     */

    public DoneViewModel convertToDoneView(Todo todo) {
        DoneViewModel doneViewModel = new DoneViewModel();
        doneViewModel.setId(String.valueOf(todo.getId()));
        doneViewModel.setDesc(todo.getDescTodo());
        doneViewModel.setDifficulty(String.valueOf(todo.getDifficultyTodo().getLibDifficulty()));
        doneViewModel.setProjectId(String.valueOf(todo.getProjects().getId()));

        Optional<State> testState = stateRepository.findById((long) 4); // State Done
        if (!testState.isPresent()) return null;
        State state = testState.get();

        Transition transition = transitionRepository.getTransitionByStateAndTodo(state, todo);
        doneViewModel.setStateId(String.valueOf(transition.getState().getId()));
        doneViewModel.setDateDebut(transition.getDateDebut());
        doneViewModel.setUsername(String.valueOf(transition.getUser().getUsername()));

        return doneViewModel;
    }

    /**
     * Converti un objet todo en la vue d'un Valid.
     *
     * @param todo Todo
     * @return ValidViewModel
     */

    public ValidViewModel convertToValidView(Todo todo){
        ValidViewModel validViewModel = new ValidViewModel();
        validViewModel.setId(String.valueOf(todo.getId()));
        validViewModel.setDesc(todo.getDescTodo());
        validViewModel.setDifficulty(String.valueOf(todo.getDifficultyTodo().getLibDifficulty()));
        validViewModel.setProjectId(String.valueOf(todo.getProjects().getId()));

        Optional<State> testStateInPr = stateRepository.findById((long) 2);
        if (!testStateInPr.isPresent()) return null;
        State stateInPr = testStateInPr.get();
        Transition transitionInPr = transitionRepository.getTransitionByStateAndTodo(stateInPr, todo);
        validViewModel.setDateDebInPr(transitionInPr.getDateDebut());
        validViewModel.setDateFinInPr(transitionInPr.getDateFin());

        Optional<State> testStateTest = stateRepository.findById((long) 3);
        if (!testStateTest.isPresent()) return null;
        State stateTest = testStateTest.get();
        Transition transitionTest = transitionRepository.getTransitionByStateAndTodo(stateTest, todo);
        validViewModel.setDateDebTest(transitionTest.getDateDebut());
        validViewModel.setDateFinTest(transitionTest.getDateFin());

        Optional<State> testStateDone = stateRepository.findById((long) 4);
        if (!testStateDone.isPresent()) return null;
        State stateDone = testStateDone.get();
        Transition transitionDone = transitionRepository.getTransitionByStateAndTodo(stateDone, todo);
        validViewModel.setDateDebDone(transitionDone.getDateDebut());
        validViewModel.setDateFinDone(transitionDone.getDateFin());

        Optional<State> testState = stateRepository.findById((long) 5);
        if (!testState.isPresent()) return null;
        State state = testState.get();
        Transition transition = transitionRepository.getTransitionByStateAndTodo(state, todo);
        validViewModel.setStateId(String.valueOf(transition.getState().getId()));
        validViewModel.setUsername(String.valueOf(transition.getUser().getUsername()));

        return validViewModel;
    }

    /**
     *
     * Converti un User en une vue simplifiée.
     *
     * @param user User
     * @return UserViewModel
     */

    public UserViewModel convertToUserView(User user){
        UserViewModel userViewModel = new UserViewModel();
        userViewModel.setId(String.valueOf(user.getId()));
        userViewModel.setUsername(user.getUsername());

        return userViewModel;
    }



    /**
     * Converti la vue d'un project en un objet project.
     *
     * @param projectViewModel ProjectView
     * @return Project
     */

    public Project convertToProject(ProjectViewModel projectViewModel) {
        Optional<Project> testProject = this.projectRepository.findById(Long.valueOf(projectViewModel.getId()));
        if (testProject.isPresent()){
            Project project = testProject.get();
            project.setDescProject(projectViewModel.getDesc());
            project.setNameProject(projectViewModel.getTitre());
            return project;
        }
        return null;
    }

    /**
     * Converti la vue d'un project en un objet project.
     * Variante de convertToProject()
     *
     * @param projectViewModel ProjectView
     * @return Project
     */
    public Project convertToProjectBis(ProjectViewModel projectViewModel) {
        User user = this.userRepository.findByUsername(projectViewModel.getUsername());
        Project project = new Project(projectViewModel.getTitre(), projectViewModel.getDesc(), user);
        return project;
    }

    /**
     * Converti un objet project en la vue d'un project.
     *
     * @param project Project
     * @return ProjectViewModel
     */

    public ProjectViewModel convertToProjectView(Project project) {
        ProjectViewModel projectViewModel = new ProjectViewModel();
        projectViewModel.setId(String.valueOf(project.getId()));
        projectViewModel.setTitre(project.getNameProject());
        projectViewModel.setDesc(project.getDescProject());
        projectViewModel.setDateCrea(project.getDatecreaProject());
        projectViewModel.setUsername(project.getUser().getUsername());

        HashMap<String, Integer> details = project.getNbTasks();
        projectViewModel.setNbTodo(details.get("Todo"));
        projectViewModel.setNbInProgress(details.get("InProgress"));
        projectViewModel.setNbTest(details.get("Test"));
        projectViewModel.setNbDone(details.get("Done"));
        projectViewModel.setNbValid(details.get("Valid"));

        return projectViewModel;
    }


}
