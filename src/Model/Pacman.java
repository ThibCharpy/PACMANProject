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
    }

    @Override
    boolean move_is_possible() {

        int pos_X = (int) x;
        int pos_Y = (int) y;
        int pos_X2 = pos_X;
        int pos_Y2 = pos_Y;
        switch (direction){
            case 1:
                pos_Y -= this.speed;
                pos_Y2 -= this.speed;
                pos_X2 += width ;
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

        if (getInfoCase(pos_X, pos_Y) == 1 || getInfoCase(pos_X2 , pos_Y2) == 1){
            this.direction = 0;
            return false;
        }
        if (getInfoCase(pos_X, pos_Y) == 3){ //|| getInfoCase(pos_X2 , pos_Y2) == 3){
            EatBigGomme(pos_X,pos_Y);
            return true;
        }
        if (getInfoCase(pos_X, pos_Y) == 2){//getInfoCase(pos_X, pos_Y) == 2 || getInfoCase(pos_X2 , pos_Y2) == 2){
            EatGomme(pos_X,pos_Y);
            return true;
        }
        return true;
    }

    public void EatGomme(double Pos_X, double Pos_Y){
        setInfoCase(Pos_X, Pos_Y, 0);
        MapChangeRequest gommeEated = new MapChangeRequest(getMonster_Case_Y(Pos_Y), getMonster_Case_X(Pos_X), "/Sprites/empty.png");
        ChangeQueue.add(gommeEated);
        updateScore(10);
    }

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
