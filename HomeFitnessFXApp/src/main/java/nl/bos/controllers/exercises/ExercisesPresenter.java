package nl.bos.controllers.exercises;

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
import nl.bos.ExerciseCell;
import nl.bos.controllers.MainView;
import nl.bos.controllers.exercise.ExercisePresenter;
import nl.bos.models.Exercise;
import nl.bos.services.ExerciseService;

import java.util.logging.Level;
import java.util.logging.Logger;

import static nl.bos.IConstants.EDIT_EXERCISE;

public class ExercisesPresenter {
    private final ExerciseService exerciseService;
    @FXML
    private View exercises;
    @FXML
    private ListView lvExercises;

    public ExercisesPresenter() {
        exerciseService = new ExerciseService();
    }

    private static void create(ActionEvent e) {
        Logger.getLogger(ExercisesPresenter.class.getName()).log(Level.INFO, "Create new Exercise", e);
        MobileApplication.getInstance().switchView(EDIT_EXERCISE);
    }

    @FXML
    private void initialize() {
        Controllers.put(this.getClass().getSimpleName(), this);
        lvExercises.setCellFactory(param -> new ExerciseCell());
        lvExercises.getItems().addAll(exerciseService.getAllExercises());
        lvExercises.getSelectionModel().selectedItemProperty().addListener((observable, oldExercise, newExercise) -> {
            if (newExercise != null) {
                Logger.getLogger(ExercisesPresenter.class.getName()).info(((Exercise) newExercise).getName());
                edit(((Exercise) newExercise).getId());
            }
        });

        exercises.setShowTransitionFactory(BounceInRightTransition::new);

        FloatingActionButton fab = new FloatingActionButton(MaterialDesignIcon.CREATE.text,
                ExercisesPresenter::create);
        fab.showOn(exercises);

        exercises.showingProperty().addListener((obs, oldValue, newValue) -> {
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

    private void edit(long exerciseId) {
        Logger.getLogger(ExercisesPresenter.class.getName()).log(Level.INFO, "Edit Exercise");
        MobileApplication.getInstance().switchView(EDIT_EXERCISE);
        ExercisePresenter exercisePresenter = (ExercisePresenter) Controllers.get("ExercisePresenter");
        exercisePresenter.updateFields(exerciseService.getExercise(exerciseId));
    }

    public void updateExercises() {
        lvExercises.getItems().clear();
        lvExercises.getItems().setAll(exerciseService.getAllExercises());
    }
}
