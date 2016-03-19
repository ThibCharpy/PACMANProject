package Model;

/**
 * Created by thibaultgeoffroy on 17/03/2016.
 */
public class Pacgirl extends Pacman {
    public Pacgirl(double x, double y, double size, double speed, int direction) {
        super(x, y, size, speed, direction);
    }
    public void paint(double x, double y) {
        if(getInfoCase(x + (width / 2), y + (height / 2))==0){
            setInfoCase(x + (width / 2), y + (height / 2), 8);
            MapChangeRequest m = new MapChangeRequest(getMonster_Case_Y(y + (height / 2)), getMonster_Case_X(x + (width / 2)), "/Sprites/pink-up-1.png", "paint");
            ChangeQueue.add(m);
            controller.updateScore(10);
        }
    }

}
