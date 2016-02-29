package Model;

/**
 * Created by thibaultgeoffroy on 25/02/2016.
 */
public abstract class Monster extends Model{
    double speed;
    int direction;
    public int newDirection = 5;
    double height;
    double width;
    int timing = 0;
    double x;
    double y;


    protected Monster(double x, double y, double size, double speed, int direction) {
        //super(x, y, size);
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.direction = direction;
        height = SIZE_OF_CASE_Y - 1;
        width = SIZE_OF_CASE_X - 1;
    }

    abstract boolean move_is_possible();


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
                //System.out.println("default case");
                break;
        }
        notifyObservers();
    }
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
                pos_X += width + width ;
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


}