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
    Image[] pacSprites = createPacman();
    Image[] RedSprites = createRed();

    private Image[] createRed() {
        RedSprites[0] = new Image("/Sprites/red-down-1.png");
        RedSprites[1] = new Image("/Sprites/red-down-2.png");
        RedSprites[2] = new Image("/Sprites/red-left-1.png");
        RedSprites[3] = new Image("/Sprites/red-left-2.png");
        RedSprites[4] = new Image("/Sprites/red-right-1.png");
        RedSprites[5] = new Image("/Sprites/red-right-2.png");
        RedSprites[6] = new Image("/Sprites/red-up-1.png");
        RedSprites[7] = new Image("/Sprites/red-up-2.png");
        return RedSprites;
    }


    Image[] BlueSprites = createBlue();

    private Image[] createBlue() {
        BlueSprites[0] = new Image("/Sprites/blue-down-1.png");
        BlueSprites[1] = new Image("/Sprites/blue-down-2.png");
        BlueSprites[2] = new Image("/Sprites/blue-left-1.png");
        BlueSprites[3] = new Image("/Sprites/blue-left-2.png");
        BlueSprites[4] = new Image("/Sprites/blue-right-1.png");
        BlueSprites[5] = new Image("/Sprites/blue-right-2.png");
        BlueSprites[6] = new Image("/Sprites/blue-up-1.png");
        BlueSprites[7] = new Image("/Sprites/blue-up-2.png");
        return BlueSprites;
    }

    Image[] OrangeSprites = createOrange();

    private Image[] createOrange() {
        OrangeSprites[0] = new Image("/Sprites/orange-down-1.png");
        OrangeSprites[1] = new Image("/Sprites/orange-down-2.png");
        OrangeSprites[2] = new Image("/Sprites/orange-left-1.png");
        OrangeSprites[3] = new Image("/Sprites/orange-left-2.png");
        OrangeSprites[4] = new Image("/Sprites/orange-right-1.png");
        OrangeSprites[5] = new Image("/Sprites/orange-right-2.png");
        OrangeSprites[6] = new Image("/Sprites/orange-up-1.png");
        OrangeSprites[7] = new Image("/Sprites/orange-up-2.png");
        return OrangeSprites;
    }

    Image[] PinkSprites = createPink();

    private Image[] createPink() {
        PinkSprites[0] = new Image("/Sprites/pink-down-1.png");
        PinkSprites[1] = new Image("/Sprites/pink-down-2.png");
        PinkSprites[2] = new Image("/Sprites/pink-left-1.png");
        PinkSprites[3] = new Image("/Sprites/pink-left-2.png");
        PinkSprites[4] = new Image("/Sprites/pink-right-1.png");
        PinkSprites[5] = new Image("/Sprites/pink-right-2.png");
        PinkSprites[6] = new Image("/Sprites/pink-up-1.png");
        PinkSprites[7] = new Image("/Sprites/pink-up-2.png");
        return PinkSprites;
    }


    private Image[] createPacman() {
        pacSprites[0] = new Image("/Sprites/pacman8.png");
        pacSprites[1] =  new Image("/Sprites/pacman2.png");
        pacSprites[2] =  new Image("/Sprites/pacman5.png");
        pacSprites[3] =  new Image("/Sprites/pacman3.png");
        pacSprites[4] = new Image("/Sprites/pacman4.png");
        pacSprites[5] =  new Image("/Sprites/pacman0.png");
        pacSprites[6] =  new Image("/Sprites/pacman7.png");
        pacSprites[7] =  new Image("/Sprites/pacman1.png");
        pacSprites[8] =  new Image("/Sprites/pacman6.png");
        return pacSprites;
    }


    public Pane getMonster(int i){
       /* final Image img;
        switch (GameController.getMonster(i)){
            case 0:
                img = pacSprites[0];
                break;
            case 1:
                img = pacSprites[0];
                break;
        final Image img =
        final ImageView imgV = new ImageView();
        imgV.setImage(img);
        imgV.setManaged(false);
        Pane pictureRegion = new Pane();
        imgV.fitWidthProperty().bind(pictureRegion.widthProperty());
        imgV.fitHeightProperty().bind(pictureRegion.heightProperty());
        pictureRegion.setMinWidth(width);
        pictureRegion.setMinHeight(height);
        pictureRegion.setMaxWidth(width);
        pictureRegion.setMaxHeight(height);
        pictureRegion.getChildren().add(imgV);*/
        return null;
    }
}
