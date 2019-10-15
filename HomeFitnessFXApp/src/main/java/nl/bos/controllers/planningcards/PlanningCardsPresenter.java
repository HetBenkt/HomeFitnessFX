package nl.bos.controllers.planningcards;

import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.FloatingActionButton;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import nl.bos.Controllers;
import nl.bos.PlanningCardCell;
import nl.bos.controllers.MainView;
import nl.bos.controllers.planningcard.PlanningCardPresenter;
import nl.bos.services.PlanningCardService;

import java.util.logging.Level;
import java.util.logging.Logger;

import static nl.bos.IConstants.EDIT_PLANNING_CARD;

public class PlanningCardsPresenter {

    private final PlanningCardService planningCardService;
    @FXML
    private View planningCards;
    @FXML
    private ListView lvPlanningCards;


    public PlanningCardsPresenter() {
        this.planningCardService = new PlanningCardService();
    }

    private static void create(ActionEvent e) {
        Logger.getLogger(MainView.class.getName()).log(Level.INFO, "Create new PlanningCard", e);
        MobileApplication.getInstance().switchView(EDIT_PLANNING_CARD);
    }

    @FXML
    private void initialize() {
        Controllers.put(this.getClass().getSimpleName(), this);
        lvPlanningCards.setCellFactory(param -> new PlanningCardCell());
        lvPlanningCards.getItems().addAll(planningCardService.getAllPlanningCards());

        planningCards.setShowTransitionFactory(BounceInRightTransition::new);

        FloatingActionButton fab = new FloatingActionButton(MaterialDesignIcon.CREATE.text,
                PlanningCardsPresenter::create);
        fab.showOn(planningCards);

        planningCards.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> 
                        MobileApplication.getInstance().getDrawer().open()));
                appBar.setTitleText("Planning Cards");
                appBar.getActionItems().add(MaterialDesignIcon.FAVORITE.button(e ->
                        Logger.getLogger(MainView.class.getName()).log(Level.INFO, "Favorite", e)));
            }
        });
    }

    public void edit(long planningCardId) {
        Logger.getLogger(PlanningCardsPresenter.class.getName()).log(Level.INFO, "Edit PlanningCard");
        MobileApplication.getInstance().switchView(EDIT_PLANNING_CARD);
        PlanningCardPresenter planningCardPresenter = (PlanningCardPresenter) Controllers.get("PlanningCardPresenter");
        planningCardPresenter.updateFields(planningCardService.getPlanningCard(planningCardId));
    }

    public void updatePlanningCards() {
        lvPlanningCards.getItems().clear();
        lvPlanningCards.getItems().setAll(planningCardService.getAllPlanningCards());
    }
}
