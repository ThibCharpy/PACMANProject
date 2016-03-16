package Model;

import Controller.GameController;
import java.util.LinkedList;

/**
 * Created by thibaultgeoffroy on 25/02/2016.
 */
public class Pacman extends Monster {

    public LinkedList ChangeQueue;
    public int lifeLeft;
    private int score;


    public Pacman(double x, double y, double size, double speed, int direction) {
        super(x, y, size, speed, direction);
        ChangeQueue = new LinkedList();
        lifeLeft = 3;
    }
    @Override
    public void startFear() {
    }

    @Override
    public void startChase() {
    }

    @Override
    public boolean afraid() {
        return false;
    }

    @Override
    public void startEaten() {
        
    }

    @Override
    public boolean eaten() {
        return false;
    }

    @Override
    public void fromFearToChase() {
    }

    @Override
    public void interact(){
        if (FindNumberOfGomme() != 0) {
            if (getInfoCase(x + (width / 2), y + (height / 2)) == 3) { //|| getInfoCase(pos_X2 , pos_Y2) == 3){
                EatBigGomme(x, y);
            }
            if (getInfoCase(x + (width / 2), y + (height / 2)) == 2) {//getInfoCase(pos_X, pos_Y) == 2 || getInfoCase(pos_X2 , pos_Y2) == 2){
                EatGomme(x, y);
            }
        } else {
            GameController.restartNeeded = true;
        }
    }

    @Override
    public void behavior(Pacman pac, Ghost red) {
        return;
    }
    
    private int FindNumberOfGomme(){
        int cmpt = 0;
        for(int x = 0; x < Maze.plateau.length; x ++){
            for (int i = 0; i < Maze.plateau[0].length; i++){
                if(Maze.plateau[x][i] == 2 || Maze.plateau[x][i] == 3){
                    cmpt++;
                }
            }
        }
        return cmpt;
    }
    
    /**
     * Fonction utilisée pour l'ingestion des gommes par pacman, lorsqu'il est sur une case avec une gomme
     * on utilise un MapChangeRequest pour demander un changement de sprite de cette case.
     * On l'ajoute a la changeQueue qui représente les changements a apporté a la view et on change le score.
     * @param Pos_X coordonnée X du pacman
     * @param Pos_Y coordonnée Y du pacman 
     */
    private void EatGomme(double Pos_X, double Pos_Y){
        setInfoCase(Pos_X, Pos_Y, 0);
        MapChangeRequest gommeEated = new MapChangeRequest(getMonster_Case_Y(Pos_Y), getMonster_Case_X(Pos_X), "/Sprites/empty.png", "Gomme");
        ChangeQueue.add(gommeEated);
        updateScore(10);   
    }
    
     /**
     * Fonction utilisée pour l'ingestion des gommes par pacman, lorsqu'il est sur une case avec une gomme
     * on utilise un MapChangeRequest pour demander un changement de sprite de cette case.
     * On l'ajoute a la changeQueue qui représente les changements a apporté a la view et on change le score.
     * @param Pos_X coordonnée X du pacman
     * @param Pos_Y coordonnée Y du pacman 
     */
    private void EatBigGomme(double Pos_X, double Pos_Y){
        setInfoCase(Pos_X, Pos_Y, 0);
        MapChangeRequest BiggommeEated = new MapChangeRequest(getMonster_Case_Y(Pos_Y), getMonster_Case_X(Pos_X), "/Sprites/empty.png", "BigGomme");
        ChangeQueue.add(BiggommeEated);
        controller.beginFear();
        updateScore(25);
    }
    private void updateScore(int points){
        this.setScore((this.getScore() + points));
    }

    public String getStringScore(){
        return Integer.toString(this.getScore());
    }
    /**
     * @return the score
     */
    
    
    
    @Override
    boolean move_is_possible() {
        int pos_X = (int) x;
        int pos_Y = (int) y;
        int pos_X2 = pos_X;
        int pos_Y2 = pos_Y;
        switch (direction) {
            case 1:
                pos_Y -= this.speed;
                pos_Y2 -= this.speed;
                pos_X2 += width;

                break;
            case 2:

                pos_Y += this.speed + height;
                pos_Y2 += this.speed + height;
                pos_X2 += width;

                break;
            case 3:

                pos_X -= this.speed;
                pos_X2 -= this.speed;
                pos_Y2 += height;

                break;
            case 4:

                pos_X += this.speed + width;
                pos_X2 += this.speed + width;
                pos_Y2 += height;

                break;
            case 5:
                return false;
            default:
                break;
        }
        if (getInfoCase(pos_X, pos_Y) == 1 || getInfoCase(pos_X2, pos_Y2) == 1 || getInfoCase(pos_X, pos_Y) == 4 || getInfoCase(pos_X2, pos_Y2) == 4) {
            this.direction = 0;
            return false;
        }

        return true;
    }
    
    public int getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(int score) {
        this.score = score;
    }

    @Override
    protected void fromDeathToChase() {
       
    }

}
