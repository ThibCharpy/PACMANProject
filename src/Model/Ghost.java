package Model;

import Controller.GameController;

import java.util.LinkedList;

/**
 * Created by thibaultgeoffroy on 25/02/2016.
 */
public abstract class Ghost extends Monster{
    public Node lastVisited;
    String state;


    public Ghost(double x, double y, double size, double speed, int direction) {
        super(x, y, size, speed, direction);
        state = "chase";
        this.lastVisited = ListOfIntersection.getIntersection(10, 18);
    }

    @Override
    public void interact() {
        return;
    }


    public abstract void behavior(Pacman pac, Ghost red);

    /**
     * Méthode utilisé pour déterminé la direction entre deux intersections( une de depart et une d'arrivé ) situées sur la même ligne ou colonne
     * @param start Intersection de depart
     * @param goal Intersection d'arrivée
     * @return un entier relatif (meme que pour la direction des monstres) à la direction a prendre pour aller de start a goal.
     */

    public int determineDirection(NoeudGraphe start, NoeudGraphe goal) {
        if (start.getCoordX() < goal.getCoordX() && start.getCoordY() == goal.getCoordY()) {
            return 4; // Droite
        }
        if (start.getCoordX() > goal.getCoordX() && start.getCoordY() == goal.getCoordY()) {
            return 3; // Gauche
        }
        if (start.getCoordY() < goal.getCoordY() && start.getCoordX() == goal.getCoordX()) {
            return 2; // Bas
        }
        if (start.getCoordY() > goal.getCoordY() && start.getCoordX() == goal.getCoordX()) {
            return 1; // Haut
        }
        return this.direction;
    }

    /**
     * Mesure la distance entre deux intersections 
     * @param GhostPos intersection de depart
     * @param PacPos intersection d'arrivée
     * @return un entier représentant la distance entre deux intersection ( en nombre de case du tableau )
     */
    public int mesureDistance(Node GhostPos, Node PacPos) {
        return (Math.abs(PacPos.noeud.getCoordX() - GhostPos.noeud.getCoordX()) + Math.abs(PacPos.noeud.getCoordY() - GhostPos.noeud.getCoordY()));
    }

  

}
