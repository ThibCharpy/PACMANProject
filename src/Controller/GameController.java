package Controller;

import Model.*;
import View.*;
import javafx.scene.*;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;


/**
 * Created by thibault on 27/02/16.
 */
public class GameController extends  Controller {
    public Map<Pane , Monster> list;
    public Pane[] p;
    int timing = 0;
    public GameController(View v) {
        super(v);
        list = new HashMap<>();
         p = new Pane[5];
    }

    public static double getHG() {
        return Model.HG;
    }

    public static double getLG() {
        return Model.LG;
    }

    public void startGame() {
        Pacman pacman = new Pacman(185, 363, 13, 1, 0);
        Pane pPacman = getMonsterPane(pacman);
        RedGhost ghost = new RedGhost(200, 145, 12, 1, 0);
        Pane pGhost = getMonsterPane(ghost);
        OrangeGhost ghost2 = new OrangeGhost(140, 145, 12, 1, 0);
        Pane pGhost2 = getMonsterPane(ghost2);
        PinkGhost ghost3 = new PinkGhost(170, 145, 12, 1, 0);
        Pane pGhost3 = getMonsterPane(ghost3);
        BlueGhost ghost4 = new BlueGhost(190, 145, 12, 1, 0);
        Pane pGhost4 = getMonsterPane(ghost4);
        list.put(pPacman , pacman);
        list.put(pGhost, ghost);
        list.put(pGhost2, ghost2);
        list.put(pGhost3, ghost3);
        list.put(pGhost4, ghost4);
        p[0] = pPacman;
        p[1] = pGhost;
        p[2] = pGhost2;
        p[3] = pGhost3;
        p[4] = pGhost4;

    }
    public int sizeOfList() {
        return list.size();
    }


    public int getMonsterType(Monster m) {
        if(m instanceof Pacman) return 0;
        if(m instanceof RedGhost) return 1;
        if(m instanceof PinkGhost) return 2;
        if(m instanceof BlueGhost) return 3;
        if(m instanceof OrangeGhost) return 4;
        else return -1;
    }

    public static void initialize_Game(String path_field) throws IOException {
        Maze.initMapArray(path_field);
    }

    public static int getMaze_Width(){
        return Maze.plateau[0].length;
    }

    public static int getMaze_Heigth(){
        return Maze.plateau.length;
    }

    public static int getMaze_Box(int i, int j){
        return Maze.plateau[i][j];
    }

    public static int getMaze_CountNeighbour(int i , int j, int value) {
        return Maze.checkWall(i, j, value);
    }
    public double getMonsterWidth(int i) {
        Model m = list.get(i);
        if(m instanceof Monster){
            return ((Monster) m).width;
        }
        return 0;
    }
    public double getMonsterHeight(int i) {
        Model m = list.get(i);
        if(m instanceof Monster){
            return ((Monster) m).height;
        }
        return 0;
    }

    public StackPane getMonsterPosition(StackPane root) {
        for(int i = 0; i<5 ; i++){
            p[i].setLayoutX(list.get(p[i]).x);
            p[i].setLayoutY(list.get(p[i]).y);
        }
        return root;
    }

    public  double getMonsterX(int i) {
        return (list.get(i).x);
    }
    public double getMonsterY(int i) {
        return (list.get(i).y);
    }
    public Pane getMonsterPane(Monster m) {
        final Image img;
        switch (getMonsterType(m)) {
            case 0:
                img = SpriteMonster.pacsprites[0];
                break;
            case 1:
                img =  SpriteMonster.redsprites[0];
                break;
            case 2:
                img =  SpriteMonster.pinksprites[0];
                break;
            case 3:
                img =  SpriteMonster.bluesprites[0];
                break;
            case 4:
                img =  SpriteMonster.orangesprites[0];
                break;
            default:
                return null;
        }
        final ImageView imgV = new ImageView();
        imgV.setImage(img);
        imgV.setManaged(true);
        Pane pictureRegion = new Pane();
        imgV.fitWidthProperty().bind(pictureRegion.widthProperty());
        imgV.fitHeightProperty().bind(pictureRegion.heightProperty());
        pictureRegion.setMinWidth(getMonsterWidth((int)m.width));
        pictureRegion.setMinHeight(getMonsterHeight((int)m.height));
        pictureRegion.setMaxWidth(getMonsterWidth((int)m.width));
        pictureRegion.setMaxHeight(getMonsterHeight((int)m.height));
        pictureRegion.getChildren().add(imgV);
        pictureRegion.setLayoutX(m.x);
        pictureRegion.setLayoutY(m.y);
        pictureRegion.setManaged(false);
        return pictureRegion;
    }

    public void setGRID_SIZE_Y(double game_heigth) {
        Model.setGRID_SIZE_Y(game_heigth);
    }
    public void setGRID_SIZE_X(double game_width) {
        Model.setGRID_SIZE_X(game_width);
    }

    public StackPane implementPane(StackPane stack) {
        for(int i = 0 ; i<5 ; i++){
            stack.getChildren().add(p[i]);
        }
        return stack;
    }

    public void movement() {
        for(int i = 0; i<5; i++){
            list.get(p[i]).movement();
        }
    }

    public void getMonsterPosition() {
        for(int i = 0; i<5 ; i++){
            p[i].setLayoutX(list.get(p[i]).x);
            p[i].setLayoutY(list.get(p[i]).y);
        }
    }
    public void pacmanMovement(KeyCode k) {
        if (k == KeyCode.UP) {
            list.get(p[0]).newDirection = 1;
        }
        if (k == KeyCode.DOWN) {
            list.get(p[0]).newDirection = 2;
        }
        if (k == KeyCode.LEFT) {
            list.get(p[0]).newDirection = 3;
        }
        if (k == KeyCode.RIGHT) {
            list.get(p[0]).newDirection = 4;
        }
    }

    public void updateImage() {
        ImageView imgv;
        timing++;
        for( int i = 0; i<5 ; i++){
            imgv = new ImageView();
            imgv.setImage(SpriteMonster.getPicture(getMonsterType(list.get(p[i])) , list.get(p[i]).direction, timing));

            imgv.setManaged(true);
            imgv.fitWidthProperty().bind(p[i].widthProperty());
            imgv.fitHeightProperty().bind(p[i].heightProperty());
            System.out.println(p[i].getChildren().size());
            p[i].getChildren().remove(0);
            System.out.println(p[i].getChildren().size());
            p[i].getChildren().add(imgv);
        }
    }

    public void ghostBehavior() {
        for( int i = 1; i<5 ; i++){
            list.get(p[i]).behavior((Pacman)list.get(p[0]) , (Ghost)list.get(p[1]));
        }
    }

    public void initialize_list() {
        ListOfIntersection.initialiseList();
    }
}
