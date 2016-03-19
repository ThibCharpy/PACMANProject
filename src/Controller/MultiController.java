package Controller;

import Model.*;
import View.*;
import javafx.scene.*;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
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
 * Created by thibaultgeoffroy on 17/03/2016.
 */
public class MultiController extends GameController{

    public Map<Pane, Monster> list;
    public static boolean restartNeeded = false;
    public static boolean PacDead = false;
    public static int cmpDeath = 0;
    public Pane[] p;
    public Score score;
    public int lifeLeft = 3;
    private int score_for_life = 0;
    int timing = 0;
    public SoundLibrary soundLibrary;
    String state = "idle";
    int timerFear = 0;
    boolean bonusExiste = false;

    public MultiController(View v) {
        super(v);
        list = new HashMap<>();
        soundLibrary = new SoundLibrary();
        p = new Pane[5];
        Model.controller = this;
        score = new Score("score", 0);
    }

    public LinkedList getChangeQueue() {
        Model m = list.get(p[0]);
        return ((Pacman) m).ChangeQueue;

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
        list.put(pPacman, pacman);
        p[0] = pPacman;
        Pacgirl pacman2 = new Pacgirl(185, 163, 13, 1, 0);
        Pane pPacman2 = getMonsterPane(pacman2);
        list.put(pPacman2, pacman2);
        p[1] = pPacman2;

    }

    public void resetPosition(){
        for(int i = 0; i<2; i++){
            list.get(p[i]).reset();
        }
        /*setMonsterX(0, 185);
        setMonsterY(0, 363);
        setMonsterX(1, 183);
        setMonsterY(1, 240);
        setMonsterX(2, 165);
        setMonsterY(2, 254);
        setMonsterX(3, 220);
        setMonsterY(3, 254);
        setMonsterX(4, 183);
        setMonsterY(4, 254);*/
    }

    public void setMonsterX(int i, int x) {
        (list.get(p[i]).x) = x;
    }

    public void setMonsterY(int i, int y) {
        (list.get(p[i]).y) = y;
    }

    public int sizeOfList() {
        return list.size();
    }

    public int getMonsterType(Monster m) {
        if (m.afraid()) {
            return 5;
        }
        if (m.eaten()) {
            return 6;
        }
        if (m instanceof Pacman) {
            return 0;
        }
        if (m instanceof RedGhost) {
            return 1;
        }
        if (m instanceof PinkGhost) {
            return 2;
        }
        if (m instanceof BlueGhost) {
            return 3;
        }
        if (m instanceof OrangeGhost) {
            return 4;
        } else {
            return -1;
        }
    }

    public void updateScore(int i) {
        score.setScore_Score(score.getScore_Score() + i);
        score_for_life += i;
        System.out.println("score : ");
        System.out.println(score);
        System.out.println("score_for_life : " );
        System.out.println(score_for_life);
        if(score_for_life >= 10000){
            lifeLeft++;
            this.soundLibrary.audio_extralife.play(1.2);
            score_for_life = score_for_life - 10000;
        }
    }

    public static void initialize_Game(String path_field) throws IOException {
        Maze.initMapArray(path_field);
    }

    public static int getMaze_Width() {
        return Maze.plateau[0].length;
    }

    public static int getMaze_Heigth() {
        return Maze.plateau.length;
    }

    public static int getMaze_Box(int i, int j) {
        return Maze.plateau[i][j];
    }

    public static int getMaze_CountNeighbour(int i, int j, int value) {
        return Maze.checkWall(i, j, value);
    }

    public double getMonsterWidth(int i) {
        Model m = list.get(i);
        if (m instanceof Monster) {
            return ((Monster) m).width;
        }
        return 0;
    }

    public double getMonsterHeight(int i) {
        Model m = list.get(i);
        if (m instanceof Monster) {
            return ((Monster) m).height;
        }
        return 0;
    }

    public StackPane getMonsterPosition(StackPane root) {
        for (int i = 0; i < 2; i++) {
            p[i].setLayoutX(list.get(p[i]).x);
            p[i].setLayoutY(list.get(p[i]).y);
        }
        return root;
    }

    public double getMonsterX(int i) {
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
                img = SpriteMonster.redsprites[0];
                break;
            case 2:
                img = SpriteMonster.pinksprites[0];
                break;
            case 3:
                img = SpriteMonster.bluesprites[0];
                break;
            case 4:
                img = SpriteMonster.orangesprites[0];
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
        pictureRegion.setMinWidth(getMonsterWidth((int) m.width));
        pictureRegion.setMinHeight(getMonsterHeight((int) m.height));
        pictureRegion.setMaxWidth(getMonsterWidth((int) m.width));
        pictureRegion.setMaxHeight(getMonsterHeight((int) m.height));
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
        for (int i = 0; i < 2; i++) {
            stack.getChildren().add(p[i]);
        }
        return stack;
    }

    public void pacmovement() {
        list.get(p[0]).movement();
        list.get(p[1]).movement();
    }
    public void deadMovement(){

    }
    public void movement() {

    }

    public void getMonsterPosition() {
        for (int i = 0; i < 2; i++) {
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
    public void pacgirlMovement(KeyCode k) {
        if (k == KeyCode.Z) {
            list.get(p[1]).newDirection = 1;
        }
        if (k == KeyCode.S) {
            list.get(p[1]).newDirection = 2;
        }
        if (k == KeyCode.Q) {
            list.get(p[1]).newDirection = 3;
        }
        if (k == KeyCode.D) {
            list.get(p[1]).newDirection = 4;
        }
    }

    public void DeathImage(int cmpt) {
        Pane pPacman = getMonsterPane(list.get(p[0]));
        ImageView imgv = new ImageView();
        imgv.setImage(SpriteMonster.getDeathPic(cmpt));
        imgv.setManaged(true);
        imgv.fitWidthProperty().bind(p[0].widthProperty());
        imgv.fitHeightProperty().bind(p[0].heightProperty());
        p[0].getChildren().remove(0);
        p[0].getChildren().add(imgv);
    }

    public void updateImage() {
        if (!GameController.PacDead) {
            timerFear--;
            if (timerFear == 0) {
                beginChase();
            }
            ImageView imgv;
            timing++;
            for (int i = 0; i < 2; i++) { // CONDITION FIN EATEN
                if (list.get(p[i]).eaten() && list.get(p[i]).hitbox.contains(list.get(p[i]).spawnx, list.get(p[i]).spawny)) {
                    list.get(p[i]).startChase();
                }
                imgv = new ImageView();
                imgv.setImage(SpriteMonster.getPicture(getMonsterType(list.get(p[i])), list.get(p[i]).direction, timing));
                imgv.setManaged(true);
                imgv.fitWidthProperty().bind(p[i].widthProperty());
                imgv.fitHeightProperty().bind(p[i].heightProperty());
                p[i].getChildren().remove(0);
                p[i].getChildren().add(imgv);
            }
        } else {

        }
    }

    public void setNbLifeLeft(){
        ((Pacman) list.get(p[0])).lifeLeft --;
    }

    public int getNbLifeLeft(){
        return ((Pacman) list.get(p[0])).lifeLeft;
    }

    private void beginChase() {
        state = "chase";
        for (int i = 1; i < 5; i++) {
            list.get(p[i]).fromFearToChase();
        }
    }

    public void ghostBehavior() {

    }

    public void initialize_list() {
        ListOfIntersection.initialiseList();
    }

    public void beginFear() {

    }

    public void findContact() {

    }

    private void contact(int i) {

    }

    public void mouvementByMouse(MouseEvent event) {
        double mouseX = event.getX();
        double mouseY = event.getY();
        double pacX = list.get(p[0]).x;
        double pacY = list.get(p[0]).y;

        double diffX = Math.abs(mouseX - pacX);
        double diffY = Math.abs(mouseY - pacY);

        if (diffX > diffY){
            if (mouseX > pacX) {
                pacmanMovement(KeyCode.RIGHT);
            }
            else{
                pacmanMovement(KeyCode.LEFT);
            }
        }
        else{
            if (mouseY > pacY) {
                pacmanMovement(KeyCode.DOWN);
            }
            else{
                pacmanMovement(KeyCode.UP);
            }
        }
    }

    public void deadBehavior() {

    }

    public void createBonus() {
    }
}
