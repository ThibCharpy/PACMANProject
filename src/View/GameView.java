package View;

import Controller.GameController;
import Model.MapChangeRequest;
import Model.Model;
import Model.Score;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;

import static javafx.scene.text.FontWeight.BOLD;

/**
 * Created by thibault on 25/02/16.
 */
public class GameView extends View {

    private final double game_Width = 380;
    private final double game_Heigth = 525;
    private Timeline timeline2;
    private Timeline timeline;
    private Timeline timeline3;
    private Timeline timeline4;

    Timeline[] timeline_tab;
    StackPane root;
    StackPane break_Root;
    StackPane break_Screen;
    StackPane record_Screen;
    StackPane record_Root;
    private Button btn_Submit;
    private Button btn_Resume;
    private Button btn_Menu;
    private Button btn_Quit;
    double btn_Width = 250;
    double btn_Height = 475;
    boolean onBreak = false;
    boolean resume = false;
    private String name;
    private String path;
    private String map;
    private int ghostSpeed;
    public Score gamescore;
    public HBox part_Lives;

    private Timeline timelineGhost;
    private Stage stage_save;
    private Stage stage;
    GameController c;

    /**
     * Constructeur de la classe GameView
     *
     * @param path Path pour le fichier de Score.
     * @param map Path pour le fichier txt contenant la carte à afficher
     */
    public GameView(String path, String map) {
        super();

        timeline_tab = new Timeline[6];
        btn_Resume = new Button("Resume");
        btn_Menu = new Button("Menu");
        btn_Quit = new Button("Quit");
        btn_Submit = new Button("Submit");
        this.map = map;
        this.path = path;
        this.ghostSpeed = 18;
        gamescore = new Score(null, 0);
        name = "";
    }

    /**
     * Fonction gérant la mort du pacman et toute les actions associé, gestion
     * des sons, reset des positions, retour au menu si plus de vie.
     */
    public void checkDeath() {
        if (GameController.PacDead) {
            c.soundLibrary.audio_background5.stop();
            c.soundLibrary.audio_alt_powermode.stop();
            c.soundLibrary.play(c.soundLibrary.bool_death, c.soundLibrary.audio_death, 0.65);
            timeline_tab[0].stop();
            timeline_tab[2].stop();
            timeline_tab[4].stop();
            timeline_tab[5].stop();
            c.DeathImage(GameController.cmpDeath);
            GameController.cmpDeath++;
            if (GameController.cmpDeath == 12) {
                timeline_tab[1].stop();
                GameController.PacDead = false;
                GameController.cmpDeath = 0;
                part_Lives.getChildren().remove(c.lifeLeft);
                c.lifeLeft--;
                if (c.lifeLeft > 0) {
                    c.resetPosition();
                    c.setMonsterPosition();
                    c.updateImage();
                    c.soundLibrary.play(c.soundLibrary.bool_introsong, c.soundLibrary.audio_introsong, 0.65);
                    timeline_tab[3].play();
                } else {
                    record_Root = new StackPane();
                    record_Root.getChildren().add(record_Screen);
                    root.getChildren().add(record_Root);
                }
            }

        }
    }

    /**
     * Fonction gérant le redémarrage, changement de carte, changement de
     * terrain entre les niveau.
     */
    public void checkRestartNeed() {
        if (GameController.restartNeeded) {
            GameController.restartNeeded = false;
            for (int i = 0; i < 5; i++) {
                Model m = c.list.get(c.p[i]);
                m = null;
                c.p[i] = null;
            }
            c.soundLibrary.audio_alt_powermode.stop();
            c.list.clear();
            timeline_tab[0].stop();
            timeline_tab[1].stop();
            timeline_tab[2].stop();
            timeline_tab[4].stop();
            timeline_tab[5].stop();
            c.soundLibrary.audio_background5.stop();
            if (this.map.equals("/Sprites/terrain.txt")) {
                this.map = "/Sprites/terrain2.txt";
                if (this.ghostSpeed > 12) {
                    this.ghostSpeed--;
                }
            } else if (this.map.equals("/Sprites/terrain2.txt")) {
                this.map = "/Sprites/terrain3.txt";
                if (this.ghostSpeed > 12) {
                    this.ghostSpeed--;
                }
            } else if (this.map.equals("/Sprites/terrain3.txt")) {
                this.map = "/Sprites/terrain.txt";
                if (this.ghostSpeed > 12) {
                    this.ghostSpeed--;
                }
            }
            start(stage_save);
        }
    }

    /**
     * Fonction gérant l'affichage et les timelines nécéssaire au fonctionnement
     * du jeu.
     *
     * @param stage Stage sur lequel afficher les informations.
     */
    @Override
    public void start(Stage stage) {
        c = new GameController(this);
        HomeView hv = new HomeView();

        btn_Resume.setMaxSize(btn_Width, btn_Height);
        btn_Resume.setOnAction(event -> {
            resume = true;
            exit_To_Break(null, root);
        });
        btn_Menu.setMaxSize(btn_Width, btn_Height);
        btn_Menu.setOnAction(event -> {
            c.soundLibrary.audio_background5.stop();
            c.btn_Action(stage, hv);
        });
        btn_Quit.setMaxSize(btn_Width, btn_Height);
        btn_Quit.setOnAction(event -> stage.close());

        VBox button_box = new VBox(50);
        button_box.setAlignment(Pos.CENTER);
        button_box.getChildren().add(btn_Resume);
        button_box.getChildren().add(btn_Menu);
        button_box.getChildren().add(btn_Quit);

        Rectangle break_Screen_Menu_Background = new Rectangle(game_Width - 80, game_Heigth - 80);
        break_Screen_Menu_Background.setFill(Color.GREY);
        StackPane break_Screen_Menu_Root = new StackPane();
        break_Screen_Menu_Root.getChildren().add(break_Screen_Menu_Background);
        break_Screen_Menu_Root.getChildren().add(button_box);

        Rectangle break_Screen_Background = new Rectangle(game_Width, game_Heigth);
        break_Screen_Background.setFill(Color.BLACK);

        break_Screen = new StackPane();
        break_Screen.getChildren().add(break_Screen_Background);
        break_Screen.getChildren().add(break_Screen_Menu_Root);

        TextField record = new TextField();
        Label record_Label = new Label("Enter your Name:");
        record_Label.setFont(Font.font("Arial", BOLD, 14));

        HBox record_Text_Box = new HBox(20);
        record_Text_Box.setAlignment(Pos.CENTER);
        record_Text_Box.getChildren().add(record_Label);
        record_Text_Box.getChildren().add(record);

        btn_Submit.setMaxSize(btn_Width - 160, btn_Height);
        btn_Submit.setOnAction(event -> {
            name = record.getText();
            try {
                c.saveGameScore(name, path);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            hv.start(stage);
        });

        VBox record_Box = new VBox(20);
        record_Box.setAlignment(Pos.CENTER);
        record_Box.getChildren().add(record_Text_Box);
        record_Box.getChildren().add(btn_Submit);

        StackPane record_Screen_Menu = new StackPane();
        record_Screen_Menu.getChildren().add(break_Screen_Menu_Background);
        record_Screen_Menu.getChildren().add(record_Box);

        record_Screen = new StackPane();
        record_Screen.getChildren().add(break_Screen_Background);
        record_Screen.getChildren().add(record_Screen_Menu);

        try {
            GameController.initialize_Game(map);
        } catch (IOException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.soundLibrary.play(c.soundLibrary.bool_introsong, c.soundLibrary.audio_introsong, 0.65);
        this.stage_save = stage;

        HBox part_Score = new HBox();
        part_Score.setAlignment(Pos.TOP_LEFT);
        Text text_Score = new Text("Score: " + 0 + "                  ");
        text_Score.setFont(Font.font("Arial", BOLD, 18));
        part_Score.getChildren().add(text_Score);

        part_Lives = new HBox();
        part_Lives.setAlignment(Pos.TOP_RIGHT);
        Image lives_one = new Image("/Sprites/pacman1.png");
        ImageView lo = new ImageView(lives_one);
        Image lives_two = new Image("/Sprites/pacman1.png");
        ImageView ltw = new ImageView(lives_two);
        Image lives_three = new Image("/Sprites/pacman1.png");
        ImageView lth = new ImageView(lives_three);
        Text text_Lives = new Text("                  Lives: ");
        text_Lives.setFont(Font.font("Arial", BOLD, 18));
        part_Lives.getChildren().add(text_Lives);
        part_Lives.getChildren().add(lo);
        part_Lives.getChildren().add(ltw);
        part_Lives.getChildren().add(lth);

        HBox toolbar_content = new HBox();
        toolbar_content.setAlignment(Pos.CENTER);
        toolbar_content.getChildren().add(part_Score);
        toolbar_content.getChildren().add(part_Lives);

        Rectangle toolbar_background = new Rectangle(getWindow_Width(), getWindow_Height() / 20);
        toolbar_background.setFill(Color.GREY);

        StackPane toolbar = new StackPane();
        toolbar.getChildren().add(toolbar_background);
        toolbar.getChildren().add(toolbar_content);

        BorderPane maze = new BorderPane();
        StackPane stack = new StackPane();
        stack.setMinWidth(game_Width);
        stack.setMinHeight(game_Heigth);
        stack.setMaxWidth(game_Width);
        stack.setMaxHeight(game_Heigth);
        // Label top_info = new Label("Get_score                                               Get_A_Life");

        GridPane grid = getGrid();
        stack.getChildren().add(grid);
        maze.setTop(toolbar);
        maze.setCenter(stack);

        c.startGame();
        stack = c.implementPane(stack);
        c.getMonsterPosition(stack);

        root = new StackPane();
        Rectangle background_root = new Rectangle(getWindow_Width(), getWindow_Height());
        background_root.setFill(Color.BLACK);
        root.getChildren().add(background_root);
        root.getChildren().add(maze);

        Scene scene = new Scene(root, 300, 250);

        scene.setOnKeyPressed(event -> {
            if (!onBreak) {
                enter_To_Break(event.getCode(), root);
                c.pacmanMovement(event.getCode());
            } else {
                exit_To_Break(event.getCode(), root);
            }
        });
        scene.setOnMouseClicked(event -> {
            c.mouvementByMouse(event);
        });
        timeline_tab[0] = new Timeline(new KeyFrame(
                Duration.millis(12),
                ae -> {
                    /*if (c.list.get(c.p[1]).afraid() || c.list.get(c.p[1]).eaten()
                            || c.list.get(c.p[2]).afraid() || c.list.get(c.p[2]).eaten() 
                            || c.list.get(c.p[3]).afraid() || c.list.get(c.p[3]).eaten() 
                            || c.list.get(c.p[4]).afraid() || c.list.get(c.p[4]).eaten()) {
                        c.ghostBehavior();
                    }*/
                    c.pacmovement();
                    c.deadBehavior();
                    c.findContact();
                    c.setMonsterPosition();
                    try {
                        updateMap(grid);
                    } catch (LineUnavailableException ex) {
                        Logger.getLogger(GameView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    text_Score.setText("Score: " + c.getSc().getScore().getRight().intValue() + "                  ");
                }));
        timeline_tab[0].setCycleCount(Animation.INDEFINITE);

        timeline_tab[4] = new Timeline(new KeyFrame(
                Duration.millis(16),
                ae -> {
                    c.deadMovement();
                    try {
                        updateMap(grid);
                    } catch (LineUnavailableException ex) {
                        Logger.getLogger(GameView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }));
        timeline_tab[4].setCycleCount(Animation.INDEFINITE);

        timeline_tab[1] = new Timeline(new KeyFrame(
                Duration.millis(150),
                ae -> {
                    this.checkDeath();
                    this.checkRestartNeed();
                    c.updateImage();
                    c.ghostBehavior();
                    c.createBonus();
                    /*if (!(c.list.get(c.p[1]).afraid() || c.list.get(c.p[1]).eaten()
                            || c.list.get(c.p[2]).afraid() || c.list.get(c.p[2]).eaten()
                            || c.list.get(c.p[3]).afraid() || c.list.get(c.p[3]).eaten()
                            || c.list.get(c.p[4]).afraid() || c.list.get(c.p[4]).eaten())) {
                        c.ghostBehavior();
                    }*/

                }));
        timeline_tab[1].setCycleCount(Animation.INDEFINITE);

        timeline_tab[2] = new Timeline(new KeyFrame(
                Duration.millis(290000),
                ae -> {
                    c.soundLibrary.playOverride(c.soundLibrary.bool_background5, c.soundLibrary.audio_background5, 0.35);
                }));
        timeline_tab[2].setCycleCount(Animation.INDEFINITE);

        timeline_tab[3] = new Timeline(new KeyFrame(
                Duration.millis(4700),
                ae -> {
                    timeline_tab[2].play();
                    timeline_tab[1].play();
                    timeline_tab[0].play();
                    timeline_tab[5].play();
                    timeline_tab[4].play();
                    c.soundLibrary.playOverride(c.soundLibrary.bool_background5, c.soundLibrary.audio_background5, 0.35);
                }));
        timeline_tab[3].setCycleCount(0);
        timeline_tab[3].play();
        timeline_tab[5] = new Timeline(new KeyFrame(
                Duration.millis(this.ghostSpeed),
                ae -> {
                    c.movement();
                    c.findContact();
                    c.setMonsterPosition();
                }));
        timeline_tab[5].setCycleCount(Animation.INDEFINITE);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Fonction déclenchant l'entrée en mode pause.
     *
     * @param k code touche.
     * @param s StackPane sur laquelle affiché le menu de pause.
     */
    public void enter_To_Break(KeyCode k, StackPane s) {
        if (k == KeyCode.ESCAPE) {
            onBreak = true;
            Rectangle r = new Rectangle(getWindow_Width(), getWindow_Height());
            r.setFill(Color.rgb(50, 50, 50, 0.75));
            GaussianBlur gb = new GaussianBlur();
            r.setEffect(gb);
            break_Root = new StackPane();
            break_Root.getChildren().add(r);
            break_Root.getChildren().add(break_Screen);
            s.getChildren().add(break_Root);
            for (int i = 0; i < timeline_tab.length - 1; i++) {
                timeline_tab[i].stop();
            }
        }
    }

    /**
     * Fonction déclenchant la sortie du mode pause.
     *
     * @param k code touche.
     * @param s StackPane sur laquelle retirer le menu de pause.
     */
    public void exit_To_Break(KeyCode k, StackPane s) {
        if (k == KeyCode.ESCAPE || resume) {
            onBreak = false;
            s.getChildren().remove(break_Root);
            for (int i = 0; i < timeline_tab.length; i++) {
                timeline_tab[i].play();
            }
            resume = false;
        }
    }

    /**
     * Fonction créant la Grid correspondant au labyrinthe dans lequel pacman
     * évolue.
     *
     * @return Grid avec tout les sprites adapté par case de la grid.
     */
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
                int maze_box = GameController.getMaze_Box(i, j);
                if (maze_box == 0 || maze_box == 4 || maze_box == 5) {
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
        c.setGRID_SIZE_X(game_Width);
        c.setGRID_SIZE_Y(game_Heigth);
        c.initialize_list();
        return grid;
    }

    /**
     * Retourne le sprites correspondant au coordonnées en paramètre, détermine
     * le type de Sprite nécéssaire.
     *
     * @param i Coordonnée i
     * @param x Coordonnée x
     * @return Sprites dans le bon sens, en fonction des coordonnées
     */
    private Pane GetWallPane(int i, int x) {
        if (isSquare(i, x)) {
            return getPictureRegion("/Sprites/Corner.png");
        }
        if (isCorner(i, x)) {
            return GetCornerPane(i, x);
        }
        if (isIntersection3(i, x)) {
            return GetInter3Pane(i, x);
        }
        if (isIntersection4(i, x)) {
            return getPictureRegion("/Sprites/Inter4.png");
        }
        if (isEndLine(i, x)) {
            return GetEndLinePane(i, x);
        }
        if (isLineH(i, x)) {
            return getPictureRegion("/Sprites/LineHorizontal.png");
        }
        if (isLineV(i, x)) {
            return getPictureRegion("/Sprites/LineVertical.png");
        }
        return getPictureRegion("/Sprites/empty.png");
    }

    /**
     * Retourne le CornerSprite correspondant au coordonnées en paramètre
     *
     * @param i Coordonnée i
     * @param x Coordonnée x
     * @return Sprites dans le bon sens, en fonction des coordonnées
     */
    private Pane GetCornerPane(int i, int x) {
        if (isCornerBD(i, x)) {
            return getPictureRegion("/Sprites/CornerBD.png");
        }
        if (isCornerHD(i, x)) {
            return getPictureRegion("/Sprites/CornerHD.png");
        }
        if (isCornerBG(i, x)) {
            return getPictureRegion("/Sprites/CornerBG.png");
        }
        if (isCornerHG(i, x)) {
            return getPictureRegion("/Sprites/CornerHG.png");
        }
        return getPictureRegion("/Sprites/empty.png");
    }

    /**
     * Retourne le Inter3Sprite correspondant au coordonnées en paramètre
     *
     * @param i Coordonnée i
     * @param x Coordonnée x
     * @return Sprites dans le bon sens, en fonction des coordonnées
     */
    private Pane GetInter3Pane(int i, int x) {
        if (isCornerHG(i, x) && isLineV(i, x)) {
            return getPictureRegion("/Sprites/Inter3VerticalD.png");
        }
        if (isCornerBD(i, x) && isLineV(i, x)) {
            return getPictureRegion("/Sprites/Inter3VerticalG.png");
        }
        if (isCornerHG(i, x) && isLineH(i, x)) {
            return getPictureRegion("/Sprites/Inter3HorizontalB.png");
        }
        if (isCornerBD(i, x) && isLineH(i, x)) {
            return getPictureRegion("/Sprites/Inter3HorizontalH.png");
        }
        return getPictureRegion("/Sprites/empty.png");
    }

    /**
     * Retourne le EndLineSprite correspondant au coordonnées en paramètre
     *
     * @param i Coordonnée i
     * @param x Coordonnée x
     * @return Sprites dans le bon sens, en fonction des coordonnées
     */
    private Pane GetEndLinePane(int i, int x) {
        if (isEndLineB(i, x)) {
            return getPictureRegion("/Sprites/EndLineB.png");
        }
        if (isEndLineH(i, x)) {
            return getPictureRegion("/Sprites/EndLineH.png");
        }
        if (isEndLineD(i, x)) {
            return getPictureRegion("/Sprites/EndLineD.png");
        }
        if (isEndLineG(i, x)) {
            return getPictureRegion("/Sprites/EndLineG.png");
        }
        return getPictureRegion("/Sprites/empty.png");
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
            if ((GameController.getMaze_Box(i + 1, j) == 1) && (GameController.getMaze_Box(i, j + 1) == 1)) {
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
            if ((GameController.getMaze_Box(i + 1, j) == 1) && (GameController.getMaze_Box(i, j - 1) == 1)) {
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
            if ((GameController.getMaze_Box(i - 1, j) == 1) && (GameController.getMaze_Box(i, j - 1) == 1)) {
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
            if ((GameController.getMaze_Box(i - 1, j) == 1) && (GameController.getMaze_Box(i, j + 1) == 1)) {
                CornerBG = true;
            }
        }
        return CornerBG;
    }

    /**
     * Teste si la case de coordonnée i,j dois etre une "Line Horizontal"
     *
     * @param i coordonnée rangée
     * @param j coordonnée colonne
     * @return vrai si Line Horizontal, faux sinon
     */
    private boolean isLineH(int i, int j) {
        boolean LineHorizontal = false;
        if (((j - 1 > -1) && (j + 1 < GameController.getMaze_Width()))) {
            if ((GameController.getMaze_Box(i, j - 1) == 1) && (GameController.getMaze_Box(i, j + 1) == 1)) {
                LineHorizontal = true;
            }
        }
        return LineHorizontal;
    }

    /**
     * Teste si la case de coordonnée i,j dois etre une "Line Vertical"
     *
     * @param i coordonnée rangée
     * @param j coordonnée colonne
     * @return vrai si Line Vertical, faux sinon
     */
    private boolean isLineV(int i, int j) {
        boolean LineVertical = false;
        if (((i - 1 > -1) && (i + 1 < GameController.getMaze_Heigth()))) {
            if ((GameController.getMaze_Box(i - 1, j) == 1) && (GameController.getMaze_Box(i + 1, j) == 1)) {
                LineVertical = true;
            }
        }
        return LineVertical;
    }

    /**
     * Teste si la case de coordonnée i,j est une fin de ligne direction haut"
     *
     * @param i coordonnée rangée
     * @param j coordonnée colonne
     * @return vrai si fin de ligne haut, faux sinon
     */
    private boolean isEndLineH(int i, int j) {
        boolean EndLineH = false;
        if (i + 1 < GameController.getMaze_Heigth()) {
            if (GameController.getMaze_Box(i + 1, j) == 1) {
                EndLineH = true;
            }
        }
        return EndLineH;
    }

    /**
     * Teste si la case de coordonnée i,j est une fin de ligne direction bas"
     *
     * @param i coordonnée rangée
     * @param j coordonnée colonne
     * @return vrai si fin de ligne bas, faux sinon
     */
    private boolean isEndLineB(int i, int j) {
        boolean EndLineB = false;
        if (i - 1 > -1) {
            if (GameController.getMaze_Box(i - 1, j) == 1) {
                EndLineB = true;
            }
        }
        return EndLineB;
    }

    /**
     * Teste si la case de coordonnée i,j est une fin de ligne direction gauche"
     *
     * @param i coordonnée rangée
     * @param j coordonnée colonne
     * @return vrai si fin de ligne gauche, faux sinon
     */
    private boolean isEndLineG(int i, int j) {
        boolean EndLineG = false;
        if (j - 1 > -1) {
            if (GameController.getMaze_Box(i, j - 1) == 1) {
                EndLineG = true;
            }
        }
        return EndLineG;
    }

    /**
     * Teste si la case de coordonnée i,j est une fin de ligne direction droite"
     *
     * @param i coordonnée rangée
     * @param j coordonnée colonne
     * @return vrai si fin de ligne droite, faux sinon
     */
    private boolean isEndLineD(int i, int j) {
        boolean EndLineD = false;
        if (j + 1 < GameController.getMaze_Width()) {
            if (GameController.getMaze_Box(i, j + 1) == 1) {
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
        return (GameController.getMaze_CountNeighbour(i, x, 1) == 2 && !isLineV(i, x) && !isLineH(i, x));

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
        return (GameController.getMaze_CountNeighbour(i, x, 1) == 3);
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
        return (GameController.getMaze_CountNeighbour(i, x, 1) == 4);
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
        return (GameController.getMaze_CountNeighbour(i, x, 1) == 1);
    }

    public boolean isSquare(int i, int x) {
        return (GameController.getMaze_CountNeighbour(i, x, 1) == 0);
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
    private void updateMap(GridPane grid) throws LineUnavailableException {
        LinkedList ChangeQueue = c.getChangeQueue();
        while (!(ChangeQueue.isEmpty())) {
            MapChangeRequest change = (MapChangeRequest) ChangeQueue.pop();
            if ("Gomme".equals(change.getType())) {
                c.soundLibrary.play(c.soundLibrary.bool_eat_gomme, c.soundLibrary.audio_eat_gomme, 0.6);
            } else if ("BigGomme".equals(change.getType())) {
                c.soundLibrary.play(c.soundLibrary.bool_eat_gomme, c.soundLibrary.audio_eat_gomme, 0.6);
                c.soundLibrary.audio_alt_powermode.setRate(0.79);
                c.soundLibrary.stopAndPlay(c.soundLibrary.bool_alt_powermode, c.soundLibrary.audio_alt_powermode, 0.8);
            }
            Pane pictureRegion = getPictureRegion(change.getSprite_change());
            grid.setConstraints(pictureRegion, change.getCase_col(), change.getCase_row());
            grid.add(pictureRegion, change.getCase_col(), change.getCase_row());
        }
    }
}
