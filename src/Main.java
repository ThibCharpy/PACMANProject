import Controller.GameController;
import View.*;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by thibault on 25/02/16.
 */
public class Main extends Application{


    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("PACMAN");
        View v = new HomeView();
        v.start(primaryStage);
    }
}
