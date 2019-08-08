package nl.bos;

import javafx.geometry.Pos;
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
            icon.setFitHeight(icon.getImage().getHeight()/3);
            icon.setFitWidth(icon.getImage().getWidth()/3);

            setGraphic(exerciseContent);
        }
    }
}
