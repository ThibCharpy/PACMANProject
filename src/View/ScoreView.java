package View;

import Model.NoMoreScoreException;
import Model.Score;
import Model.Score_Tab;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

import java.io.*;
import java.util.ArrayList;

/**
 * Created by thibault on 25/02/16.
 */
public class ScoreView extends View{

    final public static double menu_Width = 300;
    final public static double menu_Height = 475;

    private Button btn_Menu;
    private Button btn_Quit;

    private final int score_To_Show = 10;

    public ScoreView(){
        super();
        btn_Menu = new Button("Menu");
        btn_Quit = new Button("Quit");
    }

    @Override
    public void start(Stage stage) {
        //récupération des scores sur le fichier des scores..
        Score_Tab st = new Score_Tab();
        boolean is_new = false;
        try {
            FileInputStream fis = new FileInputStream("score.txt");
            ObjectInputStream o = new ObjectInputStream(fis);
            st.setScore_Tab_tab((ArrayList<Score>) o.readObject());
            fis.close();
        } catch (FileNotFoundException e) {
            System.out.print("Fichier non trouvé..  ");
            System.out.println("=> Creation score.txt..");
            File f = new File("score.txt");
            try {
                if (f.createNewFile()) {
                    System.out.println("score.txt is created..");
                    is_new = true;
                } else {
                    System.out.println("score.txt creation failed..");
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        //zone ou sont affiché les scores
        StackPane middle = new StackPane();
        Rectangle background_middle = new Rectangle(menu_Width, menu_Height);
        background_middle.setFill(Color.GREY);
        VBox score = new VBox();
        score.setAlignment(Pos.CENTER);
        if (is_new) {
            st = new Score_Tab();
        }
        Text score_display = null;
        int cpt = 0;
        while (cpt < score_To_Show) {
            score_display = null;
            if (is_new) {
                score_display = new Text((cpt + 1) + ". ..........");
            } else {
                try {
                    score_display = new Text((cpt + 1) + ". " + st.getScore(cpt));
                } catch (NoMoreScoreException n) {
                    score_display = new Text((cpt + 1) + ". ..........");
                }
            }
            score_display.setFont(new Font(25));
            score.getChildren().add(score_display);
            cpt++;
        }

        //gerer l'écriture ..
        try {
            FileOutputStream fos = new FileOutputStream("score.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(st.getScore_Tab_tab());
        } catch (FileNotFoundException e) {
            System.out.println("Fichier non trouvé pour sauvegarde..");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        middle.getChildren().add(background_middle);
        middle.getChildren().add(score);

        btn_Menu.setText("Menu");
        btn_Menu.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Back to Menu");
                start(stage);
            }
        });


        btn_Quit.setText("Quit");
        btn_Quit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Quit");
                stage.close();
            }

        });

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
        Rectangle background_root = new Rectangle(menu_Width, menu_Height);
        background_root.setFill(Color.BLACK);
        root.getChildren().add(background_root);
        root.getChildren().add(organisation);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
