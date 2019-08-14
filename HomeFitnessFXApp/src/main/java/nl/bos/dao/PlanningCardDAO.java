package nl.bos.dao;

import nl.bos.models.Exercise;
import nl.bos.models.PlanningCard;

import java.time.LocalDate;
import java.util.List;

public interface PlanningCardDAO extends GenericDAO {

    List<PlanningCard> getAllPlanningCards();

    Exercise createPlanningCard(LocalDate date, Exercise exercise);

    Exercise getPlanningCard(long id);

    boolean deletePlanningCard(long id);

    boolean updatePlanningCard(long id);

    boolean copyPlanningCard(long id);
}
