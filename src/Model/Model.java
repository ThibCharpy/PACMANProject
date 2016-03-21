package Model;

import Controller.*;

import java.util.Observable;

public class Model extends Observable {

    final public static double HG = 525;
    final public static double LG = 380;
    public static double SIZE_OF_CASE_X;
    public static double SIZE_OF_CASE_Y;
    public static double SPEED;
    public final static double MAX_PIXEL = 13.75;
    public final static double MIN_PIXEL = 13.75;
    public final static double PREF_PIXEL = 13.75;
    public static double GRID_SIZE_X;
    public static double GRID_SIZE_Y;
    public static Controller controller;


    /**
     * Défini la taille X (largeur) de la grille de jeu.
     *
     * @param x Taille X en pixels.
     */
    public static void setGRID_SIZE_X(double x) {
        Model.GRID_SIZE_X = x;
        setSIZE_OF_CASE_X();
    }

    /**
     * Défini la taille Y (hauteur) de la grille de jeu.
     *
     * @param y Taille Y en pixels.
     */
    public static void setGRID_SIZE_Y(double y) {
        Model.GRID_SIZE_Y = y;
        setSIZE_OF_CASE_Y();
    }

    /**
     * Défini la taille X (largeur) d'une case de la grille jeu.
     */
    public static void setSIZE_OF_CASE_X() {
        Model.SIZE_OF_CASE_X = GRID_SIZE_X / Maze.plateau[0].length;
    }

    /**
     * Défini la taille Y (hauteur) d'une case de la grille jeu.
     */
    public static void setSIZE_OF_CASE_Y() {
        Model.SIZE_OF_CASE_Y = GRID_SIZE_Y / Maze.plateau.length;
    }

    /**
     * Getter d'un entier associé à une case du tableau plateau en fonction de
     * la position d'un pixel.
     *
     * @param Pos_X Position X en pixel.
     * @return entier représentant une case du tableau (0,X)
     */
    public int getMonster_Case_X(double Pos_X) {
        return (int) ((Pos_X) / SIZE_OF_CASE_X);
    }

    /**
     * Getter d'un entier associé à une case du tableau plateau en fonction de
     * la position d'un pixel.
     *
     * @param Pos_Y Position Y en pixel.
     * @return entier représentant une case du tableau (Y,0)
     */
    public int getMonster_Case_Y(double Pos_Y) {
        return (int) ((Pos_Y) / SIZE_OF_CASE_Y);
    }

    /**
     * Getter de la position en pixel d'un monstre en fonction de son
     * emplacement dans le tableau.
     *
     * @param Pos_X emplacement X dans le tableau.
     * @return Position X en pixel.
     */
    public static int getMonster_Pos_X(int Pos_X) {
        return (int) ((Pos_X) * SIZE_OF_CASE_X);
    }

    /**
     * Getter de la position en pixel d'un monstre en fonction de son
     * emplacement dans le tableau.
     *
     * @param Pos_Y emplacement Y dans le tableau.
     * @return Position Y en pixel.
     */
    public static int getMonster_Pos_Y(int Pos_Y) {
        return (int) ((Pos_Y) * SIZE_OF_CASE_Y);
    }

    /**
     * Setter pour changer l'information contenue dans une case du tableau.
     *
     * @param Pos_X Coordonnée X.
     * @param Pos_Y Coordonnée Y.
     * @param toSet Nouvel entier de la case [Y][X].
     */
    public void setInfoCase(double Pos_X, double Pos_Y, int toSet) {
        Maze.plateau[getMonster_Case_Y(Pos_Y)][getMonster_Case_X(Pos_X)] = toSet;
    }

    /**
     * Getter de l'information contenue dans une case du tableau.
     *
     * @param Pos_X Coordonnée X.
     * @param Pos_Y Coordonnée Y.
     * @return Information contenue dans [Y][X].
     */
    public int getInfoCase(double Pos_X, double Pos_Y) {
        return (Maze.plateau[getMonster_Case_Y(Pos_Y)][getMonster_Case_X(Pos_X)]);
    }
}
