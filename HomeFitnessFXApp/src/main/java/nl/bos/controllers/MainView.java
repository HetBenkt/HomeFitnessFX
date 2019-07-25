package nl.bos.controllers;

import com.gluonhq.charm.glisten.mvc.View;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXMLLoader;

public class MainView {

    public View getView() {
        try {
            return FXMLLoader.load(MainView.class.getResource("main.fxml"));
        } catch (IOException e) {
            Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, e);
            return new View();
        }
    }
}
