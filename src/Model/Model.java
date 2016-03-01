package Model;

import java.util.List;
import java.util.Observable;

/**
 * Created by thibault on 25/02/16.
 */
public class Model extends Observable{
    final public static double LF = 400;
    final public static double HF = 600;
    //dimension menu
    final public static double LM = 300;
    final public static double HM = 475;

    final public static double HG = 525;
    final public static double LG = 380;


    public static double SIZE_OF_CASE_X;
    public static double SIZE_OF_CASE_Y;
    public static double SPEED;
    public final static double MAX_PIXEL = 13.75;
    public final static double MIN_PIXEL = 13.75;
    public final static double PREF_PIXEL = 13.75;
    public static double GRID_SIZE_X = LG;
    public static double GRID_SIZE_Y = HG;

    public static List<Model> liste;

    public Model(){
        //liste.add(this);
    }
    public static void setGRID_SIZE_X(double x){
        Model.GRID_SIZE_X = x;
        setSIZE_OF_CASE_X();
    }

    public static void setGRID_SIZE_Y(double y){
        Model.GRID_SIZE_Y = y;
        setSIZE_OF_CASE_Y();
    }

    public static void setSIZE_OF_CASE_X() {
        Model.SIZE_OF_CASE_X = GRID_SIZE_X / Maze.plateau[0].length;
    }

    public static void setSIZE_OF_CASE_Y() {
        Model.SIZE_OF_CASE_Y = GRID_SIZE_Y / Maze.plateau.length;
    }

    public int getMonster_Case_X(double Pos_X) {
        return (int) ((Pos_X) / SIZE_OF_CASE_X);
    }

    public int getMonster_Case_Y(double Pos_Y) {
        return (int) ((Pos_Y) / SIZE_OF_CASE_Y);
    }


    public void setInfoCase(double Pos_X, double Pos_Y, int toSet) {
        Maze.plateau[getMonster_Case_Y(Pos_Y)][getMonster_Case_X(Pos_X)] = toSet;
    }

    public int getInfoCase(double Pos_X, double Pos_Y) {
        return (Maze.plateau[getMonster_Case_Y(Pos_Y)][getMonster_Case_X(Pos_X)]);
    }
}
