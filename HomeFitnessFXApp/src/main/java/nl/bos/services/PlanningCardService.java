package nl.bos.services;

import nl.bos.dao.InMemoryPlanningCardDAO;
import nl.bos.dao.PlanningCardDAO;
import nl.bos.models.PlanningCard;

import java.util.List;

public class PlanningCardService {
    private PlanningCardDAO planningCardDAO;

    public PlanningCardService() {
        this.planningCardDAO = InMemoryPlanningCardDAO.getInstance();
    }

    public List<PlanningCard> getAllPlanningCards() {
        return planningCardDAO.getAllPlanningCards();
    }
}
