package View;

import Controller.GameController;
import Model.MapChangeRequest;
import Model.Maze;
import Model.Model;
import Model.Pacman;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by thibault on 25/02/16.
 */
public class GameView extends View{

    private final double game_Width = 380;
    private final double game_Heigth = 525;

    public GameView() {
        super();
    }

    @Override
    public void start(Stage stage) {
        try {
            //TODO #1 gérer le controlleur de la création de Maze
            GameController.initialize_Game("/Sprites/terrain.txt");
            //Maze.initMapArray("/Sprites/terrain.txt");
        } catch (IOException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }

        BorderPane root = new BorderPane();
        StackPane stack = new StackPane();
        Label top_info = new Label("Get_score                                               Get_A_Life");

        GridPane g = new GridPane();
        GridPane grid = getGrid();
        stack.getChildren().add(grid);
        root.setTop(top_info);
        root.setCenter(stack);
        //GameController.startGame();
        //root = addMonster(root);



        Scene scene = new Scene(root, 300, 250);

        stage.setScene(scene);
        stage.show();
    }

    private BorderPane addMonster(BorderPane root) {
        for(int i = 0; i< Model.liste.size() ; i++){
           /* Pane p = SpriteMonster.getMonster(i);
            root.getChildren().add();*/
        }
        return null;
    }

    private GridPane getGrid() {
        GridPane grid = new GridPane();
        grid.setVgap(0);
        grid.setHgap(0);
        grid.setGridLinesVisible(false);

        int maze_Height = GameController.getMaze_Heigth();
        int maze_Width = GameController.getMaze_Width();

        for (int i = 0; i < maze_Height; i++) {
            RowConstraints con = new RowConstraints();
            con.setPercentHeight(game_Heigth / maze_Height);
            grid.getRowConstraints().add(con);
        }
        for (int x = 0; x < maze_Width; x++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(game_Width / maze_Width);
            grid.getColumnConstraints().add(col);
        }

        for (int i = 0; i < maze_Height; i++) {
            for (int j = 0; j < maze_Width; j++) {
                int maze_box = GameController.getMaze_Box(i,j);
                if (maze_box == 0 || maze_box == 4) {
                    Pane pictureRegion = getPictureRegion("/Sprites/empty.png");
                    grid.setConstraints(pictureRegion, j, i);
                    grid.add(pictureRegion, j, i);
                }
                if (maze_box == 2) {
                    Pane pictureRegion = getPictureRegion("/Sprites/pacgome1.png");
                    grid.setConstraints(pictureRegion, j, i);
                    grid.add(pictureRegion, j, i);
                }
                if (maze_box == 3) {
                    Pane pictureRegion = getPictureRegion("/Sprites/pacgome2.png");
                    grid.setConstraints(pictureRegion, j, i);
                    grid.add(pictureRegion, j, i);
                }
                if (maze_box == 1) {
                    Pane pictureRegion = GetWallPane(i, j);
                    grid.setConstraints(pictureRegion, j, i);
                    grid.add(pictureRegion, j, i);
                }
            }
        }
        return grid;
    }
    private Pane GetWallPane(int i, int x) {
        Pane pictureRegion = new Pane();
        if (isSquare(i, x)) {
            return pictureRegion = getPictureRegion("/Sprites/Corner.png");
        }
        if (isCorner(i, x)) {
            return pictureRegion = GetCornerPane(i, x);
        }
        if (isIntersection3(i, x)) {
            return pictureRegion = GetInter3Pane(i, x);
        }
        if (isIntersection4(i, x)) {
            return pictureRegion = getPictureRegion("/Sprites/Inter4.png");
        }
        if (isEndLine(i, x)) {
            return pictureRegion = GetEndLinePane(i, x);
        }
        if (isLineH(i, x)) {
            return pictureRegion = getPictureRegion("/Sprites/LineHorizontal.png");
        }
        if (isLineV(i, x)) {
            return pictureRegion = getPictureRegion("/Sprites/LineVertical.png");
        }
        return pictureRegion = getPictureRegion("/Sprites/empty.png");
    }

    private Pane GetCornerPane(int i, int x) {
        Pane pictureRegion = new Pane();
        if (isCornerBD(i, x)) {
            return pictureRegion = getPictureRegion("/Sprites/CornerBD.png");
        }
        if (isCornerHD(i, x)) {
            return pictureRegion = getPictureRegion("/Sprites/CornerHD.png");
        }
        if (isCornerBG(i, x)) {
            return pictureRegion = getPictureRegion("/Sprites/CornerBG.png");
        }
        if (isCornerHG(i, x)) {
            return pictureRegion = getPictureRegion("/Sprites/CornerHG.png");
        }
        return pictureRegion = getPictureRegion("/Sprites/empty.png");
    }

    private Pane GetInter3Pane(int i, int x) {
        Pane pictureRegion = new Pane();
        if (isCornerHG(i, x) && isLineV(i, x)) {
            return pictureRegion = getPictureRegion("/Sprites/Inter3VerticalD.png");
        }
        if (isCornerBD(i, x) && isLineV(i, x)) {
            return pictureRegion = getPictureRegion("/Sprites/Inter3VerticalG.png");
        }
        if (isCornerHG(i, x) && isLineH(i, x)) {
            return pictureRegion = getPictureRegion("/Sprites/Inter3HorizontalB.png");
        }
        if (isCornerBD(i, x) && isLineH(i, x)) {
            return pictureRegion = getPictureRegion("/Sprites/Inter3HorizontalH.png");
        }
        return pictureRegion = getPictureRegion("/Sprites/empty.png");
    }

    private Pane GetEndLinePane(int i, int x) {
        Pane pictureRegion = new Pane();
        if (isEndLineB(i, x)) {
            return pictureRegion = getPictureRegion("/Sprites/EndLineB.png");
        }
        if (isEndLineH(i, x)) {
            return pictureRegion = getPictureRegion("/Sprites/EndLineH.png");
        }
        if (isEndLineD(i, x)) {
            return pictureRegion = getPictureRegion("/Sprites/EndLineD.png");
        }
        if (isEndLineG(i, x)) {
            return pictureRegion = getPictureRegion("/Sprites/EndLineG.png");
        }
        return pictureRegion = getPictureRegion("/Sprites/empty.png");
    }

    /**
     * Teste si la case de coordonnée i,j dois etre un "Coin Haut Gauche"
     *
     * @param i coordonnée rangée
     * @param j coordonnée colonne
     * @return vrai si Coin Haut Gauche, faux sinon
     */
    private boolean isCornerHG(int i, int j) {
        boolean CornerHG = false;
        if (((i + 1 < GameController.getMaze_Heigth()) && (j + 1 < GameController.getMaze_Width()))) {
            if ((GameController.getMaze_Box(i + 1,j) == 1) && (GameController.getMaze_Box(i,j + 1) == 1)) {
                CornerHG = true;
            }
        }
        return CornerHG;
    }

    /**
     * Teste si la case de coordonnée i,j dois etre un "Coin Haut Droit"
     *
     * @param i coordonnée rangée
     * @param j coordonnée colonne
     * @return vrai si Coin Haut Droit, faux sinon
     */
    private boolean isCornerHD(int i, int j) {
        boolean CornerHD = false;
        if (((i + 1 < GameController.getMaze_Heigth()) && (j - 1 > -1))) {
            if ((GameController.getMaze_Box(i + 1,j) == 1) && (GameController.getMaze_Box(i,j - 1) == 1)) {
                CornerHD = true;
            }
        }
        return CornerHD;
    }

    /**
     * Teste si la case de coordonnée i,j dois etre un "Coin Bas Droit"
     *
     * @param i coordonnée rangée
     * @param j coordonnée colonne
     * @return vrai si Coin Bas Droit, faux sinon
     */
    private boolean isCornerBD(int i, int j) {
        boolean CornerBD = false;
        if (((i - 1 > -1) && (j - 1 > -1))) {
            if ((GameController.getMaze_Box(i - 1,j) == 1) && (GameController.getMaze_Box(i,j - 1) == 1)) {
                CornerBD = true;
            }
        }
        return CornerBD;
    }

    /**
     * Teste si la case de coordonnée i,j dois etre un "Coin Bas Gauche"
     *
     * @param i coordonnée rangée
     * @param j coordonnée colonne
     * @return vrai si Coin Bas Gauche, faux sinon
     */
    private boolean isCornerBG(int i, int j) {
        boolean CornerBG = false;
        if (((i - 1 > -1) && (j + 1 < GameController.getMaze_Width()))) {
            if ((Maze.plateau[i - 1][j] == 1) && (Maze.plateau[i][j + 1] == 1)) {
                CornerBG = true;
            }
        }
        return CornerBG;
    }

    /**
     * Teste si la case de coordonnée i,x dois etre une "Line Horizontal"
     *
     * @param i coordonnée rangée
     * @param x coordonnée colonne
     * @return vrai si Line Horizontal, faux sinon
     */
    private boolean isLineH(int i, int x) {
        boolean LineHorizontal = false;
        if (((x - 1 > -1) && (x + 1 < Maze.plateau[0].length))) {
            if ((Maze.plateau[i][x - 1] == 1) && (Maze.plateau[i][x + 1] == 1)) {
                LineHorizontal = true;
            }
        }
        return LineHorizontal;
    }

    /**
     * Teste si la case de coordonnée i,x dois etre une "Line Vertical"
     *
     * @param i coordonnée rangée
     * @param x coordonnée colonne
     * @return vrai si Line Vertical, faux sinon
     */
    private boolean isLineV(int i, int x) {
        boolean LineVertical = false;
        if (((i - 1 > -1) && (i + 1 < Maze.plateau.length))) {
            if ((Maze.plateau[i - 1][x] == 1) && (Maze.plateau[i + 1][x] == 1)) {
                LineVertical = true;
            }
        }
        return LineVertical;
    }

    /**
     * Teste si la case de coordonnée i,x est une fin de ligne direction haut"
     *
     * @param i coordonnée rangée
     * @param x coordonnée colonne
     * @return vrai si fin de ligne haut, faux sinon
     */
    private boolean isEndLineH(int i, int x) {
        boolean EndLineH = false;
        if (i + 1 < Maze.plateau.length) {
            if (Maze.plateau[i + 1][x] == 1) {
                EndLineH = true;
            }
        }
        return EndLineH;
    }

    /**
     * Teste si la case de coordonnée i,x est une fin de ligne direction bas"
     *
     * @param i coordonnée rangée
     * @param x coordonnée colonne
     * @return vrai si fin de ligne bas, faux sinon
     */
    private boolean isEndLineB(int i, int x) {
        boolean EndLineB = false;
        if (i - 1 > -1) {
            if (Maze.plateau[i - 1][x] == 1) {
                EndLineB = true;
            }
        }
        return EndLineB;
    }

    /**
     * Teste si la case de coordonnée i,x est une fin de ligne direction gauche"
     *
     * @param i coordonnée rangée
     * @param x coordonnée colonne
     * @return vrai si fin de ligne gauche, faux sinon
     */
    private boolean isEndLineG(int i, int x) {
        boolean EndLineG = false;
        if (x - 1 > -1) {
            if (Maze.plateau[i][x - 1] == 1) {
                EndLineG = true;
            }
        }
        return EndLineG;
    }

    /**
     * Teste si la case de coordonnée i,x est une fin de ligne direction droite"
     *
     * @param i coordonnée rangée
     * @param x coordonnée colonne
     * @return vrai si fin de ligne droite, faux sinon
     */
    private boolean isEndLineD(int i, int x) {
        boolean EndLineD = false;
        if (x + 1 < Maze.plateau[0].length) {
            if (Maze.plateau[i][x + 1] == 1) {
                EndLineD = true;
            }
        }
        return EndLineD;
    }

    /**
     * Teste si la case de coordonnée i,x est un Coin, sans précisé son type"
     *
     * @param i coordonnée rangée
     * @param x coordonnée colonne
     * @return vrai si Coin, faux sinon
     */
    public boolean isCorner(int i, int x) {
        return (Maze.checkWall(i, x, 1) == 2 && !isLineV(i, x) && !isLineH(i, x));

    }

    /**
     * Teste si la case de coordonnée i,x est une intersection de 3 ligne, sans
     * précisé son type, on le calculera en combinant des tests de coin et de
     * ligne"
     *
     * @param i coordonnée rangée
     * @param x coordonnée colonne
     * @return vrai si Intersection 3, faux sinon
     */
    public boolean isIntersection3(int i, int x) {
        return (Maze.checkWall(i, x, 1) == 3);
    }

    /**
     * Teste si la case de coordonnée i,x est une intersection de 4 ligne, il
     * n'y a qu'un seul type possible"
     *
     * @param i coordonnée rangée
     * @param x coordonnée colonne
     * @return vrai si intersection 4, faux sinon
     */
    public boolean isIntersection4(int i, int x) {
        return (Maze.checkWall(i, x, 1) == 4);
    }

    /**
     * Teste si la case de coordonnée i,x est une "fin de ligne" sans précisé
     * son type" A IMPLEMENTER 4 DIRECTION POSSIBLE DE ENDLINE
     *
     * @param i coordonnée rangée
     * @param x coordonnée colonne
     * @return vrai si "fin de ligne", faux sinon
     */
    public boolean isEndLine(int i, int x) {
        return (Maze.checkWall(i, x, 1) == 1);
    }

    public boolean isSquare(int i, int x) {
        return (Maze.checkWall(i, x, 1) == 0);
    }

    /**
     * Construit une pane contenant l'image fourni en paramètre pour insertion
     * dans la gridpane
     *
     * @param file path de l'image
     * @return pane contenant l'image file
     */
    private Pane getPictureRegion(String file) {
        final Image img = new Image(View.class.getResourceAsStream(file));
        final ImageView imgV = new ImageView();
        imgV.setImage(img);
        Pane pictureRegion = new Pane();
        imgV.fitWidthProperty().bind(pictureRegion.widthProperty());
        imgV.fitHeightProperty().bind(pictureRegion.heightProperty());
        pictureRegion.getChildren().add(imgV);
        return pictureRegion;
    }

    /**
     * Chance une cellule de gridpane en fonction de la liste ChangeQueue
     * contenue dans pacman
     *
     * @param grid GridPane to change
     */
    private void updateMap(GridPane grid) {
        while (!(Pacman.ChangeQueue.isEmpty())) {
            MapChangeRequest change = (MapChangeRequest) Pacman.ChangeQueue.pop();
            Pane pictureRegion = getPictureRegion(change.getSprite_change());
            grid.setConstraints(pictureRegion, change.getCase_col(), change.getCase_row());
            grid.add(pictureRegion, change.getCase_col(), change.getCase_row());
        }
    }

    private void deleteNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                gridPane.getChildren().remove(node);
            }
        }
    }

}
