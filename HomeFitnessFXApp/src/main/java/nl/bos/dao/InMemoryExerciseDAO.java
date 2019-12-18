package nl.bos.dao;

import javafx.collections.FXCollections;
import javafx.scene.image.Image;
import nl.bos.DrawerManager;
import nl.bos.models.Exercise;

import java.util.List;
import java.util.stream.Collectors;

public class InMemoryExerciseDAO extends InMemoryDAO implements ExerciseDAO {
    private List<Exercise> exercises;
    private long currentId = 0;
    private static ExerciseDAO instance;

    private InMemoryExerciseDAO() {
        this.exercises = FXCollections.observableArrayList();

        createExercise("Plank", "Lie straight with both forearms \nsupporting body; hold position", new Image(DrawerManager.class.getResourceAsStream("/exercise1.png")));
        createExercise("Push-ups", "Lie prone with both arms stretched \nsupporting body; lower body with arms", new Image(DrawerManager.class.getResourceAsStream("/exercise2.png")));
        createExercise("Superman", "Lie on stomach with arms extended straigth; \nRaise both arms and legs up off the floor; \nhold for 2 sec. return and repeat", new Image(DrawerManager.class.getResourceAsStream("/exercise3.png")));
    }

    public static ExerciseDAO getInstance() {
        if (instance == null)
            instance = new InMemoryExerciseDAO();
        return instance;
    }

    @Override
    public List<Exercise> getAllExercises() {
        return this.exercises;
    }

    @Override
    public Exercise createExercise(String name, String description, Image icon) {
        Exercise exercise = new Exercise();
        exercise.setId(getNextId());
        exercise.setName(name);
        exercise.setDescription(description);
        exercise.setIcon(icon);

        exercises.add(exercise);
        return exercise;
    }

    @Override
    public Exercise getExercise(long id) {
        return exercises.stream()
                .filter(exercise -> exercise.getId() == id)
                .collect(Collectors.toList()).get(0);
    }

    @Override
    public void deleteExercise(Exercise exercise) {
        exercises.remove(exercise);
    }

    @Override
    public Exercise updateExercise(long id, String name, String description) {
        Exercise exercise = getExercise(id);
        exercise.setName(name);
        exercise.setDescription(description);
        return exercise;
    }

    @Override
    public List<Exercise> getAllUnusedExercises() {
        return exercises.stream()
                .filter(exercise -> !exercise.isUsedByPlanningCards())
                .collect(Collectors.toList());
    }

    @Override
    public void addExerciseData(long id, int reps, int sets, String unit) {
        Exercise exercise = getExercise(id);
        exercise.setReps(reps);
        exercise.setSets(sets);
        exercise.setUnit(unit);
    }

    @Override
    public Exercise copyExercise(Exercise exercise) {
        return createExercise(exercise.getName() + "- copy", exercise.getDescription(), exercise.getIcon());
    }

    @Override
    public long getNextId() {
        return ++currentId;
    }
}
