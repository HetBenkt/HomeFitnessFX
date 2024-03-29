package nl.bos.controllers.exercises;

import com.gluonhq.charm.glisten.mvc.View;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExercisesView {
    public View getView() {
        try {
            return FXMLLoader.load(ExercisesView.class.getResource("/nl/bos/views/exercises.fxml"));
        } catch (IOException e) {
            Logger.getLogger(ExercisesView.class.getName()).log(Level.SEVERE, null, e);
            return new View();
        }
    }
}
