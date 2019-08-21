package nl.bos.services;

import nl.bos.dao.InMemoryPlanningCardDAO;
import nl.bos.dao.PlanningCardDAO;
import nl.bos.models.Exercise;
import nl.bos.models.PlanningCard;

import java.time.LocalDate;
import java.util.List;

public class PlanningCardService {
    private PlanningCardDAO planningCardDAO;

    public PlanningCardService() {
        this.planningCardDAO = InMemoryPlanningCardDAO.getInstance();
    }

    public List<PlanningCard> getAllPlanningCards() {
        return planningCardDAO.getAllPlanningCards();
    }

    public PlanningCard createPlanningCard(String name, String description, LocalDate date, List<Exercise> exercises) {
        return planningCardDAO.createPlanningCard(name, description, date, exercises);
    }

}
