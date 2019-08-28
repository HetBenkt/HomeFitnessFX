package nl.bos.controllers.exercise;

import com.gluonhq.charm.glisten.mvc.View;
import javafx.fxml.FXMLLoader;
import nl.bos.controllers.exercises.ExercisesView;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExerciseView {
    public View getView() {
        try {
            return FXMLLoader.load(ExercisesView.class.getResource("/nl/bos/views/exercise.fxml"));
        } catch (IOException e) {
            Logger.getLogger(ExerciseView.class.getName()).log(Level.SEVERE, null, e);
            return new View();
        }
    }
}
