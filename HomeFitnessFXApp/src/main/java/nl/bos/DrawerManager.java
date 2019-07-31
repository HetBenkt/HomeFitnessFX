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
import static nl.bos.HomeFitnessFX.MAIN_VIEW;
import static nl.bos.HomeFitnessFX.ADMIN_VIEW;
import javafx.scene.image.Image;

public class DrawerManager {

    private DrawerManager() {
    }

    static void buildDrawer(MobileApplication app) {
        NavigationDrawer drawer = app.getDrawer();
        
        NavigationDrawer.Header header = new NavigationDrawer.Header("Home Fitness",
                "The App for working out @ home!",
                new Avatar(21, new Image(DrawerManager.class.getResourceAsStream("/icon.png"))));
        drawer.setHeader(header);
        
        final Item mainItem = new ViewItem("Exercises", MaterialDesignIcon.HOME.graphic(), MAIN_VIEW, ViewStackPolicy.SKIP);
        final Item adminItem = new ViewItem("Administration", MaterialDesignIcon.DASHBOARD.graphic(), ADMIN_VIEW);
        drawer.getItems().addAll(mainItem, adminItem);
        
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