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
import nl.bos.Controllers;
import nl.bos.controllers.MainView;
import nl.bos.models.Exercise;
import nl.bos.services.ExerciseService;

import java.util.logging.Level;
import java.util.logging.Logger;

import static nl.bos.IConstants.VIEW_PLANNING_CARDS;

public class ExerciseDataPresenter {
    private ExerciseService exerciseService;

    @FXML
    private TextField tfId;
    @FXML
    private TextField tfPlanningcard;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfSets;
    @FXML
    private TextField tfUnit; //TODO should be a dropdown!
    @FXML
    private TextField tfReps;
    @FXML
    private TextArea taDescription;


    @FXML
    private View exercise;
    private long currentExerciseId = -1;

    public ExerciseDataPresenter() {
        exerciseService = new ExerciseService();
    }

    private void save(ActionEvent actionEvent) {
        Logger.getLogger(ExerciseDataPresenter.class.getName()).log(Level.INFO, "Save Exercise Data", actionEvent);

        exerciseService.updateExercise(currentExerciseId, tfName.getText(), taDescription.getText());
        exerciseService.addExerciseData(currentExerciseId, Integer.parseInt(tfReps.getText()), Integer.parseInt(tfSets.getText()), tfUnit.getText());
        cleanFormFields();
        currentExerciseId = -1;

        //PlanningCardsPresenter planningCardsPresenter = (PlanningCardsPresenter) Controllers.get(PlanningCardsPresenter.class.getSimpleName());
        //planningCardsPresenter.updateExercises();
        MobileApplication.getInstance().switchView(VIEW_PLANNING_CARDS);
    }

    private void cleanFormFields() {
        tfPlanningcard.setText("");
        tfId.setText("");
        tfName.setText("");
        taDescription.setText("");
        tfReps.setText("");
        tfSets.setText("");
        tfUnit.setText("");
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
                appBar.setTitleText("Exercise Data");
                appBar.getActionItems().add(MaterialDesignIcon.SEARCH.button(e ->
                        Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, e)));
            }
        });
    }

    public void updateFields(Exercise exercise) {
        this.currentExerciseId = exercise.getId();
        tfPlanningcard.setText(String.valueOf(exercise.getUsedByPlanningCards()));
        tfId.setText(String.valueOf(exercise.getId()));
        tfName.setText(exercise.getName());
        taDescription.setText(exercise.getDescription());
        tfReps.setText(String.valueOf(exercise.getReps()));
        tfSets.setText(String.valueOf(exercise.getSets()));
        tfUnit.setText(exercise.getUnit());
    }
}
