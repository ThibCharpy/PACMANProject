package View;

import javafx.stage.Stage;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by thibault on 25/02/16.
 */
public abstract class View implements Observer{
    String title;
    double window_Height; // Hauteur
    double window_Width; // Largeur

    public View(){
        title="unknow";
        window_Height=0;
        window_Width=0;
    }

    public View(String name){
        title=name;
        window_Height=0;
        window_Width=0;
    }

    public View(String name, double height, double width){
        title=name;
        window_Height=height;
        window_Width=width;
    }

    public abstract void start(Stage stage);
    public abstract void update(Observable o, Object arg);
}
