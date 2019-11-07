package nl.bos.controllers;

import com.gluonhq.charm.down.Services;
import com.gluonhq.charm.down.plugins.LifecycleService;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import nl.bos.Controllers;
import nl.bos.ExerciseCell;
import nl.bos.models.Exercise;
import nl.bos.models.PlanningCard;
import nl.bos.services.MainService;
import nl.bos.services.PlanningCardService;

import java.time.format.DateTimeFormatter;
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
    @FXML
    private Button btnStartTimer;
    @FXML
    private Button btnStopTimer;
    @FXML
    private Label lblDate;
    @FXML
    private Label lblDay;
    @FXML
    private Label lblSlogan;
    @FXML
    private Label lblDone;
    @FXML
    private Label lblTimer;

    public MainPresenter() {
        mainService = new MainService();
        this.planningCardService = new PlanningCardService();
    }

    @FXML
    private void initialize() {
        Controllers.put(this.getClass().getSimpleName(), this);
        lvExercises.setCellFactory(param -> new ExerciseCell(this.getClass().getSimpleName()));
        PlanningCard planningCardToday = planningCardService.getPlanningCardToday();

        lvExercises.getItems().addAll(planningCardToday.getExercises());
        lblDate.setText(planningCardToday.getDate().format(DateTimeFormatter.ISO_DATE));
        lblDay.setText(planningCardToday.getName());
        lblSlogan.setText(planningCardToday.getDescription());
        lblDone.setText(String.format("0 / %s DONE", planningCardToday.getExercises().size()));

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
        PlanningCard planningCardToday = planningCardService.getPlanningCardToday();

        lvExercises.getItems().setAll(planningCardToday.getExercises());
        lblDate.setText(planningCardToday.getDate().format(DateTimeFormatter.ISO_DATE));
        lblDay.setText(planningCardToday.getName());
        lblSlogan.setText(planningCardToday.getDescription());
        lblDone.setText(String.format("0 / %s DONE", planningCardToday.getExercises().size()));
    }

    public void selectToggle(long id) {
        Exercise exercise = planningCardService.getPlanningCardToday().getExercise(id);

        if (exercise.isSelected())
            exercise.setSelected(false);
        else
            exercise.setSelected(true);

        updateExercises();
    }

    public void startTimer(ActionEvent actionEvent) {
        btnStartTimer.setVisible(false);
        btnStopTimer.setVisible(true);
        lblTimer.setText("45:00");
    }

    public void stopTimer(ActionEvent actionEvent) {
        btnStartTimer.setVisible(true);
        btnStopTimer.setVisible(false);
    }

    public void quit(ActionEvent actionEvent) {
        Services.get(LifecycleService.class).ifPresent(LifecycleService::shutdown);
    }
}
