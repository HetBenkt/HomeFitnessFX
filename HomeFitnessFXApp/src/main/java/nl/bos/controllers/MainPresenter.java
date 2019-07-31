package nl.bos.controllers;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import nl.bos.DrawerManager;
import nl.bos.models.Exercise;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MainPresenter {

    @FXML
    private View main;

    @FXML
    private ListView lvExercises;

    @FXML
    private void initialize() {
        ObservableList<Exercise> exercises = FXCollections.observableArrayList();
        exercises.add(new Exercise(0, "Definition of First Word", "test 1", new Image(DrawerManager.class.getResourceAsStream("/exercise1.png"))));
        exercises.add(new Exercise(1, "Definition of Second Word", "test 2", new Image(DrawerManager.class.getResourceAsStream("/exercise2.png"))));
        exercises.add(new Exercise(2, "Definition of Third Word", "test 3", new Image(DrawerManager.class.getResourceAsStream("/exercise3.png"))));

        lvExercises.setCellFactory(param -> new ExerciseCell());

        lvExercises.getItems().addAll(exercises);

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
