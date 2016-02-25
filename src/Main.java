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
        View v = new HomeView("PACMAN",600,400);
        v.start(primaryStage);
    }
}
