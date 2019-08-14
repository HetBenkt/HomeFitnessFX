package nl.bos.dao;

import javafx.scene.image.Image;
import nl.bos.models.Exercise;

import java.util.List;

public interface ExerciseDAO extends GenericDAO {

    List<Exercise> getAllExercises();

    Exercise createExercise(String name, String description, Image icon);

    Exercise getExercise(long id);

    boolean deleteExercise(long id);

    boolean updateExercise(long id);
}
