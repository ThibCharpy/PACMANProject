package Controller;

import Model.*;
import View.View;
import javafx.scene.*;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

/**
 * Created by thibault on 27/02/16.
 */
public class GameController extends  Controller {
    public GameController(View v) {
        super(v);
    }

    public static double getHG() {
        return Model.HG;
    }

    public static double getLG() {
        return Model.LG;
    }

    public static void startGame() {
        Pacman pacman = new Pacman(185, 363, 13, 1, 0);
        RedGhost ghost = new RedGhost(200, 145, 12, 1, 0);
        OrangeGhost ghost2 = new OrangeGhost(140, 145, 12, 1, 0);
        PinkGhost ghost3 = new PinkGhost(170, 145, 12, 1, 0);
        BlueGhost ghost4 = new BlueGhost(190, 145, 12, 1, 0);
    }

    public static int sizeOfList() {
        return Model.liste.size();
    }

    

    public static int getMonster(int i) {
        Model m = Model.liste.get(i);
        if(m instanceof Pacman) return 0;
        if(m instanceof RedGhost) return 1;
        if(m instanceof PinkGhost) return 2;
        if(m instanceof BlueGhost) return 3;
        if(m instanceof OrangeGhost) return 4;
        else return -1;
    }
}
