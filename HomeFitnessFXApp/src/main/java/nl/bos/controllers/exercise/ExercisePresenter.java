package nl.bos.controllers.exercise;

import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.FloatingActionButton;
import com.gluonhq.charm.glisten.control.TextArea;
import com.gluonhq.charm.glisten.control.TextField;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import nl.bos.Controllers;
import nl.bos.DrawerManager;
import nl.bos.controllers.MainView;
import nl.bos.controllers.exercises.ExercisesPresenter;
import nl.bos.models.Exercise;
import nl.bos.services.ExerciseService;

import java.util.logging.Level;
import java.util.logging.Logger;

import static nl.bos.IConstants.VIEW_EXERCISES;

public class ExercisePresenter {
    private ExerciseService exerciseService;

    @FXML
    private TextField tfName;
    @FXML
    private TextArea taDescription;


    @FXML
    private View exercise;
    private long currentExerciseId = -1;

    public ExercisePresenter() {
        exerciseService = new ExerciseService();
    }

    private void save(ActionEvent actionEvent) {
        Logger.getLogger(ExercisePresenter.class.getName()).log(Level.INFO, "Save Exercise", actionEvent);
        if (currentExerciseId == -1) {
            exerciseService.createExercise(tfName.getText(), taDescription.getText(), new Image(DrawerManager.class.getResourceAsStream("/icon.png")));
            cleanFormFields();
        } else {
            exerciseService.updateExercise(currentExerciseId, tfName.getText(), taDescription.getText());
            cleanFormFields();
            currentExerciseId = -1;
        }
        ExercisesPresenter exercisesPresenter = (ExercisesPresenter) Controllers.get(ExercisesPresenter.class.getSimpleName());
        exercisesPresenter.updateExercises();
        MobileApplication.getInstance().switchView(VIEW_EXERCISES);
    }

    private void cleanFormFields() {
        tfName.setText("");
        taDescription.setText("");
    }

    @FXML
    private void initialize() {
        Controllers.put(this.getClass().getSimpleName(), this);
        exercise.setShowTransitionFactory(BounceInRightTransition::new);

        FloatingActionButton fab = new FloatingActionButton(MaterialDesignIcon.SAVE.text,
                this::save);
        fab.showOn(exercise);

        exercise.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
                        MobileApplication.getInstance().getDrawer().open()));
                appBar.setTitleText("Exercise");
                appBar.getActionItems().add(MaterialDesignIcon.SEARCH.button(e ->
                        Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, e)));
            }
        });
    }

    public void updateFields(Exercise exercise) {
        this.currentExerciseId = exercise.getId();
        tfName.setText(exercise.getName());
        taDescription.setText(exercise.getDescription());
    }
}
