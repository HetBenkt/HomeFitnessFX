package nl.bos.dao;

import nl.bos.models.Exercise;

import java.util.List;

public interface ExerciseDAO {

    List<Exercise> getAllExercises();

    Exercise createExercise();

    Exercise getExercise(long id);

    boolean deleteExercise(long id);

    boolean updateExercise(long id);

    long getNextExerciseId();
}
