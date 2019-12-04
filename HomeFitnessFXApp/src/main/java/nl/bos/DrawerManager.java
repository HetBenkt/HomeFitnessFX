package nl.bos;

import com.gluonhq.charm.down.Platform;
import com.gluonhq.charm.down.Services;
import com.gluonhq.charm.down.plugins.LifecycleService;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.application.ViewStackPolicy;
import com.gluonhq.charm.glisten.control.Avatar;
import com.gluonhq.charm.glisten.control.NavigationDrawer;
import com.gluonhq.charm.glisten.control.NavigationDrawer.Item;
import com.gluonhq.charm.glisten.control.NavigationDrawer.ViewItem;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.scene.image.Image;

import static com.gluonhq.charm.glisten.application.MobileApplication.HOME_VIEW;

public class DrawerManager {

    private DrawerManager() {
    }

    static void buildDrawer(MobileApplication app) {
        NavigationDrawer drawer = app.getDrawer();
        
        NavigationDrawer.Header header = new NavigationDrawer.Header("Home Fitness",
                "The App for working out @ home!",
                new Avatar(21, new Image(DrawerManager.class.getResourceAsStream("/icon.png"))));
        drawer.setHeader(header);

        final Item mainItem = new ViewItem("Home", MaterialDesignIcon.HOME.graphic(), HOME_VIEW, ViewStackPolicy.SKIP);
        final Item exercisesItem = new ViewItem("Exercises", MaterialDesignIcon.DASHBOARD.graphic(), EConstants.VIEW_EXERCISES.name());
        final Item adminItem = new ViewItem("PlanningCards", MaterialDesignIcon.DASHBOARD.graphic(), EConstants.VIEW_PLANNING_CARDS.name());
        drawer.getItems().addAll(mainItem, exercisesItem, adminItem);
        
        if (Platform.isDesktop()) {
            final Item quitItem = new Item("Quit", MaterialDesignIcon.EXIT_TO_APP.graphic());
            quitItem.selectedProperty().addListener((obs, ov, nv) -> {
                if (nv) {
                    Services.get(LifecycleService.class).ifPresent(LifecycleService::shutdown);
                }
            });
            drawer.getItems().add(quitItem);
        }
    }
}