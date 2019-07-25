package nl.bos.controllers;

import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.FloatingActionButton;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.fxml.FXML;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminPresenter {

    @FXML
    private View admin;

    @FXML
    private void initialize() {
        admin.setShowTransitionFactory(BounceInRightTransition::new);
        
        FloatingActionButton fab = new FloatingActionButton(MaterialDesignIcon.INFO.text,
                e -> Logger.getLogger(MainView.class.getName()).log(Level.INFO, "Info", e));
        fab.showOn(admin);
        
        admin.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> 
                        MobileApplication.getInstance().getDrawer().open()));
                appBar.setTitleText("Administration");
                appBar.getActionItems().add(MaterialDesignIcon.FAVORITE.button(e ->
                        Logger.getLogger(MainView.class.getName()).log(Level.INFO, "Favorite", e)));
            }
        });
    }
}
