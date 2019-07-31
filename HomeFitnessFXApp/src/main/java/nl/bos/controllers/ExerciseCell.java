package nl.bos.controllers;

import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import nl.bos.models.Exercise;

public class ExerciseCell extends ListCell<Exercise> {
    private Text description;
    private Text name;
    private ImageView icon;
    private HBox exerciseContent;

    ExerciseCell() {
        super();
        name = new Text();
        description = new Text();
        icon = new ImageView();

        exerciseContent = new HBox(icon, new VBox(name, description));
        exerciseContent.setSpacing(10);
    }

    @Override
    protected void updateItem(Exercise item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setGraphic(null);
        } else {
            name.setFont(Font.font ("Verdana", 14));
            name.setText(item.getName());

            description.setFont(Font.font ("Verdana", FontPosture.ITALIC, 10));
            description.setText(item.getDescription());

            icon.setImage(item.getIcon());
            icon.setFitHeight(icon.getImage().getHeight()/5);
            icon.setFitWidth(icon.getImage().getWidth()/5);

            setGraphic(exerciseContent);
        }
    }
}
