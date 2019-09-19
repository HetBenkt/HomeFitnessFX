package nl.bos;

import javafx.geometry.Pos;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import nl.bos.controllers.planningcards.PlanningCardsPresenter;
import nl.bos.models.PlanningCard;

import java.util.logging.Logger;

public class PlanningCardCell extends ListCell<PlanningCard> {
    private Text description;
    private Text name;
    private DatePicker date;
    private HBox exerciseContent;
    private PlanningCard planningCard;

    public PlanningCardCell() {
        super();
        name = new Text();
        description = new Text();
        date = new DatePicker();

        VBox textBox = new VBox(name, description);
        textBox.setAlignment(Pos.CENTER_LEFT);

        exerciseContent = new HBox(textBox);
        exerciseContent.setSpacing(10);

        this.setOnMouseClicked(event -> {
            Logger.getLogger(ExerciseCell.class.getName()).info(planningCard.getName());
            this.listViewProperty().get().getSelectionModel().select(planningCard);
            PlanningCardsPresenter planningCardsPresenter = (PlanningCardsPresenter) Controllers.get("PlanningCardsPresenter");
            planningCardsPresenter.edit(planningCard.getId());
        });

    }

    @Override
    protected void updateItem(PlanningCard planningCard, boolean empty) {
        super.updateItem(planningCard, empty);
        if (empty || planningCard == null) {
            setGraphic(null);
        } else {
            this.planningCard = planningCard;
            name.setFont(Font.font("Verdana", 14));
            name.setText(planningCard.getName());

            description.setFont(Font.font("Verdana", FontPosture.ITALIC, 10));
            description.setText(planningCard.getDescription());

            date.setValue(planningCard.getDate());

            setGraphic(exerciseContent);
        }
    }
}
