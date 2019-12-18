package nl.bos.dao;

import nl.bos.models.Exercise;
import nl.bos.models.PlanningCard;

import java.time.LocalDate;
import java.util.List;

public interface PlanningCardDAO extends GenericDAO {

    List<PlanningCard> getAllPlanningCards();

    PlanningCard createPlanningCard(String name, String description, LocalDate date, List<Exercise> exercises);

    PlanningCard getPlanningCard(long id);

    void deletePlanningCard(PlanningCard planningCard);

    PlanningCard updatePlanningCard(long id, String name, String description, LocalDate date, List<Exercise> exercises);

    PlanningCard copyPlanningCard(PlanningCard planningCard);

    PlanningCard getPlanningCardToday();
}
