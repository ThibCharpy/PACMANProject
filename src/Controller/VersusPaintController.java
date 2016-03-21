package Controller;

import Model.*;
import View.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by Charpignon Thibault on 18/03/2016.
 */
public class VersusPaintController extends Controller{

    public Map<Pane, Monster> list;
    public static boolean restartNeeded = false;
    public static boolean PacDead = false;
    public static int cmpDeath = 0;
    public Pane[] p;
    public static Score sc;
    public int lifeLeft = 3;
    private int score_for_life = 0;
    int timing = 0;
    public SoundLibrary soundLibrary;
    String state = "idle";
    int timerFear = 0;
    boolean bonusExiste = false;


    /**
     * Constructeur de la classe VersusPaintController.
     *
     * @param v View associer au Controller
     */
    public VersusPaintController(View v) {
        super(v);
        list = new HashMap<>();
        soundLibrary = new SoundLibrary();
        p = new Pane[2];
        Model.controller = this;
        sc = new Score("", 0);
    }

    /**
     * Getter de ChangeQueue.
     *
     * @return ChangeQueue contenue dans Pacman
     */
    public LinkedList getChangeQueue() {
        Model m = list.get(p[0]);
        return ((Pacman) m).ChangeQueue;
    }

    public static double getHG() {
        return Model.HG;
    }

    public static double getLG() {
        return Model.LG;
    }

    /**
     * Fonction utilisée pour initialiser les monstres sur la carte.
     */
    public void startGame() {
        Pacman pacman = new Pacman(185, 363, 13, 1, 0);
        Pacgirl pacgirl = new Pacgirl(185, 145, 13, 1, 0);
        Pane pPacman = getMonsterPane(pacman);
        Pane pPacgirl = getMonsterPane(pacgirl);
        list.put(pPacman, pacman);
        list.put(pPacgirl, pacgirl);
        p[0] = pPacman;
        p[1] = pPacgirl;
    }


    /**
     * Lance la réinitialisation des positions des monstres.
     */
    public void resetPosition() {
        for (int i = 0; i < 2; i++) {
            list.get(p[i]).reset();
        }
    }

    /**
     * Fonction de sauvegarde du score à l'intérieur d'un fichier texte en
     * fonction du nom du joueur.
     *
     * @param name Nom du joueur.
     * @param path Chemin vers le fichier de sauvegarde des scores.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void saveGameScore(String name, String path) throws IOException, ClassNotFoundException {
        File f = new File(path);
        Score_Tab st;
        sc.setScore_Name(name);
        if (f.exists()) {
            System.out.println("Fichier trouvé => ouverture fichier");
            st = new Score_Tab(path);
        } else {
            st = new Score_Tab();
            Path myFile = Paths.get(path);
            Files.createFile(myFile);
        }
        st.add_Score(sc);
        st.writeScore_Tab(path);
    }

    /**
     * Setter de la position X d'un monstre.
     *
     * @param i numero du monstre
     * @param x position X du monstre
     */
    public void setMonsterX(int i, int x) {
        (list.get(p[i]).x) = x;
    }

    /**
     * Setter de la position Y d'un monstre.
     *
     * @param i numero du monstre
     * @param y position Y du monstre
     */
    public void setMonsterY(int i, int y) {
        (list.get(p[i]).y) = y;
    }

    /**
     * Getter de la taille de la liste de monstre.
     *
     * @return
     */
    public int sizeOfList() {
        return list.size();
    }

    /**
     * Retourne un entier représentant le type de monstre passé en paramètre.
     *
     * @param m Monstre (Pacman / Fantomes).
     * @return entier correspondant au type de monstre.
     */
    public int getMonsterType(Monster m) {
        if (m.afraid()) {
            return 5;
        }
        if (m.eaten()) {
            return 6;
        }
        if (m instanceof Pacman) {
            return 0;
        }
        if (m instanceof Pacgirl) {
            return 7;
        }
        if (m instanceof RedGhost) {
            return 1;
        }
        if (m instanceof PinkGhost) {
            return 2;
        }
        if (m instanceof BlueGhost) {
            return 3;
        }
        if (m instanceof OrangeGhost) {
            return 4;
        } else {
            return -1;
        }
    }

    /**
     * Met à jour le score en fonction d'un entier i.
     *
     * @param i entier i à ajouter au score.
     */
    public void updateScore(int i) {
        sc.setScore_Score(sc.getScore_Score() + i);
        score_for_life += i;
        if (score_for_life >= 10000) {
            lifeLeft++;
            this.soundLibrary.audio_extralife.play(1.2);
            score_for_life = score_for_life - 10000;
        }
    }

    /**
     * Getter de score.
     *
     * @return score.
     */
    public Score getSc() {
        return sc;
    }

    /**
     * Initialise un tableau static contenant une réprésentation du fichier
     * texte contenant la carte.
     *
     * @param path_field chemin vers le fichier contenant la carte.
     * @throws IOException
     */
    public static void initialize_Game(String path_field) throws IOException {
        Maze.initMapArray(path_field);
    }

    /**
     * Getter de largeur du tableau du terrain.
     *
     * @return largeur du tableau.
     */
    public static int getMaze_Width() {
        return Maze.plateau[0].length;
    }

    /**
     * Getter de hauteur du tableau du terrain.
     *
     * @return hauteur du tableau.
     */
    public static int getMaze_Heigth() {
        return Maze.plateau.length;
    }

    /**
     * Getter de contenu du tableau du terrain.
     *
     * @param i Coordonnée i.
     * @param j Coordonnée i.
     * @return Information contenu dans la case [i][j].
     */
    public static int getMaze_Box(int i, int j) {
        return Maze.plateau[i][j];
    }

    /**
     * Getter du nombre de voisin d'une case du tableau, un voisin étant toute
     * case qui n'est pas un mur.
     *
     * @param i Coordonnée i.
     * @param j Coordonnée j.
     * @param value valeur a tester pour considéré une case comme voisine.
     * @return Nombre de voisin de la case.
     */
    public static int getMaze_CountNeighbour(int i, int j, int value) {
        return Maze.checkWall(i, j, value);
    }

    /**
     * Retourne la largeur du monstre.
     *
     * @param i ID du monstre dans la liste
     * @return largeur du monstre en pixel
     */
    public double getMonsterWidth(int i) {
        Model m = list.get(i);
        if (m instanceof Monster) {
            return ((Monster) m).width;
        }
        return 0;
    }

    /**
     * Retourne la hauteur du monstre.
     *
     * @param i ID du monstre dans la liste
     * @return hauteur du monstre en pixel
     */
    public double getMonsterHeight(int i) {
        Model m = list.get(i);
        if (m instanceof Monster) {
            return ((Monster) m).height;
        }
        return 0;
    }

    /**
     * Actualise la position des monstres.
     *
     * @param root StackPane contenant les monstres.
     * @return root avec les positions acutalisées.
     */
    public StackPane getMonsterPosition(StackPane root) {
        for (int i = 0; i < 2; i++) {
            p[i].setLayoutX(list.get(p[i]).x);
            p[i].setLayoutY(list.get(p[i]).y);
        }
        return root;
    }

    /**
     * Getter de la position X du fantome
     *
     * @param i ID du fantome
     * @return Coordonnée X du fantome
     */
    public double getMonsterX(int i) {
        return (list.get(i).x);
    }

    /**
     * Getter de la position Y du fantome
     *
     * @param i ID du fantome
     * @return Coordonnée Y du fantome
     */
    public double getMonsterY(int i) {
        return (list.get(i).y);
    }


    /**
     * Getter de la Pane associer au monstre en parametre.
     *
     * @param m Monstre.
     * @return Pane du Monstre
     */
    public Pane getMonsterPane(Monster m) {
        final Image img;
        switch (getMonsterType(m)) {
            case 0:
                img = SpriteMonster.pacsprites[0];
                break;
            case 1:
                img = SpriteMonster.redsprites[0];
                break;
            case 2:
                img = SpriteMonster.pinksprites[0];
                break;
            case 3:
                img = SpriteMonster.bluesprites[0];
                break;
            case 4:
                img = SpriteMonster.orangesprites[0];
                break;
            case 7:
                img = SpriteMonster.pacgirlsprites[0];
                break;
            default:
                return null;
        }
        final ImageView imgV = new ImageView();
        imgV.setImage(img);
        imgV.setManaged(true);
        Pane pictureRegion = new Pane();
        imgV.fitWidthProperty().bind(pictureRegion.widthProperty());
        imgV.fitHeightProperty().bind(pictureRegion.heightProperty());
        pictureRegion.setMinWidth(getMonsterWidth((int) m.width));
        pictureRegion.setMinHeight(getMonsterHeight((int) m.height));
        pictureRegion.setMaxWidth(getMonsterWidth((int) m.width));
        pictureRegion.setMaxHeight(getMonsterHeight((int) m.height));
        pictureRegion.getChildren().add(imgV);
        pictureRegion.setLayoutX(m.x);
        pictureRegion.setLayoutY(m.y);
        pictureRegion.setManaged(false);
        return pictureRegion;
    }

    public void setGRID_SIZE_Y(double game_heigth) {
        Model.setGRID_SIZE_Y(game_heigth);
    }

    public void setGRID_SIZE_X(double game_width) {
        Model.setGRID_SIZE_X(game_width);
    }

    /**
     * Méthode qui défini comme enfant de "stack" les Panes des monstres
     *
     * @param stack Stackpane où intégré les monstres
     * @return StackPane avec Pane de tout les monstres
     */
    public StackPane implementPane(StackPane stack) {
        for (int i = 0; i < 2; i++) {
            stack.getChildren().add(p[i]);
        }
        return stack;
    }

    /**
     * Déclenche l'ordre de mouvement pour le pacman
     */
    public void pacmovement() {
        if(p[0] != null) {
            list.get(p[0]).paintMovement();
            list.get(p[1]).paintMovement();
        }
    }


    public void deadMovement() {

    }

    public void movement() {

    }
    /**
     * Défini la position ( coordonnées X,Y )de toute les Panes monstres.
     */
    public void setMonsterPosition() {
        if(p[0] != null) {
            for (int i = 0; i < 2; i++) {
                p[i].setLayoutX(list.get(p[i]).x);
                p[i].setLayoutY(list.get(p[i]).y);
            }
        }
    }

    /**
     * Fonction gerant la direction du pacman lors de l'appui d'une touche K.
     *
     * @param k code de la touche
     */
    public void pacmanMovement(KeyCode k) {
        if (k == KeyCode.UP) {
            list.get(p[0]).newDirection = 1;
        }
        if (k == KeyCode.DOWN) {
            list.get(p[0]).newDirection = 2;
        }
        if (k == KeyCode.LEFT) {
            list.get(p[0]).newDirection = 3;
        }
        if (k == KeyCode.RIGHT) {
            list.get(p[0]).newDirection = 4;
        }
    }

    /**
     * Fonction gerant la direction du pacgirl lors de l'appui d'une touche K.
     *
     * @param k code de la touche
     */
    public void pacgirlMovement(KeyCode k) {
        if (k == KeyCode.W) {
            list.get(p[1]).newDirection = 1;
        }
        if (k == KeyCode.S) {
            list.get(p[1]).newDirection = 2;
        }
        if (k == KeyCode.A) {
            list.get(p[1]).newDirection = 3;
        }
        if (k == KeyCode.D) {
            list.get(p[1]).newDirection = 4;
        }
    }

    /**
     * Fonction renvoyant un des sprites de la mort du pacman, en fonction d'un
     * entier représentant le sprites a afficher.
     *
     * @param cmpt timing pour le choix des sprites.
     */
    public void DeathImage(int cmpt) {
        Pane pPacman = getMonsterPane(list.get(p[0]));
        ImageView imgv = new ImageView();
        imgv.setImage(SpriteMonster.getDeathPic(cmpt));
        imgv.setManaged(true);
        imgv.fitWidthProperty().bind(p[0].widthProperty());
        imgv.fitHeightProperty().bind(p[0].heightProperty());
        p[0].getChildren().remove(0);
        p[0].getChildren().add(imgv);
    }

    /**
     * Fonction utilisé pour rafraichir les sprites de tout les monstres. Gere
     * également le changement d'état entre "Fear" ou "Eaten" et "Chase".
     */
    public void updateImage() {
        if(p[0] != null) {
            if (!PacDead) {
                timerFear--;
                if (timerFear == 0) {
                    beginChase();
                }
                ImageView imgv;
                timing++;
                for (int i = 0; i < 2; i++) { // CONDITION FIN EATEN
                    if (list.get(p[i]).eaten() && list.get(p[i]).hitbox.contains(list.get(p[i]).spawnx, list.get(p[i]).spawny)) {
                        list.get(p[i]).startChase();
                    }
                    imgv = new ImageView();
                    imgv.setImage(SpriteMonster.getPicture(getMonsterType(list.get(p[i])), list.get(p[i]).direction, timing));
                    imgv.setManaged(true);
                    imgv.fitWidthProperty().bind(p[i].widthProperty());
                    imgv.fitHeightProperty().bind(p[i].heightProperty());
                    p[i].getChildren().remove(0);
                    p[i].getChildren().add(imgv);
                }
            } else {

            }
        }
    }

    /**
     * Setter du nombre de vie restante
     */
    public void setNbLifeLeft() {
        ((Pacman) list.get(p[0])).lifeLeft--;
    }

    /**
     * Getter du nombre de vie restante
     *
     * @return nombre de vie restante
     */
    public int getNbLifeLeft() {
        return ((Pacman) list.get(p[0])).lifeLeft;
    }



    protected void beginChase() {

    }

    /**
     * Lancement des méthodes behavior des monstres, ces méthodes définissent
     * leur direction.
     */
    public void ghostBehavior() {
        if(p[0] != null) {
            for (int i = 0; i < 2; i++) {
                list.get(p[i]).behavior((Pacman) list.get(p[0]), null);
            }
        }
    }

    /**
     * Lance l'initialisation de la liste d'intersection.
     */
    public void initialize_list() {
        ListOfIntersection.initialiseList();
    }

    public void beginFear() {

    }

    public void findContact() {
       /* double x = list.get(p[0]).x + ((list.get(p[0]).width) / 2);
        double y = list.get(p[0]).y + ((list.get(p[0]).height) / 2);
        for (int i = 1; i < 2; i++) {
            if (list.get(p[i]).hitbox.contains(x, y)) {
                contact(i);
            }
        }*/
    }

    protected void contact(int i) {
        /*if ((list.get(p[i])).afraid()) {
            list.get(p[i]).startEaten();
            this.soundLibrary.audio_eat_ghost.play(1.2);
            int scoreMult = 1;
            for (int x = 1; x < 2; x++) {
                if (list.get(p[x]).eaten()) {
                    scoreMult++;
                }
            }
            sc.setScore_Score(sc.getScore_Score() + 150 * scoreMult);
            score_for_life += 150 * scoreMult;
        } else if (!list.get(p[i]).eaten()) {
            GameController.PacDead = true;

        }*/
    }

    /**
     * Méthode gérant le mouvement du pacman à la souris.
     *
     * @param event Event relatif au clic souris.
     */
    public void mouvementByMouse(MouseEvent event) {
        double mouseX = event.getX();
        double mouseY = event.getY();
        double pacX = list.get(p[0]).x;
        double pacY = list.get(p[0]).y;

        double diffX = Math.abs(mouseX - pacX);
        double diffY = Math.abs(mouseY - pacY);

        if (diffX > diffY) {
            if (mouseX > pacX) {
                pacmanMovement(KeyCode.RIGHT);
            } else {
                pacmanMovement(KeyCode.LEFT);
            }
        } else if (mouseY > pacY) {
            pacmanMovement(KeyCode.DOWN);
        } else {
            pacmanMovement(KeyCode.UP);
        }
    }

    public void deadBehavior() {}

    public void createBonus() {
       /* if ((int) (Math.random() * 1000) % 100 == 0 && !bonusExiste) {
            bonusExiste = true;
            while (true) {
                for (int i = 0; i < Maze.plateau.length; i++) {
                    for (int j = 0; j < Maze.plateau[0].length; j++) {
                        if ((int) (Math.random() * 1000) % 100 == 0 && Maze.plateau[i][j] == 2) {
                            MapChangeRequest m = new MapChangeRequest(i, j, "/Sprites/bonus_cherry.png", "Bonus");
                            getChangeQueue().add(m);
                            Maze.plateau[i][j] = 6;
                            return;
                        }
                    }
                }
            }

        }*/
    }

    /**
     * Fonction retournant le nombre de point marqué par pacGirl.
     * @return
     */
    public int getPinkPoint() {
        return ((Pacman) list.get(p[0])).pinkPoint;
    }

    /**
     * Fonction retournant le nombre de point marqué par pacman.
     * @return
     */
    public int getYellowPoint() {
        return ((Pacman) list.get(p[0])).yellowPoint;
    }
}
