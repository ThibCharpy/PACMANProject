package View;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Created by thibaultgeoffroy on 17/03/2016.
 */
public class CreateView extends View{
    public void start(Stage stage){
        BorderPane root = new BorderPane();
        GridPane map = new GridPane();
        map.setHgap(1);
        map.setVgap(1);
        GridPane object = new GridPane();
        Rectangle r = new Rectangle();
        r.setFill(Color.BLACK);
        map.setGridLinesVisible(true);
        root.setCenter(map);
        root.setLeft(object);
        for (int i = 0; i < 29; i++){
            for (int j = 0; j< 23; j++){
                r = new Rectangle();
                r.setManaged(false);
                System.out.println(map.getHeight() + "     "+ map.getWidth());
                r.setHeight(map.getHeight()/29);
                r.setWidth(map.getWidth()/23);
                r.setFill(Color.RED);
                map.add(r, j, i);
            }
        }
        Scene scene = new Scene(root, 300, 250);
        stage.setScene(scene);
        stage.show();
    }
}
