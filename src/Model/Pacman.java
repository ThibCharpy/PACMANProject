package Model;

import java.util.LinkedList;

/**
 * Created by thibaultgeoffroy on 25/02/2016.
 */
public class Pacman extends Monster {


    public static LinkedList ChangeQueue;
    private int score;


    public Pacman(double x, double y, double size, double speed, int direction) {
        super(x, y, size, speed, direction);
        ChangeQueue = new LinkedList();
    }

    public void interact(){
        if (getInfoCase(x+(width/2), y +(height/2)) == 3){ //|| getInfoCase(pos_X2 , pos_Y2) == 3){
            EatBigGomme(x,y);
        }
        if (getInfoCase(x+(width/2), y +(height/2)) == 2){//getInfoCase(pos_X, pos_Y) == 2 || getInfoCase(pos_X2 , pos_Y2) == 2){
            EatGomme(x,y);
        }
    }

    @Override
    public void behavior(Pacman pac, Ghost red) {
        return;
    }

    /**
     * Fonction utilisée pour l'ingestion des gommes par pacman, lorsqu'il est sur une case avec une gomme
     * on utilise un MapChangeRequest pour demander un changement de sprite de cette case.
     * On l'ajoute a la changeQueue qui représente les changements a apporté a la view et on change le score.
     * @param Pos_X coordonnée X du pacman
     * @param Pos_Y coordonnée Y du pacman 
     */
    public void EatGomme(double Pos_X, double Pos_Y){
        setInfoCase(Pos_X, Pos_Y, 0);
        MapChangeRequest gommeEated = new MapChangeRequest(getMonster_Case_Y(Pos_Y), getMonster_Case_X(Pos_X), "/Sprites/empty.png");
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
    public void EatBigGomme(double Pos_X, double Pos_Y){
        setInfoCase(Pos_X, Pos_Y, 0);
        MapChangeRequest BiggommeEated = new MapChangeRequest(getMonster_Case_Y(Pos_Y), getMonster_Case_X(Pos_X), "/Sprites/empty.png");
        ChangeQueue.add(BiggommeEated);
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
    public int getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(int score) {
        this.score = score;
    }

}
