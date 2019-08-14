package nl.bos.controllers;

import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.FloatingActionButton;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import nl.bos.PlanningCardCell;
import nl.bos.services.PlanningCardService;

import java.util.logging.Level;
import java.util.logging.Logger;

import static nl.bos.IConstants.CREATE_PLANNING_CARD;

public class AdminPresenter {

    private final PlanningCardService planningCardService;
    @FXML
    private View admin;
    @FXML
    private ListView lvPlanningCards;


    public AdminPresenter() {
        this.planningCardService = new PlanningCardService();
    }

    private static void create(ActionEvent e) {
        Logger.getLogger(MainView.class.getName()).log(Level.INFO, "Create new PlanningCard", e);
        MobileApplication.getInstance().switchView(CREATE_PLANNING_CARD);
    }

    @FXML
    private void initialize() {
        lvPlanningCards.setCellFactory(param -> new PlanningCardCell());
        lvPlanningCards.getItems().addAll(planningCardService.getAllPlanningCards());

        admin.setShowTransitionFactory(BounceInRightTransition::new);

        FloatingActionButton fab = new FloatingActionButton(MaterialDesignIcon.CREATE.text,
                AdminPresenter::create);
        fab.showOn(admin);
        
        admin.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> 
                        MobileApplication.getInstance().getDrawer().open()));
                appBar.setTitleText("Administration");
                appBar.getActionItems().add(MaterialDesignIcon.FAVORITE.button(e ->
                        Logger.getLogger(MainView.class.getName()).log(Level.INFO, "Favorite", e)));
            }
        });
    }
}
