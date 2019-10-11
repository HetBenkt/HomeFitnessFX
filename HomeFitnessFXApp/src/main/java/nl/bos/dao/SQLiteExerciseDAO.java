package nl.bos.dao;

import javafx.scene.image.Image;
import nl.bos.models.Exercise;

import java.util.List;

public class SQLiteExerciseDAO extends SQLiteDAO implements ExerciseDAO {
    private static SQLiteExerciseDAO instance;
    private long currentId = 0;

    private SQLiteExerciseDAO() {
        this.createDatabase();
    }

    public static SQLiteExerciseDAO getInstance() {
        if (instance == null)
            instance = new SQLiteExerciseDAO();
        return instance;
    }

    @Override
    public List<Exercise> getAllExercises() {
        return null;
    }

    @Override
    public Exercise createExercise(String name, String description, Image icon) {
        Exercise exercise = new Exercise();
        exercise.setId(getNextId());
        exercise.setName(name);
        exercise.setDescription(description);
        exercise.setIcon(icon);

        //exercises.add(exercise);
        return exercise;
    }

    @Override
    public Exercise getExercise(long id) {
        return null;
    }

    @Override
    public boolean deleteExercise(long id) {
        return false;
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
        return null;
    }

    @Override
    public long getNextId() {
        return ++currentId;
    }
}
