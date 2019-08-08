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
        exercise.setId(getNextExerciseId());
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
