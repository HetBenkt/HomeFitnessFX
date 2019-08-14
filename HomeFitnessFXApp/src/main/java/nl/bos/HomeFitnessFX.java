package nl.bos;

import com.gluonhq.charm.down.common.JavaFXPlatform;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.license.License;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.Swatch;
import javafx.scene.Scene;
import nl.bos.controllers.AdminView;
import nl.bos.controllers.ExercisesView;
import nl.bos.controllers.MainView;
import nl.bos.controllers.PlanningCardView;

import static nl.bos.IConstants.*;

@License(key = "7e71ebf2-bb31-44b7-806e-2cb0e3a7f4ba")
public class HomeFitnessFX extends MobileApplication {


    private static View getMain() {
        return new MainView().getView();
    }

    private static View getAdmin() {
        return new AdminView().getView();
    }

    private static View getPlanningCard() {
        return new PlanningCardView().getView();
    }

    private static View getExercises() {
        return new ExercisesView().getView();
    }

    @Override
    public void init() {
        addViewFactory(HOME_VIEW, HomeFitnessFX::getMain);
        addViewFactory(ADMIN_VIEW, HomeFitnessFX::getAdmin);
        addViewFactory(EXERCISES_VIEW, HomeFitnessFX::getExercises);
        addViewFactory(CREATE_PLANNING_CARD, HomeFitnessFX::getPlanningCard);

        DrawerManager.buildDrawer(this);
    }

    @Override
    public void postInit(Scene scene) {
        Swatch.INDIGO.assignTo(scene);

        if(JavaFXPlatform.isDesktop()){
            double ratio = 3.5D;
            scene.getWindow().setWidth(1440/ratio);
            scene.getWindow().setHeight(2960/ratio);
        }

        scene.getStylesheets().add(HomeFitnessFX.class.getResource("style.css").toExternalForm());
    }
}
