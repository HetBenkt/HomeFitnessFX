package nl.bos;

import javafx.geometry.Pos;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import nl.bos.models.PlanningCard;

public class PlanningCardCell extends ListCell<PlanningCard> {
    private Text description;
    private Text name;
    private DatePicker date;
    private HBox exerciseContent;

    public PlanningCardCell() {
        super();
        name = new Text();
        description = new Text();
        date = new DatePicker();

        VBox textBox = new VBox(name, description);
        textBox.setAlignment(Pos.CENTER_LEFT);

        exerciseContent = new HBox(textBox);
        exerciseContent.setSpacing(10);
    }

    @Override
    protected void updateItem(PlanningCard item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setGraphic(null);
        } else {
            name.setFont(Font.font("Verdana", 14));
            name.setText(item.getName());

            description.setFont(Font.font("Verdana", FontPosture.ITALIC, 10));
            description.setText(item.getDescription());

            date.setValue(item.getDate());

            setGraphic(exerciseContent);
        }
    }
}
