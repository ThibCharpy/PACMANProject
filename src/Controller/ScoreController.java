package Controller;

import Model.*;
import View.View;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

public class ScoreController extends Controller {

    private final String path_Field;

    private Score_Tab st;

    /**
     * Constructeur de la classe ScoreController
     * @param v vue associer a l'affichage du score
     * @param path chemin vers le fichier de sauvegarde des scores
     */
    public ScoreController(View v, String path){
        super(v);
        File f = new File(path);
        if(f.exists()){
            try {
                System.out.println("Fichier trouvé => ouverture fichier");
                st = new Score_Tab(path);
            } catch (IOException | ClassNotFoundException e) {
            }
        }else{
            st = new Score_Tab();
        }
        path_Field = path;
    }

    /**
     * Méthode gérant l'action lié a l'appui sur un bouton
     * @param s Stage lié au bouton
     * @param v View lié au bouton
     */
    @Override
    public void btn_Action(Stage s, View v){
        v.start(s);
    }

    @Override
    public void startGame() {

    }

    @Override
    public LinkedList getChangeQueue() {
        return null;
    }

    @Override
    public void setMonsterPosition() {

    }

    @Override
    public void updateImage() {

    }

    @Override
    public void resetPosition() {

    }

    @Override
    public void saveGameScore(String name, String path) throws IOException, ClassNotFoundException {

    }

    @Override
    public void updateScore(int i) {

    }

    @Override
    public Score getSc() {
        return null;
    }

    @Override
    public double getMonsterWidth(int i) {
        return 0;
    }

    @Override
    public double getMonsterHeight(int i) {
        return 0;
    }

    @Override
    public StackPane getMonsterPosition(StackPane root) {
        return null;
    }

    @Override
    public void setGRID_SIZE_Y(double game_heigth) {

    }

    @Override
    public void setGRID_SIZE_X(double game_width) {

    }

    @Override
    public StackPane implementPane(StackPane stack) {
        return null;
    }

    @Override
    public void pacmovement() {

    }

    @Override
    public void movement() {

    }

    @Override
    public void pacmanMovement(KeyCode k) {

    }

    @Override
    public void DeathImage(int cmpt) {

    }

    @Override
    protected void beginChase() {

    }

    @Override
    public void ghostBehavior() {

    }

    @Override
    public void findContact() {

    }

    @Override
    public void initialize_list() {

    }

    @Override
    protected void contact(int i) {

    }

    @Override
    public void mouvementByMouse(MouseEvent event) {

    }

    @Override
    public void deadBehavior() {

    }

    @Override
    public void createBonus() {

    }

    @Override
    public void beginFear() {

    }

    @Override
    public Pane getMonsterPane(Monster m) {
        return null;
    }

    @Override
    public int getMonsterType(Monster m) {
        return 0;
    }

    /**
     * Méthode gérant la création du fichier de sauvegarde des scores si il n'existe pas, sauvegarde le score.
     */
    public void saveScore(){
        File f = new File(path_Field);
        if(!(f.exists())){
            Path myFile = Paths.get(path_Field);
            try {
                Path file = Files.createFile(myFile);
            } catch (IOException e) {
            }
        }
        try {
            st.writeScore_Tab(path_Field);
        } catch (IOException e) {
        }
    }

    /**
     * Getter de score
     * @param n
     * @return 
     */
    public Score getSt(int n) {
        try {
            return st.getScore(n);
        } catch (NoMoreScoreException nme) {
            return null;
        }
    }
}
