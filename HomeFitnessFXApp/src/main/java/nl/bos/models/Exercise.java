package nl.bos.models;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class Exercise {
    private long id;
    private String name;
    private String description;
    private Image icon;
    private int reps;
    private int sets;
    private String unit;
    private boolean isSelected;
    private List<PlanningCard> usedByPlanningCards = new ArrayList<>();

    public void addToUsedByPlanningCards(PlanningCard planningCard) {
        usedByPlanningCards.add(planningCard);
    }

    public boolean isUsedByPlanningCards() {
        return usedByPlanningCards.size() > 0;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getIcon() {
        return icon;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public String toString() {
        return String.format("%s", name);
    }
}