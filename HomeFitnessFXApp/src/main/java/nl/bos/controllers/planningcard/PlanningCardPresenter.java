package nl.bos.controllers.planningcard;

import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.*;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import nl.bos.Controllers;
import nl.bos.ExerciseSelectionCell;
import nl.bos.controllers.MainView;
import nl.bos.controllers.planningcards.PlanningCardsPresenter;
import nl.bos.models.Exercise;
import nl.bos.models.PlanningCard;
import nl.bos.services.ExerciseService;
import nl.bos.services.PlanningCardService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static nl.bos.IConstants.VIEW_PLANNING_CARDS;

public class PlanningCardPresenter {
    @FXML
    private View planningCard;
    @FXML
    private ListView<Exercise> lvExercises;
    @FXML
    private TextField tfName;
    @FXML
    private TextArea taDescription;
    @FXML
    private DatePicker dpDate;
    private PlanningCardService planningCardService;
    private long currentPlanningCardId = -1;

    public PlanningCardPresenter() {
        planningCardService = new PlanningCardService();
    }

    private void save(ActionEvent actionEvent) {
        Logger.getLogger(PlanningCardPresenter.class.getName()).log(Level.INFO, "Save PlanningCard", actionEvent);

        if (currentPlanningCardId == -1) {
            planningCardService.createPlanningCard(tfName.getText(), taDescription.getText(), dpDate.getValue(), new ArrayList<>(lvExercises.getItems()));
            cleanFormFields();
        } else {
            planningCardService.updateExercise(currentPlanningCardId, tfName.getText(), taDescription.getText(), dpDate.getValue(), new ArrayList<>(lvExercises.getItems()));
            cleanFormFields();
            currentPlanningCardId = -1;
        }

        PlanningCardsPresenter planningCardsPresenter = (PlanningCardsPresenter) Controllers.get(PlanningCardsPresenter.class.getSimpleName());
        planningCardsPresenter.updatePlanningCards();
        MobileApplication.getInstance().switchView(VIEW_PLANNING_CARDS);
    }

    private void cleanFormFields() {
        tfName.setText("");
        taDescription.setText("");
        dpDate.setValue(LocalDate.now());
        lvExercises.getItems().clear();
    }

    @FXML
    private void initialize() {
        Controllers.put(this.getClass().getSimpleName(), this);
        planningCard.setShowTransitionFactory(BounceInRightTransition::new);
        dpDate.setValue(LocalDate.now());
        lvExercises.setCellFactory(param -> new ExerciseSelectionCell(false));

        FloatingActionButton fab = new FloatingActionButton(MaterialDesignIcon.SAVE.text,
                this::save);
        fab.showOn(planningCard);

        planningCard.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
                        MobileApplication.getInstance().getDrawer().open()));
                appBar.setTitleText("New Planning Card");
                appBar.getActionItems().add(MaterialDesignIcon.SEARCH.button(e ->
                        Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, e)));
            }
        });
    }

    @FXML
    private void addExercise(ActionEvent actionEvent) {
        Dialog dialog = new Dialog();
        dialog.setTitle(new Label("Select your exercises"));
        ListView<Exercise> exercises = new ListView<>();
        exercises.setCellFactory(param -> new ExerciseSelectionCell(true));
        exercises.getItems().clear();
        exercises.getItems().addAll(new ExerciseService().getAllUnusedExercises());
        dialog.setContent(exercises);
        Button okButton = new Button("Add");
        okButton.setOnAction(e -> {
            lvExercises.getItems().addAll(exercises.getSelectionModel().getSelectedItems());
            dialog.hide();
        });
        dialog.getButtons().add(okButton);
        dialog.showAndWait();
    }

    public void updateFields(PlanningCard planningCard) {
        this.currentPlanningCardId = planningCard.getId();
        tfName.setText(planningCard.getName());
        taDescription.setText(planningCard.getDescription());
        dpDate.setValue(planningCard.getDate());
        lvExercises.getItems().addAll(planningCard.getExercises());
    }

    public void remove(Exercise exercise) {
        lvExercises.getItems().remove(exercise);
    }
}
