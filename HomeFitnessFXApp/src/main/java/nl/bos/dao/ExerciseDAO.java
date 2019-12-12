package nl.bos.dao;

import javafx.scene.image.Image;
import nl.bos.models.Exercise;

import java.util.List;

public interface ExerciseDAO extends GenericDAO {

    List<Exercise> getAllExercises();

    Exercise createExercise(String name, String description, Image icon);

    Exercise getExercise(long id);

    boolean deleteExercise(long id);

    Exercise updateExercise(long id, String name, String description);

    List<Exercise> getAllUnusedExercises();

    void addExerciseData(long id, int reps, int sets, String unit);

    Exercise copyExercise(Exercise exercise);
}
