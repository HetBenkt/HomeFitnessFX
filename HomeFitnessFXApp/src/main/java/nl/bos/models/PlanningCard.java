package nl.bos.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PlanningCard {
    private long id;
    private LocalDate date;
    private final List<Exercise> exercises = new ArrayList<>();
    private int reps;
    private int sets;
    private String unit;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean addExercise(Exercise exercise) {
        return exercises.add(exercise);
    }

    public boolean removeExercise(Exercise exercise) {
        return exercises.remove(exercise);
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
