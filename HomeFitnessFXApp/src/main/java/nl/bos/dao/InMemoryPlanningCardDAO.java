package nl.bos.dao;

import javafx.collections.FXCollections;
import javafx.scene.image.Image;
import nl.bos.DrawerManager;
import nl.bos.models.Exercise;
import nl.bos.models.PlanningCard;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryPlanningCardDAO extends InMemoryDAO implements PlanningCardDAO {
    private List<PlanningCard> planningCards;
    private long currentId = 0;
    private static PlanningCardDAO instance;

    private InMemoryPlanningCardDAO() {
        this.planningCards = FXCollections.observableArrayList();
        ExerciseDAO exerciseDAO = InMemoryExerciseDAO.getInstance();
        Exercise exercise = exerciseDAO.createExercise("Plank", "Lie straight with both forearms \nsupporting body; hold position", new Image(DrawerManager.class.getResourceAsStream("/exercise1.png")));
        List<Exercise> exercises = new ArrayList<>();
        exercises.add(exercise);
        createPlanningCard("First day", "", LocalDate.now(), exercises);
    }

    public static PlanningCardDAO getInstance() {
        if (instance == null)
            instance = new InMemoryPlanningCardDAO();
        return instance;
    }

    @Override
    public List<PlanningCard> getAllPlanningCards() {
        return this.planningCards;
    }

    @Override
    public PlanningCard createPlanningCard(String name, String description, LocalDate date, List<Exercise> exercises) {
        PlanningCard planningCard = new PlanningCard();
        planningCard.setId(getNextId());
        planningCard.setDate(date);
        planningCard.setName(name);
        planningCard.setDescription(description);
        planningCard.addExercises(exercises);

        planningCards.add(planningCard);
        return planningCard;
    }

    @Override
    public PlanningCard getPlanningCard(long id) {
        return planningCards.stream()
                .filter(planningCard -> planningCard.getId() == id)
                .collect(Collectors.toList()).get(0);
    }

    @Override
    public boolean deletePlanningCard(long id) {
        return false;
    }

    @Override
    public PlanningCard updatePlanningCard(long id, String name, String description, LocalDate date, List<Exercise> exercises) {
        PlanningCard planningCard = getPlanningCard(id);
        planningCard.setName(name);
        planningCard.setDescription(description);
        planningCard.setDate(date);
        planningCard.addExercises(exercises);
        return planningCard;
    }

    @Override
    public boolean copyPlanningCard(long id) {
        return false;
    }

    @Override
    public PlanningCard getPlanningCardToday() {
        return planningCards.stream()
                .filter(planningCard -> planningCard.getDate().isEqual(LocalDate.now()))
                .collect(Collectors.toList()).get(0);
    }

    @Override
    public long getNextId() {
        return ++currentId;
    }
}
