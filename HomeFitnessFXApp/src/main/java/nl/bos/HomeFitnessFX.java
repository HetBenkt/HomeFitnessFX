package nl.bos;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.license.License;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.Swatch;
import javafx.scene.Scene;
import nl.bos.controllers.AdminView;
import nl.bos.controllers.MainView;


@License(key = "7e71ebf2-bb31-44b7-806e-2cb0e3a7f4ba")
public class HomeFitnessFX extends MobileApplication {

    static final String MAIN_VIEW = HOME_VIEW;
    static final String ADMIN_VIEW = "Admin View";

    private static View getMain() {
        return new MainView().getView();
    }

    private static View getAdmin() {
        return new AdminView().getView();
    }

    @Override
    public void init() {
        addViewFactory(MAIN_VIEW, HomeFitnessFX::getMain);
        addViewFactory(ADMIN_VIEW, HomeFitnessFX::getAdmin);

        DrawerManager.buildDrawer(this);
    }

    @Override
    public void postInit(Scene scene) {
        Swatch.BLUE.assignTo(scene);

        scene.getStylesheets().add(HomeFitnessFX.class.getResource("style.css").toExternalForm());
    }
}
