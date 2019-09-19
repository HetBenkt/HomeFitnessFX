package nl.bos;

import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import nl.bos.controllers.exercises.ExercisesPresenter;
import nl.bos.models.Exercise;

import java.util.logging.Logger;

public class ExerciseCell extends ListCell<Exercise> {
    private Text description;
    private Text name;
    private ImageView icon;
    private HBox exerciseContent;
    private Exercise exercise;

    public ExerciseCell() {
        super();
        name = new Text();
        description = new Text();
        icon = new ImageView();

        VBox iconBox = new VBox(icon);
        iconBox.setAlignment(Pos.CENTER);

        VBox textBox = new VBox(name, description);
        textBox.setAlignment(Pos.CENTER_LEFT);

        exerciseContent = new HBox(iconBox, textBox);
        exerciseContent.setSpacing(10);

        this.setOnMouseClicked(event -> {
            Logger.getLogger(ExerciseCell.class.getName()).info(exercise.getName());
            this.listViewProperty().get().getSelectionModel().select(exercise);
            ExercisesPresenter exercisesPresenter = (ExercisesPresenter) Controllers.get("ExercisesPresenter");
            exercisesPresenter.edit(exercise.getId());
        });
    }

    @Override
    protected void updateItem(Exercise exercise, boolean empty) {
        super.updateItem(exercise, empty);
        if (empty || exercise == null) {
            setGraphic(null);
        } else {
            this.exercise = exercise;
            name.setFont(Font.font ("Verdana", 14));
            name.setText(exercise.getName());

            description.setFont(Font.font ("Verdana", FontPosture.ITALIC, 10));
            description.setText(exercise.getDescription());

            icon.setImage(exercise.getIcon());
            icon.setFitHeight(icon.getImage().getHeight()/3);
            icon.setFitWidth(icon.getImage().getWidth()/3);

            setGraphic(exerciseContent);
        }
    }
}
