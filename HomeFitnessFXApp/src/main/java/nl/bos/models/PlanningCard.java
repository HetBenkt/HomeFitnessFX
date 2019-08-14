package nl.bos.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PlanningCard {
    private long id;
    private LocalDate date;
    private String name;
    private String description;
    private final List<Exercise> exercises = new ArrayList<>();

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
