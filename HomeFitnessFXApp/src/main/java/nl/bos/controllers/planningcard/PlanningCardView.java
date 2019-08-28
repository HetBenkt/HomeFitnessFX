package nl.bos.controllers.planningcard;

import com.gluonhq.charm.glisten.mvc.View;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlanningCardView {
    public View getView() {
        try {
            return FXMLLoader.load(PlanningCardView.class.getResource("/nl/bos/views/planningcard.fxml"));
        } catch (IOException e) {
            Logger.getLogger(PlanningCardView.class.getName()).log(Level.SEVERE, null, e);
            return new View();
        }
    }
}
