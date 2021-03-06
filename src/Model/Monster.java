package Model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.LinkedList;

public abstract class Monster extends Model {

    double speed;
    public int direction;
    public int newDirection = 5;
    public double height;
    public double width;
    int timing = 0;
    public double x;
    public double y;
    public Rectangle hitbox = new Rectangle();
    public double spawnx;
    public double spawny;
    public Node PrisonCenter = ListOfIntersection.getIntersectionAndClosest(9, 14);
    public static LinkedList ChangeQueue;

    /**
     * Créer une instance de Monster qui représente une créature du jeu (ghost
     * ou pacman)
     *
     * @param x coordonnée dans l'axe x
     * @param y coordonnée dans l'axe y
     * @param size taille du monster
     * @param speed vitesse
     * @param direction direction de base du monster
     */
    protected Monster(double x, double y, double size, double speed, int direction) {
        super();
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.direction = direction;
        height = SIZE_OF_CASE_Y - 1;
        width = SIZE_OF_CASE_X - 1;
        spawnx = x;
        spawny = y;
        hitbox.setX(x);
        hitbox.setY(y);
        hitbox.setWidth(SIZE_OF_CASE_X - 1);
        hitbox.setHeight(SIZE_OF_CASE_Y - 1);
        hitbox.setFill(Color.TRANSPARENT);
    }

    public abstract void interact();

    /**
     * Permet de bouger le pacman en modifiant ses valeurs x et y
     */
    public void movement() {

        if (new_move_is_possible()) {
            this.direction = this.newDirection;
            this.newDirection = 5;
        }

        if (!move_is_possible()) {
            this.direction = 0;
            return;
        }
        switch (this.direction) {
            case 1:
                this.y = Math.abs((this.y - this.speed));
                break;
            case 2:
                this.y = Math.abs(this.y + this.speed);
                break;
            case 3:
                this.x = Math.abs(this.x - this.speed);
                break;
            case 4:
                this.x = Math.abs(this.x + this.speed);
                break;
            default:
                break;
        }
        hitbox.setX(x);
        hitbox.setY(y);

        interact();
    }

    /**
     * Permet de voir si un mouvement dans une direction est possible et stop
     * le pacman si il rencontre un mur
     *
     * @return si oui ou non l'objet peut bouger
     */
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
        if (getInfoCase(pos_X, pos_Y) == 1 || getInfoCase(pos_X2, pos_Y2) == 1 || getInfoCase(pos_X, pos_Y) == 5 || getInfoCase(pos_X2, pos_Y2) == 5) {
            this.direction = 0;
            return false;
        }
        if (getInfoCase(pos_X, pos_Y) == 4 || getInfoCase(pos_X2, pos_Y2) == 4) {
            fromDeathToChase();
        }

        return true;
    }

    /**
     * Permet de savoir si l'objet peut bouger vers une direction demandé mais
     * qui n'etait pas encore disponible
     *
     * @return si oui ou non l'objet peut bouger
     */
    public boolean new_move_is_possible() {
        int pos_X = (int) x;
        int pos_Y = (int) y;
        int pos_X2 = pos_X;
        int pos_Y2 = pos_Y;
        switch (this.newDirection) {
            case 1:
                pos_Y -= height;
                pos_Y2 -= height;
                pos_X2 += width;
                break;
            case 2:
                pos_Y += height + height;
                pos_Y2 += height + height;
                pos_X2 += width;
                break;
            case 3:
                pos_X -= width;
                pos_X2 -= width;
                pos_Y2 += height;
                break;
            case 4:
                pos_X += width + width;
                pos_X2 += width + width;
                pos_Y2 += height;
                break;
            case 5:
                return false;
            default:
                break;
        }
        if (getInfoCase(pos_X, pos_Y) == 1 || getInfoCase(pos_X2, pos_Y2) == 1) {
            return false;
        }
        return true;
    }

    protected abstract void fromDeathToChase();

    public abstract void behavior(Pacman pac, Ghost red);

    public abstract void startFear();

    public abstract void startChase();

    public abstract boolean afraid();

    public abstract void startEaten();

    public abstract boolean eaten();

    public abstract void fromFearToChase();

    /**
     * Fonction réinitilisant la poisiton d'un monstre ( retour a la position de
     * départ ).
     */
    public void reset() {
        this.x = spawnx;
        this.y = spawny;
        startChase();
        this.newDirection = 5;
        this.direction = 0;
    }

    /**
     * Permet de bouger le pacman dans le mode VersusPaint en modifiant ses valeurs x et y
     */
    public void paintMovement(){
        if (new_move_is_possible()) {
            this.direction = this.newDirection;
            this.newDirection = 5;
        }

        if (!move_is_possible()) {
            this.direction = 0;
            return;
        }
        switch (this.direction) {
            case 1:
                this.y = Math.abs((this.y - this.speed));
                break;
            case 2:
                this.y = Math.abs(this.y + this.speed);
                break;
            case 3:
                this.x = Math.abs(this.x - this.speed);
                break;
            case 4:
                this.x = Math.abs(this.x + this.speed);
                break;
            default:
                break;
        }
        hitbox.setX(x);
        hitbox.setY(y);
        paintInteract();
    }
    public abstract void paintInteract();
}
