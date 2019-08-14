package nl.bos.controllers;

import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.FloatingActionButton;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.util.logging.Level;
import java.util.logging.Logger;

import static nl.bos.IConstants.ADMIN_VIEW;

public class PlanningCardPresenter {
    @FXML
    private View planningcard;

    private static void save(ActionEvent actionEvent) {
        Logger.getLogger(PlanningCardPresenter.class.getName()).log(Level.INFO, "Save PlanningCard", actionEvent);
        MobileApplication.getInstance().switchView(ADMIN_VIEW);
    }

    @FXML
    private void initialize() {
        planningcard.setShowTransitionFactory(BounceInRightTransition::new);

        FloatingActionButton fab = new FloatingActionButton(MaterialDesignIcon.SAVE.text,
                PlanningCardPresenter::save);
        fab.showOn(planningcard);

        planningcard.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
                        MobileApplication.getInstance().getDrawer().open()));
                appBar.setTitleText("New Planning Card");
                appBar.getActionItems().add(MaterialDesignIcon.SEARCH.button(e ->
                        Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, e)));
            }
        });
    }
}
