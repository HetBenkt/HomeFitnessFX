package nl.bos;

import com.gluonhq.charm.glisten.control.Dialog;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import nl.bos.controllers.MainPresenter;
import nl.bos.controllers.exercises.ExercisesPresenter;
import nl.bos.models.Exercise;
import nl.bos.services.ExerciseService;

import java.util.logging.Logger;

public class ExerciseCell extends ListCell<Exercise> {
    private Text description;
    private Text exerciseData;
    private ImageView icon;
    private HBox exerciseContent;
    private Exercise exercise;
    private final ExerciseService exerciseService;
    private Button copy;
    private Button delete;


    public ExerciseCell(String presenterName) {
        super();
        exerciseService = new ExerciseService();
        exerciseData = new Text();
        description = new Text();
        icon = new ImageView();

        copy = new Button();
        copy.setMaxSize(16, 16);
        copy.setGraphic(new ImageView(new Image(DrawerManager.class.getResourceAsStream("/copy.png"))));
        copy.setOnAction(event -> copyExercise());

        delete = new Button();
        delete.setMaxSize(16, 16);
        delete.setGraphic(new ImageView(new Image(DrawerManager.class.getResourceAsStream("/delete.png"))));
        delete.setOnAction(event -> showDialog());

        VBox iconBox = new VBox(icon);
        iconBox.setAlignment(Pos.CENTER);

        VBox textBox = new VBox(exerciseData, description);
        textBox.setAlignment(Pos.CENTER_LEFT);

        if (presenterName.equals("ExercisesPresenter")) {
            VBox actions = new VBox(delete, copy);
            actions.setSpacing(10);
            exerciseContent = new HBox(actions, iconBox, textBox);
        } else {
            exerciseContent = new HBox(iconBox, textBox);
        }

        exerciseContent.setSpacing(10);

        this.setOnMouseClicked(event -> {
            if (exercise != null) {
                Logger.getLogger(ExerciseCell.class.getName()).info(exercise.getName());
                this.listViewProperty().get().getSelectionModel().select(exercise);
                if (presenterName.equals("ExercisesPresenter")) {
                    ExercisesPresenter exercisesPresenter = (ExercisesPresenter) Controllers.get("ExercisesPresenter");
                    if (exercisesPresenter != null) {
                        exercisesPresenter.edit(exercise.getId());
                    }
                } else {
                    MainPresenter mainPresenter = (MainPresenter) Controllers.get("MainPresenter");
                    if (mainPresenter != null) {
                        mainPresenter.selectToggle(exercise.getId());
                    }
                }
            }
        });
    }

    private void showDialog() {
        Dialog dialog = new Dialog();
        dialog.setTitle(new Label("Are you sure?"));
        Button okButton = new Button("Ok");
        Button cancelButton = new Button("Cancel");
        okButton.setOnAction(e -> {
            deleteExercise();
            dialog.hide();
        });
        cancelButton.setOnAction(e -> dialog.hide());
        dialog.getButtons().addAll(okButton, cancelButton);
        dialog.showAndWait();
    }

    private void copyExercise() {
        Logger.getLogger(PlanningCardCell.class.getName()).info(String.valueOf(exercise));
        exerciseService.copyExercise(exercise);
        ExercisesPresenter exercisesPresenter = (ExercisesPresenter) Controllers.get("ExercisesPresenter");
        exercisesPresenter.updateExercises();
    }

    private void deleteExercise() {
        Logger.getLogger(PlanningCardCell.class.getName()).info(String.valueOf(exercise));
        exerciseService.deleteExercise(exercise);
        ExercisesPresenter exercisesPresenter = (ExercisesPresenter) Controllers.get("ExercisesPresenter");
        exercisesPresenter.updateExercises();
    }

    @Override
    protected void updateItem(Exercise exercise, boolean empty) {
        super.updateItem(exercise, empty);
        if (empty || exercise == null) {
            setGraphic(null);
        } else {
            this.exercise = exercise;
            exerciseData.setFont(Font.font("Verdana", 14));
            exerciseData.setText(String.format("%s [%s x %s %s]", exercise.getName(), exercise.getReps(), exercise.getSets(), exercise.getUnit()));

            description.setFont(Font.font ("Verdana", FontPosture.ITALIC, 10));
            description.setText(exercise.getDescription());

            icon.setImage(exercise.getIcon());
            icon.setFitHeight(icon.getImage().getHeight()/3);
            icon.setFitWidth(icon.getImage().getWidth()/3);

            if (exercise.isSelected()) {
                exerciseContent.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                exercise.setDone(true);
            } else {
                exerciseContent.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                exercise.setDone(false);
            }

            setGraphic(exerciseContent);
        }
    }
}
