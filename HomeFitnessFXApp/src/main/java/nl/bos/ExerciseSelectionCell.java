package nl.bos;

import com.gluonhq.charm.glisten.application.MobileApplication;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import nl.bos.controllers.exercise.ExerciseDataPresenter;
import nl.bos.controllers.planningcard.PlanningCardPresenter;
import nl.bos.models.Exercise;

import java.util.logging.Level;
import java.util.logging.Logger;

import static nl.bos.IConstants.ADD_EXERCISE_DATA;

public class ExerciseSelectionCell extends ListCell<Exercise> {
    private Text name;
    private Exercise exercise;
    private HBox exerciseContent;

    public ExerciseSelectionCell(boolean isDialog) {
        super();
        name = new Text();

        if (isDialog)
            exerciseContent = new HBox(name);
        else {
            Button btnRemove = new Button();
            btnRemove.setMaxSize(16, 16);
            btnRemove.setGraphic(new ImageView(new Image(DrawerManager.class.getResourceAsStream("/remove.png"))));
            btnRemove.setOnAction(event -> removeExerciseFromPlanningCard());
            Button btnEdit = new Button();
            btnEdit.setMaxSize(16, 16);
            btnEdit.setGraphic(new ImageView(new Image(DrawerManager.class.getResourceAsStream("/edit.png"))));
            btnEdit.setOnAction(event -> editExerciseForPlanningCard());
            exerciseContent = new HBox(btnRemove, btnEdit, name); //TODO not only the name, but also the reps/sets/unit
        }
        exerciseContent.setSpacing(10);

        this.setOnMouseClicked(event -> {
            if (exercise != null) {
                if (isDialog) {
                    Logger.getLogger(ExerciseCell.class.getName()).info(exercise.getName());
                    this.listViewProperty().get().getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                    this.listViewProperty().get().getSelectionModel().select(exercise);
                }
            }
        });
    }

    private void editExerciseForPlanningCard() {
        this.listViewProperty().get().getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.listViewProperty().get().getSelectionModel().select(exercise);

        Logger.getLogger(ExerciseSelectionCell.class.getName()).log(Level.INFO, "Add Reps/Sets/Units");
        MobileApplication.getInstance().switchView(ADD_EXERCISE_DATA);
        ExerciseDataPresenter exerciseDataPresenter = (ExerciseDataPresenter) Controllers.get("ExerciseDataPresenter");
        exerciseDataPresenter.updateFields(exercise);
    }

    private void removeExerciseFromPlanningCard() {
        Logger.getLogger(ExerciseSelectionCell.class.getName()).info("Remove from list " + exercise.getName());
        PlanningCardPresenter planningCardPresenter = (PlanningCardPresenter) Controllers.get("PlanningCardPresenter");
        planningCardPresenter.remove(exercise);
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
