package nl.bos.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import nl.bos.DrawerManager;
import nl.bos.models.Exercise;

import java.util.List;
import java.util.stream.Collectors;

public class InMemoryExerciseDAO implements ExerciseDAO {
    private ObservableList<Exercise> exercises;
    private long currentId = 0;
    private static ExerciseDAO instance;

    private InMemoryExerciseDAO() {
        this.exercises = FXCollections.observableArrayList();
        exercises.add(new Exercise(0, "Definition of First Exercise", "test 2", new Image(DrawerManager.class.getResourceAsStream("/exercise1.png"))));
        exercises.add(new Exercise(1, "Definition of Second Exercise", "test 3", new Image(DrawerManager.class.getResourceAsStream("/exercise2.png"))));
        exercises.add(new Exercise(2, "Definition of Third Exercise", "test 4", new Image(DrawerManager.class.getResourceAsStream("/exercise3.png"))));
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
        Exercise exercise = new Exercise(getNextExerciseId(), name, description, icon);
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
    public boolean deleteExercise(long id) {
        return false;
    }

    @Override
    public boolean updateExercise(long id) {
        return false;
    }

    @Override
    public long getNextExerciseId() {
        return ++currentId;
    }
}
