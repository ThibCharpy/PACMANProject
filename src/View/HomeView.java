package View;

import Controller.HomeController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
public class HomeView extends View{

    HomeController hCtrl;

    private Button btn_Score;
    private Button btn_Game;
    private Button btn_Quit;

    private final double btn_Width=250;
    private final double btn_Heigth=475;

    final public static double menu_Width = 300;
    final public static double menu_Height = 475;

    public HomeView(){
        super();
        btn_Score = new Button("Score");
        btn_Game = new Button("Game");
        btn_Quit = new Button("Quit");
        hCtrl = new HomeController(this);
    }

    @Override
    public void start(final Stage stage) {
        stage.setWidth(getWindow_Width());
        stage.setHeight(getWindow_Height());

        final View gv = new GameView();
        final View sv = new ScoreView();


        btn_Game.setMaxSize(btn_Width, btn_Heigth);
        btn_Game.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                hCtrl.btn_Action(stage,gv);
            }
        });

        btn_Score.setMaxSize(btn_Width, btn_Heigth);
        btn_Score.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                hCtrl.btn_Action(stage,sv);
            }
        });

        btn_Quit.setMaxSize(btn_Width, btn_Heigth);
        btn_Quit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Quitter");
                stage.close();
            }
        });

        //box pour placer les bouttons dans la stackpane
        VBox box_btn = new VBox(50);
        box_btn.setAlignment(Pos.CENTER);
        box_btn.getChildren().add(btn_Game);
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
