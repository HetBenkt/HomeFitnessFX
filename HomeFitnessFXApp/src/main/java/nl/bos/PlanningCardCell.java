package nl.bos;

import com.gluonhq.charm.glisten.control.Dialog;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import nl.bos.controllers.planningcards.PlanningCardsPresenter;
import nl.bos.models.PlanningCard;
import nl.bos.services.PlanningCardService;

import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class PlanningCardCell extends ListCell<PlanningCard> {
    private Text description;
    private Text name;
    private final PlanningCardService planningCardService;
    private HBox exerciseContent;
    private Text date;
    private PlanningCard planningCard;
    private Button copy;
    private Button delete;

    public PlanningCardCell() {
        super();
        planningCardService = new PlanningCardService();
        name = new Text();
        description = new Text();
        date = new Text();
        copy = new Button();
        copy.setMaxSize(16, 16);
        copy.setGraphic(new ImageView(new Image(DrawerManager.class.getResourceAsStream("/copy.png"))));
        copy.setOnAction(event -> copyPlanningCard());
        delete = new Button();
        delete.setMaxSize(16, 16);
        delete.setGraphic(new ImageView(new Image(DrawerManager.class.getResourceAsStream("/delete.png"))));
        delete.setOnAction(event -> showDialog());

        VBox actions = new VBox(delete, copy);
        actions.setSpacing(10);
        HBox header = new HBox(name, date);
        header.setSpacing(10);
        VBox information = new VBox(header, description);
        information.setSpacing(10);

        exerciseContent = new HBox(actions, information);
        exerciseContent.setSpacing(10);

        this.setOnMouseClicked(event -> {
            if (planningCard != null) {
                Logger.getLogger(ExerciseCell.class.getName()).info(planningCard.getName());
                this.listViewProperty().get().getSelectionModel().select(planningCard);
                PlanningCardsPresenter planningCardsPresenter = (PlanningCardsPresenter) Controllers.get("PlanningCardsPresenter");
                planningCardsPresenter.edit(planningCard.getId());
            }
        });

    }

    private void showDialog() {
        Dialog dialog = new Dialog();
        dialog.setTitle(new Label("Are you sure?"));
        Button okButton = new Button("Ok");
        Button cancelButton = new Button("Cancel");
        okButton.setOnAction(e -> {
            deletePlanningCard();
            dialog.hide();
        });
        cancelButton.setOnAction(e -> dialog.hide());
        dialog.getButtons().addAll(okButton, cancelButton);
        dialog.showAndWait();
    }

    private void copyPlanningCard() {
        Logger.getLogger(PlanningCardCell.class.getName()).info(String.valueOf(planningCard));
        planningCardService.copyPlanningCard(planningCard);
        PlanningCardsPresenter planningCardsPresenter = (PlanningCardsPresenter) Controllers.get("PlanningCardsPresenter");
        planningCardsPresenter.updatePlanningCards();
    }

    private void deletePlanningCard() {
        Logger.getLogger(PlanningCardCell.class.getName()).info(String.valueOf(planningCard));
        planningCardService.deletePlanningCard(planningCard);
        PlanningCardsPresenter planningCardsPresenter = (PlanningCardsPresenter) Controllers.get("PlanningCardsPresenter");
        planningCardsPresenter.updatePlanningCards();
    }

    @Override
    protected void updateItem(PlanningCard planningCard, boolean empty) {
        super.updateItem(planningCard, empty);
        if (empty || planningCard == null) {
            setGraphic(null);
        } else {
            this.planningCard = planningCard;
            name.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
            name.setText(planningCard.getName());

            description.setFont(Font.font("Verdana", FontPosture.ITALIC, 10));
            description.setText(planningCard.getDescription());

            date.setFont(Font.font("Verdana", 14));
            date.setText(planningCard.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE));

            setGraphic(exerciseContent);
        }
    }
}
