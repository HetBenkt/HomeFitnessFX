package nl.bos;

import com.gluonhq.charm.down.common.JavaFXPlatform;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.license.License;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.Swatch;
import javafx.scene.Scene;
import nl.bos.controllers.MainPresenter;
import nl.bos.controllers.MainView;
import nl.bos.controllers.exercise.ExerciseDataView;
import nl.bos.controllers.exercise.ExerciseView;
import nl.bos.controllers.exercises.ExercisesView;
import nl.bos.controllers.planningcard.PlanningCardView;
import nl.bos.controllers.planningcards.PlanningCardsView;

import static nl.bos.IConstants.*;

@License(key = "7e71ebf2-bb31-44b7-806e-2cb0e3a7f4ba")
public class HomeFitnessFX extends MobileApplication {

    private static View getMain() {
        View main = new MainView().getView();
        main.setOnShown(event -> {
            MainPresenter mainPresenter = (MainPresenter) Controllers.get("MainPresenter");
            mainPresenter.updateExercises();
        });
        return main;
    }

    private static View getPlanningCards() {
        return new PlanningCardsView().getView();
    }

    private static View getPlanningCard() {
        return new PlanningCardView().getView();
    }

    private static View getExercises() {
        return new ExercisesView().getView();
    }

    private static View getExercise() {
        return new ExerciseView().getView();
    }

    private static View getExerciseData() {
        return new ExerciseDataView().getView();
    }

    @Override
    public void init() {
        addViewFactory(HOME_VIEW, HomeFitnessFX::getMain);

        addViewFactory(VIEW_PLANNING_CARDS, HomeFitnessFX::getPlanningCards);
        addViewFactory(EDIT_PLANNING_CARD, HomeFitnessFX::getPlanningCard);

        addViewFactory(VIEW_EXERCISES, HomeFitnessFX::getExercises);
        addViewFactory(EDIT_EXERCISE, HomeFitnessFX::getExercise);
        addViewFactory(ADD_EXERCISE_DATA, HomeFitnessFX::getExerciseData);

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
