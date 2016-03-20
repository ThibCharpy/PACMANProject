package Controller;

import View.View;
import javafx.stage.Stage;

public class Controller {

    protected View v;

    /**
     * Constructeur de la classe controller.
     *
     * @param v View sur laquelle le controller va agir.
     */
    public Controller(View v) {
        this.v = v;
    }

    /**
     * Fonction de controlle de bouton, lance le stage correspondant a la view.
     *
     * @param s Stage a start.
     * @param v View associé au Stage.
     */
    public void btn_Action(Stage s, View v) {
        v.start(s);
    }
}
