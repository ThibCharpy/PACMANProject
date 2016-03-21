package View;

import Controller.Controller;
import javafx.stage.Stage;



public abstract class View {
    private final double window_Height = 600; // Hauteur
    private final double window_Width = 400; // Largeur

    public View(){

    }

    public abstract void start(Stage stage);


    public double getWindow_Height() {
        return window_Height;
    }

    public double getWindow_Width() {
        return window_Width;
    }


}
