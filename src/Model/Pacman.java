package Model;

import Controller.GameController;
import java.util.LinkedList;

/**
 * Created by thibaultgeoffroy on 25/02/2016.
 */
public class Pacman extends Monster {

    public LinkedList ChangeQueue;
    public int lifeLeft;
    private Node LastVisited = ListOfIntersection.getIntersectionAndClosest(11, 14);

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
    public void interact() {
        if (FindNumberOfGomme() != 0) {
            if (getInfoCase(x + (width / 2), y + (height / 2)) == 3) { //|| getInfoCase(pos_X2 , pos_Y2) == 3){
                EatBigGomme(x, y);
            }
            if (getInfoCase(x + (width / 2), y + (height / 2)) == 2) {//getInfoCase(pos_X, pos_Y) == 2 || getInfoCase(pos_X2 , pos_Y2) == 2){
                EatGomme(x, y);
            }
            if (getInfoCase(x + (width / 2), y + (height / 2)) == 6) {//getInfoCase(pos_X, pos_Y) == 2 || getInfoCase(pos_X2 , pos_Y2) == 2){
                EatBonus(x, y);
            }
        } else {
            GameController.restartNeeded = true;
        }
    }

    @Override
    public void behavior(Pacman pac, Ghost red) { // Permet l'usage de multiple téléporteur, si volontée d'en ajouté plusieurs.
        int Pos_X = getMonster_Case_X(this.x + (width / 2));
        int Pos_Y = getMonster_Case_Y(this.y + (height / 2));
        int xp = 0;
        int yp = 0;
        int dirsave = this.direction;
        int newdirsave = this.newDirection;
        Node PacPos = ListOfIntersection.getIntersection(Pos_X, Pos_Y);
        if (PacPos != null && PacPos.noeud != null) {
            boolean passageAutorisé = false;
            if (PacPos.noeud.TypeOf.equals("TeleportG")) {
                if (this.direction == 3) {
                    passageAutorisé = true;
                    xp = ((int) -Model.SIZE_OF_CASE_X);
                    yp = 1;
                }
            }
            if (PacPos.noeud.TypeOf.equals("TeleportD")) {
                if (this.direction == 4) {
                    passageAutorisé = true;
                    xp = ((int) Model.SIZE_OF_CASE_X);
                    yp = 1;
                }
            }
            if (PacPos.noeud.TypeOf.equals("TeleportH")) {
                if (this.direction == 1) {
                    passageAutorisé = true;
                    yp = ((int) -Model.SIZE_OF_CASE_Y);
                }
            }
            if (PacPos.noeud.TypeOf.equals("TeleportB")) {
                if (this.direction == 2) {
                    passageAutorisé = true;
                    yp = ((int) Model.SIZE_OF_CASE_Y);
                }
            }
            if (passageAutorisé) {
                this.LastVisited = PacPos;
                LinkedList<Node> destination = new LinkedList();
                for (Node element : ListOfIntersection.IntersectionList) {
                    if ((element.noeud.TypeOf.equals("TeleportG"))
                            || (element.noeud.TypeOf.equals("TeleportD"))
                            || (element.noeud.TypeOf.equals("TeleportB"))
                            || (element.noeud.TypeOf.equals("TeleportH"))
                            && (!element.compare(PacPos))) {
                        destination.add(element);
                    }
                }
                for (Node element : destination) {
                    if (!(element.compare(this.LastVisited)) && (element.noeud.getCoordX() == PacPos.noeud.getCoordX() || element.noeud.getCoordY() == PacPos.noeud.getCoordY())) {
                        this.LastVisited = PacPos;
                        this.x = (int) element.noeud.coordX * Model.SIZE_OF_CASE_X + xp;
                        this.y = (int) element.noeud.coordY * Model.SIZE_OF_CASE_Y + yp;
                        controller.getMonsterPosition();
                        controller.updateImage();
                    }
                }
                destination.clear();
            }

        }

    }

    private int FindNumberOfGomme() {
        int cmpt = 0;
        for (int x = 0; x < Maze.plateau.length; x++) {
            for (int i = 0; i < Maze.plateau[0].length; i++) {
                if (Maze.plateau[x][i] == 2 || Maze.plateau[x][i] == 3) {
                    cmpt++;
                }
            }
        }
        return cmpt;
    }

    /**
     * Fonction utilisée pour l'ingestion des gommes par pacman, lorsqu'il est
     * sur une case avec une gomme on utilise un MapChangeRequest pour demander
     * un changement de sprite de cette case. On l'ajoute a la changeQueue qui
     * représente les changements a apporté a la view et on change le score.
     *
     * @param Pos_X coordonnée X du pacman
     * @param Pos_Y coordonnée Y du pacman
     */
    private void EatGomme(double Pos_X, double Pos_Y) {
        if (getInfoCase(x + (width / 2), y + (height / 2)) == 2) {
            setInfoCase(x + (width / 2), y + (height / 2), 0);
            MapChangeRequest gommeEated = new MapChangeRequest(getMonster_Case_Y(y + (height / 2)/*Pos_Y*/), getMonster_Case_X(x + (width / 2)/*Pos_X*/), "/Sprites/empty.png", "Gomme");
            ChangeQueue.add(gommeEated);
            controller.updateScore(10);
        }
    }

    /**
     * Fonction utilisée pour l'ingestion des gommes par pacman, lorsqu'il est
     * sur une case avec une gomme on utilise un MapChangeRequest pour demander
     * un changement de sprite de cette case. On l'ajoute a la changeQueue qui
     * représente les changements a apporté a la view et on change le score.
     *
     * @param Pos_X coordonnée X du pacman
     * @param Pos_Y coordonnée Y du pacman
     */
    private void EatBigGomme(double Pos_X, double Pos_Y) {
        if (getInfoCase(x + (width / 2), y + (height / 2)) == 3) {
            setInfoCase(x + (width / 2), y + (height / 2), 0);
            MapChangeRequest BiggommeEated = new MapChangeRequest(getMonster_Case_Y(y + (height / 2)), getMonster_Case_X(x + (width / 2)), "/Sprites/empty.png", "BigGomme");
            ChangeQueue.add(BiggommeEated);
            controller.beginFear();
            controller.updateScore(25);
        }
    }

    private void EatBonus(double x, double y) {
        if (getInfoCase(x + (width / 2), y + (height / 2)) == 6) {
            setInfoCase(x + (width / 2), y + (height / 2), 0);
            MapChangeRequest m = new MapChangeRequest(getMonster_Case_Y(y + (height / 2)), getMonster_Case_X(x + (width / 2)), "/Sprites/empty.png", "Bonus");
            ChangeQueue.add(m);
            controller.updateScore(100);
        }
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
        if (getInfoCase(pos_X, pos_Y) == 1 || getInfoCase(pos_X2, pos_Y2) == 1 || getInfoCase(pos_X, pos_Y) == 4 || getInfoCase(pos_X2, pos_Y2) == 4 || getInfoCase(pos_X, pos_Y) == 5 || getInfoCase(pos_X2, pos_Y2) == 5) {
            this.direction = 0;
            return false;
        }
        return true;
    }

    @Override
    protected void fromDeathToChase() {

    }

}
