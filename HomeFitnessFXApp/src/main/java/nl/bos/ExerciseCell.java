package nl.bos;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import nl.bos.controllers.MainPresenter;
import nl.bos.controllers.exercises.ExercisesPresenter;
import nl.bos.models.Exercise;

import java.util.logging.Logger;

public class ExerciseCell extends ListCell<Exercise> {
    private Text description;
    private Text exerciseData;
    private ImageView icon;
    private HBox exerciseContent;
    private Exercise exercise;

    public ExerciseCell(String presenterName) {
        super();
        exerciseData = new Text();
        description = new Text();
        icon = new ImageView();

        VBox iconBox = new VBox(icon);
        iconBox.setAlignment(Pos.CENTER);

        VBox textBox = new VBox(exerciseData, description);
        textBox.setAlignment(Pos.CENTER_LEFT);

        exerciseContent = new HBox(iconBox, textBox);
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
            } else {
                exerciseContent.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            }


            setGraphic(exerciseContent);
        }
    }
}
