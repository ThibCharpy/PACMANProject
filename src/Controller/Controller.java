package Controller;


import View.View;
import Model.Model;
import Model.Monster;
import javafx.scene.input.KeyCode;

/**
 * Created by thibault on 25/02/16.
 */
public class Controller {
    private View v;
    private Model m;

    public Controller(View v) {

    }

    public static void movement(KeyCode k , Monster m ) {
        if (k == KeyCode.UP) {
            m.newDirection = 1;
        }
        if (k == KeyCode.DOWN) {
            m.newDirection = 2;
        }
        if (k == KeyCode.LEFT) {
            m.newDirection = 3;
        }
        if (k == KeyCode.RIGHT) {
            m.newDirection = 4;
        }
    }

}
