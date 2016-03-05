package Model;

import java.util.LinkedList;

/**
 * Created by thibaultgeoffroy on 25/02/2016.
 */
public class Ghost extends Monster{
    public Node lastVisited;
    String state;


    public Ghost(double x, double y, double size, double speed, int direction) {
        super(x, y, size, speed, direction);
    }

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

        /*hitzone.setX(c.getLayoutX());
        hitzone.setY(c.getLayoutY());*/

        /*System.out.println( x+" " + y);
        System.out.println(hitzone.getHeight() + "   " + hitzone.getWidth());
        System.out.println(hitzone.getX() + "    " + hitzone.getY());*/
        /*if (hitzone.contains(x, y)) {

        }*/
        if (getInfoCase(pos_X, pos_Y) == 1 || getInfoCase(pos_X2, pos_Y2) == 1) {
            this.direction = 0;
            return false;
        }

        return true;
    }


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

    public int mesureDistance(Node GhostPos, Node PacPos) {
        return (Math.abs(PacPos.noeud.getCoordX() - GhostPos.noeud.getCoordX()) + Math.abs(PacPos.noeud.getCoordY() - GhostPos.noeud.getCoordY()));
    }

  

}
