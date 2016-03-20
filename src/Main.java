
import View.*;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    /**
     * Fonction main.
     *
     * @param args
     */
    public static void main(String[] args) {

        launch(args);
    }

    /**
     * Fonction start, lancement de la HomeView ( Menu ) du jeu.
     *
     * @param primaryStage Stage sur lequel afficher.
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("PACMAN");
        View v = new HomeView();
        v.start(primaryStage);
    }
}
