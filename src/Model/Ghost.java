package Model;

import java.util.LinkedList;

/**
 * Created by thibaultgeoffroy on 25/02/2016.
 */
public class Ghost extends Monster{
    public Node lastVisited;
    String state;


    protected Ghost(double x, double y, double size, double speed, int direction) {
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


    public void redBehavior(Pacman pac) {
       /* int Pos_X_pac = getMonster_Case_X(pac.c.getLayoutX());
        int Pos_Y_pac = getMonster_Case_Y(pac.c.getLayoutY());*/
        int Pos_X_Gho = getMonster_Case_X(x);
        int Pos_Y_Gho = getMonster_Case_Y(y);
        Node GhostPos = ListOfIntersection.getIntersection(Pos_X_Gho, Pos_Y_Gho);
        LinkedList<NoeudGraphe> result;
        if (GhostPos != this.lastVisited) {
            switch (this.state) {
                case "idle":
                    Node BlinkyPos = ListOfIntersection.getIntersection(20, 4);
                    if (!GhostPos.compare(BlinkyPos)) {
                        result = RechercheChemin.DiscoverPath(GhostPos, BlinkyPos, this);
                        this.newDirection = determineDirection(GhostPos.noeud, result.getFirst());
                        this.lastVisited = GhostPos;
                    }
                    break;
                case "chase":
                    int Pos_X_pac = getMonster_Case_X(pac.x);
                    int Pos_Y_pac = getMonster_Case_Y(pac.y);
                    Node PacPos = ListOfIntersection.getIntersection(Pos_X_pac, Pos_Y_pac);
                    if (ListOfIntersection.testIntersection(Pos_X_Gho, Pos_Y_Gho) != null) {
                        result = RechercheChemin.DiscoverPath(GhostPos, PacPos, this);
                        if (!result.isEmpty()) {
                            this.newDirection = determineDirection(GhostPos.noeud, result.getFirst());
                            this.lastVisited = GhostPos;
                        }

                    }
                    break;
                case "fear":
                    break;
            }
        }

    }

    public void orangeBehavior(Pacman pac) {
        int Pos_X_Gho = getMonster_Case_X(this.x);
        int Pos_Y_Gho = getMonster_Case_Y(this.y);
        Node GhostPos = ListOfIntersection.getIntersection(Pos_X_Gho, Pos_Y_Gho);
        Node ClydePos1 = ListOfIntersection.getIntersection(3, 24);
        Node ClydePos2 = ListOfIntersection.getIntersection(3, 22);
        LinkedList<NoeudGraphe> result;
        if (GhostPos != this.lastVisited) {
            switch (this.state) {
                case "idle":
                    if (!GhostPos.compare(ClydePos1)) {
                        result = RechercheChemin.DiscoverPath(GhostPos, ClydePos1, this);
                        this.newDirection = determineDirection(GhostPos.noeud, result.getFirst());
                        this.lastVisited = GhostPos;
                    } else if (!GhostPos.compare(ClydePos2)) {
                        result = RechercheChemin.DiscoverPath(GhostPos, ClydePos1, this);
                        this.newDirection = determineDirection(GhostPos.noeud, result.getFirst());
                        this.lastVisited = GhostPos;
                    }
                    break;
                case "chase":
                    int Pos_X_pac = getMonster_Case_X(pac.x);
                    int Pos_Y_pac = getMonster_Case_Y(pac.y);
                    Node PacPos = ListOfIntersection.getIntersection(Pos_X_pac, Pos_Y_pac);
                    if (ListOfIntersection.testIntersection(Pos_X_Gho, Pos_Y_Gho) != null) {
                        result = RechercheChemin.DiscoverPath(GhostPos, PacPos, this);
                        if (!result.isEmpty() && result.size() > 3) {
                            this.newDirection = determineDirection(GhostPos.noeud, result.getFirst());
                            this.lastVisited = GhostPos;
                        } else if ((!result.isEmpty() && result.size() <= 2)) {
                            if (!GhostPos.compare(ClydePos1)) {
                                result = RechercheChemin.DiscoverPath(GhostPos, ClydePos1, this);
                                this.newDirection = determineDirection(GhostPos.noeud, result.getFirst());
                                this.lastVisited = GhostPos;
                            } else if (!GhostPos.compare(ClydePos2)) {
                                result = RechercheChemin.DiscoverPath(GhostPos, ClydePos1, this);
                                this.newDirection = determineDirection(GhostPos.noeud, result.getFirst());
                                this.lastVisited = GhostPos;
                            }
                        }
                    }
                    break;
                case "fear":

                    break;
            }
        }
    }

    public void pinkBehavior(Pacman pac) {
        int Pos_X_Gho = getMonster_Case_X(this.x);
        int Pos_Y_Gho = getMonster_Case_Y(this.y);
        Node GhostPos = ListOfIntersection.getIntersection(Pos_X_Gho, Pos_Y_Gho);
        LinkedList<NoeudGraphe> result;
        if (GhostPos != this.lastVisited) {
            switch (this.state) {
                case "idle":
                    Node PinkyPos = ListOfIntersection.getIntersection(3, 4);
                    if (!GhostPos.compare(PinkyPos)) {
                        result = RechercheChemin.DiscoverPath(GhostPos, PinkyPos, this);
                        this.newDirection = determineDirection(GhostPos.noeud, result.getFirst());
                        this.lastVisited = GhostPos;
                    }
                    break;
                case "chase":
                    int Pos_X_pac = getMonster_Case_X(pac.x);
                    int Pos_Y_pac = getMonster_Case_Y(pac.y);
                    Node PacPos = ListOfIntersection.getIntersection(Pos_X_pac, Pos_Y_pac);
                    Node Pacp = PacPos.noeud.getVoisin().get((int) (Math.random() * PacPos.noeud.getVoisin().size()));
                    if (ListOfIntersection.testIntersection(Pos_X_Gho, Pos_Y_Gho) != null) {
                        result = RechercheChemin.DiscoverPath(GhostPos, Pacp, this);
                        if (!result.isEmpty()) {
                            this.newDirection = determineDirection(GhostPos.noeud, result.getFirst());
                            this.lastVisited = GhostPos;
                        }
                    }
                    break;
                case "fear":

                    break;
            }
        }
    }

    public void blueBehavior(Pacman pac) {
        int Pos_X_Gho = getMonster_Case_X(pac.x);
        int Pos_Y_Gho = getMonster_Case_Y(pac.y);
        Node GhostPos = ListOfIntersection.getIntersection(Pos_X_Gho, Pos_Y_Gho);
        LinkedList<NoeudGraphe> result;
        if (GhostPos != this.lastVisited) {
            switch (this.state) {
                case "idle":
                    Node InkyPos = ListOfIntersection.getIntersection(20, 24);
                    if (!GhostPos.compare(InkyPos)) {
                        result = RechercheChemin.DiscoverPath(GhostPos, InkyPos, this);
                        this.newDirection = determineDirection(GhostPos.noeud, result.getFirst());
                        this.lastVisited = GhostPos;
                    }
                    break;
                case "chase":
                    int Pos_X_pac = getMonster_Case_X(pac.x);
                    int Pos_Y_pac = getMonster_Case_Y(pac.y);
                    Node PacPos = ListOfIntersection.getIntersection(Pos_X_pac, Pos_Y_pac);
                    if (ListOfIntersection.testIntersection(Pos_X_Gho, Pos_Y_Gho) != null) {
                        result = RechercheChemin.DiscoverPath(GhostPos, PacPos, this);
                        if (!result.isEmpty()) {
                            this.newDirection = determineDirection(GhostPos.noeud, result.getFirst());
                            this.lastVisited = GhostPos;
                        }
                    }
                    break;
                case "fear":

                    break;
            }
        }
    }

    private int determineDirection(NoeudGraphe start, NoeudGraphe goal) {
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

}
