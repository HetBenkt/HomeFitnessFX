package nl.bos.services;

import nl.bos.dao.ExerciseDAO;
import nl.bos.dao.InMemoryExerciseDAO;
import nl.bos.models.Exercise;

import java.util.List;

public class ExerciseService {
    private ExerciseDAO exerciseDAO;

    public ExerciseService() {
        this.exerciseDAO = InMemoryExerciseDAO.getInstance();
    }

    public List<Exercise> getAllExercises() {
        return exerciseDAO.getAllExercises();
    }
}
