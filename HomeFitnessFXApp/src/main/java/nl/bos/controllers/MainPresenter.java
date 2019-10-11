package nl.bos.controllers;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import nl.bos.Controllers;
import nl.bos.ExerciseCell;
import nl.bos.models.Exercise;
import nl.bos.services.MainService;
import nl.bos.services.PlanningCardService;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MainPresenter {
    private final PlanningCardService planningCardService;
    private final MainService mainService;
    @FXML
    private View main;
    @FXML
    private ListView lvExercises;
    @FXML
    private TextArea txaStatus;

    public MainPresenter() {
        mainService = new MainService();
        this.planningCardService = new PlanningCardService();
    }

    @FXML
    private void initialize() {
        Controllers.put(this.getClass().getSimpleName(), this);
        lvExercises.setCellFactory(param -> new ExerciseCell(this.getClass().getSimpleName()));
        lvExercises.getItems().addAll(planningCardService.getPlanningCardToday().getExercises());

        txaStatus.appendText(mainService.checkDriver());
        txaStatus.appendText(System.lineSeparator());
        txaStatus.appendText(mainService.createDatabase());
        txaStatus.appendText(System.lineSeparator());

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

    public void selectToggle(long id) {
        Exercise exercise = planningCardService.getPlanningCardToday().getExercise(id);

        if (exercise.isSelected())
            exercise.setSelected(false);
        else
            exercise.setSelected(true);

        updateExercises();
    }
}
