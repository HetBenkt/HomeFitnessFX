package nl.bos.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.bos.models.Exercise;
import nl.bos.models.PlanningCard;

import java.time.LocalDate;
import java.util.List;

public class InMemoryPlanningCardDAO implements PlanningCardDAO {
    private ObservableList<PlanningCard> planningCards;
    private long currentId = 0;
    private static PlanningCardDAO instance;

    private InMemoryPlanningCardDAO() {
        this.planningCards = FXCollections.observableArrayList();
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
        return null;
    }

    @Override
    public boolean deletePlanningCard(long id) {
        return false;
    }

    @Override
    public boolean updatePlanningCard(long id) {
        return false;
    }

    @Override
    public boolean copyPlanningCard(long id) {
        return false;
    }

    @Override
    public long getNextId() {
        return ++currentId;
    }
}
