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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by thibault on 27/02/16.
 */
public class GameController extends Controller {

    

    public Map<Pane, Monster> list;
    public static boolean restartNeeded = false;
    public static boolean PacDead = false;
    public static int cmpDeath = 0;
    public Pane[] p;
    public Score sc;
    public int lifeLeft = 3;
    private int score_for_life = 0;
    int timing = 0;
    public SoundLibrary soundLibrary;
    String state = "idle";
    int timerFear = 0;
    boolean bonusExiste = false;

    public GameController(View v) {
        super(v);
        list = new HashMap<>();
        soundLibrary = new SoundLibrary();
        p = new Pane[5];
        Model.controller = this;
        sc = new Score("score", 0);
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
        RedGhost ghost = new RedGhost(183, 240, 12, 1, 0);
        Pane pGhost = getMonsterPane(ghost);
        OrangeGhost ghost2 = new OrangeGhost(165, 254, 12, 1, 0);
        Pane pGhost2 = getMonsterPane(ghost2);
        PinkGhost ghost3 = new PinkGhost(220, 254, 12, 1, 0);
        Pane pGhost3 = getMonsterPane(ghost3);
        BlueGhost ghost4 = new BlueGhost(183, 254, 12, 1, 0);
        Pane pGhost4 = getMonsterPane(ghost4);
        list.put(pPacman, pacman);
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

    public void resetPosition(){
        for(int i = 0; i<5; i++){
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

    public void saveGameScore(String name, String path) throws IOException, ClassNotFoundException {
        File f = new File(path);
        Score_Tab st;
        sc.setScore_Name(name);
        if(f.exists()){
            System.out.println("Fichier trouvé => ouverture fichier");
            st = new Score_Tab(path);
        }else{
            st = new Score_Tab();
            Path myFile = Paths.get(path);
            Files.createFile(myFile);
        }
        st.add_Score(sc);
        st.writeScore_Tab(path);
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
        sc.setScore_Score(sc.getScore_Score() + i);
        score_for_life += i;
        System.out.println("score : ");
        System.out.println(sc);
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
        for (int i = 0; i < 5; i++) {
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
        for (int i = 0; i < 5; i++) {
            stack.getChildren().add(p[i]);
        }
        return stack;
    }

    public void pacmovement() {
        list.get(p[0]).movement();
    }
    public void deadMovement(){
        for (int i = 1; i < 5; i++) {
            if(list.get(p[i]).afraid()) list.get(p[i]).movement();
        }
    }
    public void movement() {
        for (int i = 1; i < 5; i++) {
           if(!(list.get(p[i]).afraid())) list.get(p[i]).movement();
        }
    }

    public void getMonsterPosition() {
        for (int i = 0; i < 5; i++) {
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
            for (int i = 0; i < 5; i++) { // CONDITION FIN EATEN
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
        for (int i = 1; i < 5; i++) {
            if(!(list.get(p[i]).eaten())) list.get(p[i]).behavior((Pacman) list.get(p[0]), (Ghost) list.get(p[1]));
        }
    }

    public void initialize_list() {
        ListOfIntersection.initialiseList();
    }

    public void beginFear() {
        state = "fear";
        timerFear = 50;
        for (int i = 1; i < 5; i++) {
            list.get(p[i]).startFear();
        }
    }

    public void findContact() {
        double x = list.get(p[0]).x + ((list.get(p[0]).width) / 2);
        double y = list.get(p[0]).y + ((list.get(p[0]).height) / 2);
        for (int i = 1; i < 5; i++) {
            if (list.get(p[i]).hitbox.contains(x, y)) {
                contact(i);
            }
        }
    }

    private void contact(int i) {
        if ((list.get(p[i])).afraid()) {
            list.get(p[i]).startEaten();
            this.soundLibrary.audio_eat_ghost.play(1.2);
            int scoreMult = 1;
            for (int x = 1; x < 5; x++) {
                if (list.get(p[x]).eaten()) {
                    scoreMult ++;
                }
            }
            sc.setScore_Score(sc.getScore_Score() + 200 * scoreMult);
            score_for_life += 200 * scoreMult;
        } else if (!list.get(p[i]).eaten()) {
            GameController.PacDead = true;
            
        }
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
        for(int i = 1; i<5 ; i++){
            if(list.get(p[i]).eaten()){
               list.get(p[i]).behavior( (Pacman) list.get(p[0]), (Ghost) list.get(p[1]));
            }
        }
    }

    public void createBonus() {

        if ((int)(Math.random()*1000)%100 == 0 && !bonusExiste){
            bonusExiste = true;
            while(true) {
                for (int i = 0; i < Maze.plateau.length; i++){
                    for (int j = 0; j < Maze.plateau[0].length; j++) {
                        if ((int) (Math.random() * 1000) % 100 == 0 && Maze.plateau[i][j]==2) {
                            MapChangeRequest m = new MapChangeRequest(i, j, "/Sprites/bonus_cherry.png", "Bonus");
                            getChangeQueue().add(m);
                            Maze.plateau[i][j] = 6;
                            return;
                        }
                    }
                }
            }

        }
    }
}
