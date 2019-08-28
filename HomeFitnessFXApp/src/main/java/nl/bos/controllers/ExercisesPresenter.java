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
import nl.bos.ExerciseCell;
import nl.bos.services.ExerciseService;

import java.util.logging.Level;
import java.util.logging.Logger;

import static nl.bos.IConstants.CREATE_EXERCISE;

public class ExercisesPresenter {
    private final ExerciseService exerciseService;
    @FXML
    private View main;
    @FXML
    private ListView lvExercises;

    public ExercisesPresenter() {
        exerciseService = new ExerciseService();
    }

    private static void create(ActionEvent e) {
        Logger.getLogger(MainView.class.getName()).log(Level.INFO, "Create new Exercise", e);
        MobileApplication.getInstance().switchView(CREATE_EXERCISE);
    }

    @FXML
    private void initialize() {
        lvExercises.setCellFactory(param -> new ExerciseCell());
        lvExercises.getItems().addAll(exerciseService.getAllExercises());

        main.setShowTransitionFactory(BounceInRightTransition::new);

        FloatingActionButton fab = new FloatingActionButton(MaterialDesignIcon.CREATE.text,
                ExercisesPresenter::create);
        fab.showOn(main);

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
