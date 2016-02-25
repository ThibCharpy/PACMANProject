package View;

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

import java.util.Observable;

/**
 * Created by thibault on 25/02/16.
 */
public class HomeView extends View{
    public HomeView(String name,  double height, double width){
        super(name,height,width);
    }

    @Override
    public void start(Stage stage) {

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
