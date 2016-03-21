package Controller;

import Model.Monster;
import Model.Score;
import View.View;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;

public abstract  class Controller {

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

    abstract public void startGame();
    abstract public LinkedList getChangeQueue();

    abstract public void setMonsterPosition();

    abstract public void updateImage();
    abstract public void resetPosition();
    abstract public void saveGameScore(String name, String path) throws IOException, ClassNotFoundException;
    abstract public int getMonsterType(Monster m);
    abstract public void updateScore(int i);
    abstract public Score getSc();
    abstract public double getMonsterWidth(int i);
    abstract public double getMonsterHeight(int i);
    abstract public StackPane getMonsterPosition(StackPane root);
    abstract public Pane getMonsterPane(Monster m);
    abstract public void setGRID_SIZE_Y(double game_heigth);
    abstract public void setGRID_SIZE_X(double game_width);
    abstract public StackPane implementPane(StackPane stack);
    abstract public void pacmovement();
    abstract public void movement();
    abstract public void pacmanMovement(KeyCode k);
    abstract public void DeathImage(int cmpt);
    abstract protected void beginChase();
    abstract public void ghostBehavior();
    abstract public void findContact();
    abstract public void initialize_list();
    abstract protected void contact(int i);
    abstract public void mouvementByMouse(MouseEvent event);
    abstract public void deadBehavior();
    abstract public void createBonus();
    abstract public void beginFear();

}
