package View;

import Controller.GameController;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Created by thibaultgeoffroy on 29/02/2016.
 */
public class SpriteMonster {
    public static Image[] pacsprites = createPacman();
    public static Image[] redsprites = createRed();

    private static Image[] createRed() {
        redsprites = new Image[9];
        redsprites[0] = new Image("/Sprites/red-down-1.png");
        redsprites[1] = new Image("/Sprites/red-down-2.png");
        redsprites[2] = new Image("/Sprites/red-left-1.png");
        redsprites[3] = new Image("/Sprites/red-left-2.png");
        redsprites[4] = new Image("/Sprites/red-right-1.png");
        redsprites[5] = new Image("/Sprites/red-right-2.png");
        redsprites[6] = new Image("/Sprites/red-up-1.png");
        redsprites[7] = new Image("/Sprites/red-up-2.png");
        return redsprites;
    }


    public static Image[] bluesprites = createBlue();

    static private Image[] createBlue() {
        bluesprites = new Image[9];
        bluesprites[0] = new Image("/Sprites/blue-down-1.png");
        bluesprites[1] = new Image("/Sprites/blue-down-2.png");
        bluesprites[2] = new Image("/Sprites/blue-left-1.png");
        bluesprites[3] = new Image("/Sprites/blue-left-2.png");
        bluesprites[4] = new Image("/Sprites/blue-right-1.png");
        bluesprites[5] = new Image("/Sprites/blue-right-2.png");
        bluesprites[6] = new Image("/Sprites/blue-up-1.png");
        bluesprites[7] = new Image("/Sprites/blue-up-2.png");
        return bluesprites;
    }

    public static Image[] orangesprites = createOrange();

    static private Image[] createOrange() {
        orangesprites = new Image[9];
        orangesprites[0] = new Image("/Sprites/orange-down-1.png");
        orangesprites[1] = new Image("/Sprites/orange-down-2.png");
        orangesprites[2] = new Image("/Sprites/orange-left-1.png");
        orangesprites[3] = new Image("/Sprites/orange-left-2.png");
        orangesprites[4] = new Image("/Sprites/orange-right-1.png");
        orangesprites[5] = new Image("/Sprites/orange-right-2.png");
        orangesprites[6] = new Image("/Sprites/orange-up-1.png");
        orangesprites[7] = new Image("/Sprites/orange-up-2.png");
        return orangesprites;
    }

    public static Image[] pinksprites = createPink();

    static private Image[] createPink() {
        pinksprites = new Image[9];
        pinksprites[0] = new Image("/Sprites/pink-down-1.png");
        pinksprites[1] = new Image("/Sprites/pink-down-2.png");
        pinksprites[2] = new Image("/Sprites/pink-left-1.png");
        pinksprites[3] = new Image("/Sprites/pink-left-2.png");
        pinksprites[4] = new Image("/Sprites/pink-right-1.png");
        pinksprites[5] = new Image("/Sprites/pink-right-2.png");
        pinksprites[6] = new Image("/Sprites/pink-up-1.png");
        pinksprites[7] = new Image("/Sprites/pink-up-2.png");
        return pinksprites;
    }


    private static Image[] createPacman() {
        pacsprites = new Image[9];
        pacsprites[0] = new Image("/Sprites/pacman8.png");
        pacsprites[1] =  new Image("/Sprites/pacman2.png");
        pacsprites[2] =  new Image("/Sprites/pacman5.png");
        pacsprites[3] =  new Image("/Sprites/pacman3.png");
        pacsprites[4] = new Image("/Sprites/pacman4.png");
        pacsprites[5] =  new Image("/Sprites/pacman0.png");
        pacsprites[6] =  new Image("/Sprites/pacman7.png");
        pacsprites[7] =  new Image("/Sprites/pacman1.png");
        pacsprites[8] =  new Image("/Sprites/pacman6.png");
        return pacsprites;
    }
    public static Image[] scaredsprites = createScared();

    private static Image[] createScared() {
        scaredsprites = new Image[4];
        scaredsprites[0] = new Image("/Sprites/scared_blue_ghost_1.png");
        scaredsprites[1] = new Image("/Sprites/scared_blue_ghost_2.png");
        scaredsprites[2] = new Image("/Sprites/scared_white_ghost_1.png");
        scaredsprites[3] = new Image("/Sprites/scared_white_ghost_2.png");
        return scaredsprites;
    }

    public static Image[] deadsprites = createDead();

    private static Image[] createDead() {
        deadsprites = new Image[4];
        deadsprites[0] = new Image("/Sprites/dead_ghost_up.png");
        deadsprites[1] = new Image("/Sprites/dead_ghost_down.png");
        deadsprites[2] = new Image("/Sprites/dead_ghost_left.png");
        deadsprites[3] = new Image("/Sprites/dead_ghost_right.png");
        return deadsprites;
    }


    public static  Image getPicture(int i , int direction , int timing){
        switch  (i){
            case 0:
                return getPacSprite(pacsprites , direction , timing);
            case 1:
                return getSprite(redsprites , direction , timing);
            case 2:
                return getSprite(bluesprites , direction , timing);
            case 3:
                return getSprite(pinksprites , direction , timing);
            case 4:
                return getSprite(orangesprites , direction , timing);
            case 5:
                return getScared(timing);
            case 6:
                return  getEaten(direction, timing);
        }

        return null;
    }

    private static Image getEaten(int direction, int timing) {
        switch(direction){
            case 0:
                return deadsprites[1];
            case 1:
                return deadsprites[0];
            case 2:
                return deadsprites[1];
            case 3:
                return deadsprites[2];
            case 4:
                return deadsprites[3];
        }
        return null;
    }

    private static Image getScared(int timing) {
        switch(timing%2) {
            case 0:
                return scaredsprites[0];
            case 1:
                return scaredsprites[1];
        }
        return null;
    }

    private static Image getSprite(Image[] sprites , int direction, int timing) {
        switch (direction){
            case 0:
                return sprites[0];
            case 1:
                switch(timing%2){
                    case 0:
                        return sprites[6];
                    case 1:
                        return sprites[7];

                }
                break;
            case 2:
                switch(timing%2){
                    case 0:
                        return sprites[0];
                    case 1:
                        return sprites[1];
                }
                break;
            case 3:
                switch(timing%2){
                    case 0:
                        return sprites[2];
                    case 1:
                        return sprites[3];
                }
                break;
            case 4:
                switch(timing%2) {
                    case 0:
                        return sprites[4];
                    case 1:
                        return sprites[5];
                }
                break;

        }
        return null;
    }
    private static Image getPacSprite(Image[] sprites , int direction, int timing) {
        switch (direction){
            case 0:
                return sprites[0];
            case 1:
                switch(timing%2){
                    case 0:
                        return sprites[0];
                    case 1:
                        return sprites[2];

                }
                break;
            case 2:
                switch(timing%2){
                    case 0:
                        return sprites[0];
                    case 1:
                        return sprites[4];
                }
                break;
            case 3:
                switch(timing%2){
                    case 0:
                        return sprites[0];
                    case 1:
                        return sprites[6];
                }
                break;
            case 4:
                switch(timing%2) {
                    case 0:
                        return sprites[0];
                    case 1:
                        return sprites[8];
                }
                break;

        }
        return null;
    }
}
