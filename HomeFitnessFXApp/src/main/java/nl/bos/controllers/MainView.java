package nl.bos.controllers;

import com.gluonhq.charm.glisten.mvc.View;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainView {

    public View getView() {
        try {
            return FXMLLoader.load(MainView.class.getResource("/nl/bos/views/main.fxml"));
        } catch (IOException e) {
            Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, e);
            return new View();
        }
    }
}
