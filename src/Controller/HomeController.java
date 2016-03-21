package Controller;

import Model.Monster;
import Model.Score;
import View.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.LinkedList;


public class HomeController extends Controller{

    /**
     * Constructeur de la classe HomeController
     * @param v Vue associé au menu
     */
    public HomeController(HomeView v){
        super(v);
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
    public int getMonsterType(Monster m) {
        return 0;
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
    public Pane getMonsterPane(Monster m) {
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
}
