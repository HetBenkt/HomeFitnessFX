package nl.bos.controllers;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import nl.bos.ExerciseCell;
import nl.bos.services.ExerciseService;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ExercisesPresenter {
    private final ExerciseService exerciseService;
    @FXML
    private View main;
    @FXML
    private ListView lvExercises;

    public ExercisesPresenter() {
        exerciseService = new ExerciseService();
    }

    @FXML
    private void initialize() {
        lvExercises.setCellFactory(param -> new ExerciseCell());
        lvExercises.getItems().addAll(exerciseService.getAllExercises());

        main.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
                        MobileApplication.getInstance().getDrawer().open()));
                appBar.setTitleText("Exercises");
                appBar.getActionItems().add(MaterialDesignIcon.SEARCH.button(e ->
                        Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, e)));
            }
        });
    }
}
