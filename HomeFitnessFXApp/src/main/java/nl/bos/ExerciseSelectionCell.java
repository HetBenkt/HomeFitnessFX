package nl.bos;

import javafx.scene.control.ListCell;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import nl.bos.controllers.planningcard.PlanningCardPresenter;
import nl.bos.models.Exercise;

import java.util.logging.Logger;

public class ExerciseSelectionCell extends ListCell<Exercise> {
    private Text name;
    private Exercise exercise;
    private HBox exerciseContent;

    public ExerciseSelectionCell(boolean isDialog) {
        super();
        name = new Text();

        exerciseContent = new HBox(name);
        exerciseContent.setSpacing(10);

        this.setOnMouseClicked(event -> {
            if (exercise != null) {
                if (event.getClickCount() == 2 && !isDialog) {
                    Logger.getLogger(ExerciseSelectionCell.class.getName()).info("Remove from list " + exercise.getName());
                    PlanningCardPresenter planningCardPresenter = (PlanningCardPresenter) Controllers.get("PlanningCardPresenter");
                    planningCardPresenter.remove(exercise);
                } else {
                    Logger.getLogger(ExerciseCell.class.getName()).info(exercise.getName());
                    this.listViewProperty().get().getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                    this.listViewProperty().get().getSelectionModel().select(exercise);
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
            name.setFont(Font.font("Verdana", 14));
            name.setText(exercise.getName());

            setGraphic(exerciseContent);
        }
    }
}
