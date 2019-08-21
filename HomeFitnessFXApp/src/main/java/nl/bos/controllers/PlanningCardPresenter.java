package nl.bos.controllers;

import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.*;
import com.gluonhq.charm.glisten.control.Dialog;
import com.gluonhq.charm.glisten.control.TextArea;
import com.gluonhq.charm.glisten.control.TextField;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import nl.bos.Controllers;
import nl.bos.models.Exercise;
import nl.bos.services.ExerciseService;
import nl.bos.services.PlanningCardService;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static nl.bos.IConstants.ADMIN_VIEW;

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

    public PlanningCardPresenter() {
        planningCardService = new PlanningCardService();
    }

    private void save(ActionEvent actionEvent) {
        Logger.getLogger(PlanningCardPresenter.class.getName()).log(Level.INFO, "Save PlanningCard", actionEvent);
        planningCardService.createPlanningCard(tfName.getText(), taDescription.getText(), dpDate.getValue(), new ArrayList<>(lvExercises.getItems()));
        AdminPresenter adminPresenter = (AdminPresenter) Controllers.get(AdminPresenter.class.getSimpleName());
        adminPresenter.updatePlanningCards();
        MobileApplication.getInstance().switchView(ADMIN_VIEW);
    }

    @FXML
    private void initialize() {
        planningCard.setShowTransitionFactory(BounceInRightTransition::new);

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
        exercises.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        exercises.getItems().clear();
        exercises.getItems().addAll(new ExerciseService().getAllExercises());
        dialog.setContent(exercises);
        Button okButton = new Button("Add");
        okButton.setOnAction(e -> {
            lvExercises.getItems().addAll(exercises.getSelectionModel().getSelectedItems());
            dialog.hide();
        });
        dialog.getButtons().add(okButton);
        dialog.showAndWait();
    }

    @FXML
    private void removeExercise(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            lvExercises.getItems().remove(lvExercises.getSelectionModel().getSelectedIndex());
        }
    }
}
