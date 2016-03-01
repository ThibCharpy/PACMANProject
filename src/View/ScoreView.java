package View;

import Controller.ScoreController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by thibault on 25/02/16.
 */
public class ScoreView extends View{

    final public static double menu_Width = 300;
    final public static double menu_Height = 475;

    private ScoreController sCtrl;

    private Button btn_Menu;
    private Button btn_Quit;

    public ScoreView(String path){
        super();
        btn_Menu = new Button("Menu");
        btn_Quit = new Button("Quit");
        sCtrl = new ScoreController(this,path);
    }

    @Override
    public void start(Stage stage) {

        final HomeView hv = new HomeView();

        StackPane middle = new StackPane();
        Rectangle background_middle = new Rectangle(menu_Width, menu_Height);
        background_middle.setFill(Color.GREY);
        VBox score = new VBox();
        score.setAlignment(Pos.CENTER);

        Text score_display;
        int cpt = 0;
        int score_To_Show = 10;

        while (cpt < score_To_Show) {
            int n = sCtrl.getSt(cpt);
            if(n<0){
                score_display = new Text((cpt + 1) + ". ..........");
            }else{
                score_display = new Text((cpt + 1) + ". " + n);
            }
            score_display.setFont(new Font(25));
            score.getChildren().add(score_display);
            cpt++;
        }

        sCtrl.saveScore();

        middle.getChildren().add(background_middle);
        middle.getChildren().add(score);

        btn_Menu.setOnAction(event -> sCtrl.btn_Action(stage,hv));

        btn_Quit.setOnAction(event -> stage.close());

        StackPane top = new StackPane();
        Text title_top = new Text("HIGHSCORE");
        title_top.setFont(new Font(15));
        title_top.setFill(Color.WHITE);
        HBox title = new HBox();
        title.setAlignment(Pos.CENTER);
        title.getChildren().add(title_top);
        HBox box_btn = new HBox(200);
        box_btn.setAlignment(Pos.BASELINE_CENTER);
        box_btn.getChildren().add(btn_Menu);
        box_btn.getChildren().add(btn_Quit);
        top.getChildren().add(title);
        top.getChildren().add(box_btn);

        BorderPane organisation = new BorderPane();
        organisation.setTop(top);
        organisation.setCenter(middle);

        StackPane root = new StackPane();
        Rectangle background_root = new Rectangle(getWindow_Width(), getWindow_Height());
        background_root.setFill(Color.BLACK);
        root.getChildren().add(background_root);
        root.getChildren().add(organisation);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
