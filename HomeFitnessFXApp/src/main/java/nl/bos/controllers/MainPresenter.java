package nl.bos.controllers;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import nl.bos.Controllers;
import nl.bos.ExerciseCell;
import nl.bos.services.PlanningCardService;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MainPresenter {
    private final PlanningCardService planningCardService;
    @FXML
    private View main;
    @FXML
    private ListView lvExercises;

    public MainPresenter() {
        this.planningCardService = new PlanningCardService();
    }

    @FXML
    private void initialize() {
        Controllers.put(this.getClass().getSimpleName(), this);
        lvExercises.setCellFactory(param -> new ExerciseCell());
        lvExercises.getItems().addAll(planningCardService.getPlanningCardToday().getExercises());

        main.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
                        MobileApplication.getInstance().getDrawer().open()));
                appBar.setTitleText("Home");
                appBar.getActionItems().add(MaterialDesignIcon.SEARCH.button(e ->
                        Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, e)));
            }
        });
    }

    public void updateExercises() {
        lvExercises.getItems().clear();
        lvExercises.getItems().setAll(planningCardService.getPlanningCardToday().getExercises());
    }
}
