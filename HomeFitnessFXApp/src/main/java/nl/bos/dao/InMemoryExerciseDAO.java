package nl.bos.dao;

import javafx.scene.image.Image;
import nl.bos.models.Exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryExerciseDAO implements ExerciseDAO {
    private List<Exercise> exercises;
    private long currentId = 0;
    private static ExerciseDAO instance;

    private InMemoryExerciseDAO() {
        this.exercises = new ArrayList<>();
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
