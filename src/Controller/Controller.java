package Controller;


import View.View;
import Model.Model;
import Model.Monster;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

/**
 * Created by thibault on 25/02/16.
 */
public class Controller {
    protected View v;

    public Controller(View v) {
        this.v = v;
    }

    public void btn_Action(Stage s, View v){
        v.start(s);
    }
}
