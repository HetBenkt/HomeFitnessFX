package nl.bos.controllers.planningcards;

import com.gluonhq.charm.glisten.mvc.View;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlanningCardsView {
    
    public View getView() {
        try {
            return FXMLLoader.load(PlanningCardsView.class.getResource("/nl/bos/views/planningcards.fxml"));
        } catch (IOException e) {
            Logger.getLogger(PlanningCardsView.class.getName()).log(Level.SEVERE, null, e);
            return new View();
        }
    }
}
