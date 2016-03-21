package View;

import Controller.HomeController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Created by thibault on 25/02/16.
 */
public class HomeView extends View {

    HomeController hCtrl;

    private Button btn_Score;
    private Button btn_Game;
    private Button btn_Paint_Versus_Mode;
    private Button btn_Quit;

    final public static double menu_Width = 300;
    final public static double menu_Height = 475;

    /**
     * Constructeur de classe HomeView.
     */
    public HomeView() {
        super();
        btn_Score = new Button("Score");
        btn_Game = new Button("Game");
        btn_Quit = new Button("Quit");
        btn_Paint_Versus_Mode = new Button("Versus Paint Mode");
        hCtrl = new HomeController(this);
    }

    /**
     * Fonction start pour la gestion du menu principal.
     *
     * @param stage Stage sur lequel affiché le menu principal.
     */
    @Override
    public void start(final Stage stage) {
        stage.setWidth(getWindow_Width());
        stage.setHeight(getWindow_Height());

        double btn_Width = 250;
        double btn_Height = 475;

        final View gv = new GameView("score.txt", "/Sprites/terrain.txt");
        final View sv = new ScoreView("score.txt");
        final View vv = new VersusPaintView("src/Model/score.txt");
        //final View sv = new CreateView();

        btn_Game.setMaxSize(btn_Width, btn_Height);
        btn_Game.setOnAction(event -> hCtrl.btn_Action(stage, gv));

        btn_Paint_Versus_Mode.setMaxSize(btn_Width, btn_Height);
        btn_Paint_Versus_Mode.setOnAction(event -> hCtrl.btn_Action(stage, vv));

        btn_Score.setMaxSize(btn_Width, btn_Height);
        btn_Score.setOnAction(event -> hCtrl.btn_Action(stage, sv));

        btn_Quit.setMaxSize(btn_Width, btn_Height);
        btn_Quit.setOnAction(event -> stage.close());

        //box pour placer les bouttons dans la stackpane
        VBox box_btn = new VBox(50);
        box_btn.setAlignment(Pos.CENTER);
        box_btn.getChildren().add(btn_Game);
        box_btn.getChildren().add(btn_Paint_Versus_Mode);
        box_btn.getChildren().add(btn_Score);
        box_btn.getChildren().add(btn_Quit);

        //fenetre de menu
        StackPane menu = new StackPane();
        Rectangle background = new Rectangle(menu_Width, menu_Height);
        background.setFill(Color.GREY);
        menu.setMaxSize(menu_Width, menu_Height);
        menu.getChildren().add(background);
        menu.getChildren().add(box_btn);

        //definition de la fenetre
        StackPane root = new StackPane();
        Rectangle background_root = new Rectangle(getWindow_Width(), getWindow_Height());
        background_root.setFill(Color.BLACK);
        root.getChildren().add(background_root);
        root.getChildren().add(menu);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

}
