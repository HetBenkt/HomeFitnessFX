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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import nl.bos.Controllers;
import nl.bos.EConstants;
import nl.bos.controllers.MainView;
import nl.bos.models.Exercise;
import nl.bos.services.ExerciseService;

import java.util.logging.Level;
import java.util.logging.Logger;

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
    private RadioButton rbTimes;
    @FXML
    private RadioButton rbSec;
    @FXML
    private RadioButton rbMin;
    @FXML
    private ToggleGroup rbUnit;
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
        RadioButton selectedUnit = (RadioButton) rbUnit.getSelectedToggle();
        exerciseService.addExerciseData(currentExerciseId, Integer.parseInt(tfReps.getText()), Integer.parseInt(tfSets.getText()), selectedUnit.getText());
        cleanFormFields();
        currentExerciseId = -1;

        MobileApplication.getInstance().switchView(EConstants.VIEW_PLANNING_CARDS.name());
    }

    private void cleanFormFields() {
        tfPlanningcard.setText("");
        tfId.setText("");
        tfName.setText("");
        taDescription.setText("");
        tfReps.setText("");
        tfSets.setText("");
        rbUnit.selectToggle(null);
        rbTimes.setSelected(true);
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

        rbUnit.selectToggle(null);
        rbTimes.setSelected(exercise.getUnit().equals("Times"));
        rbSec.setSelected(exercise.getUnit().equals("Sec."));
        rbMin.setSelected(exercise.getUnit().equals("Min."));
    }
}
