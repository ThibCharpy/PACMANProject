package Model;

/**
 * Created by thibaultgeoffroy on 29/02/2016.
 */
public class BlueGhost extends Ghost {
    public BlueGhost(double x, double y, double size, double speed, int direction) {
        super(x, y, size, speed, direction);
    }
    /**
     * Recupère la cible ( case du tableau ) du fantome, le cible ce calcule de cette façon : 
     *      - On calcul une cible (red_X - pac_X),(red_Y - pac_Y), cela revient a la distance entre le fantome rouge et pacman
     *      - On ajoute au coordonnée du fantome rouge cette cible, on a donc une nouvelle cible crée par cette combinaison
     *      - Si les coordonnées, multiplié par 2, de cible sont dans les limites de la fenetre de jeu et qu'il ne sagit pas d'un mur, on recupére l'intersection la plus proche et on la retourne.
     *      - Sinon on refait le meme test sans multiplication, si ce test ne réussi pas, on renvoi null.
     * @param pac_X Coordonnée X du pacman ( 1er case du tableau ) 
     * @param pac_Y Coordonnée Y du rouge ( 2eme case du tableau ) 
     * @param red_X Coordonnée X du pacman ( 2eme case du tableau ) 
     * @param red_Y Coordonnée Y du rouge ( 1er case du tableau ) 
     * @return la cible du fantome, ou null si cette cible ne passe pas les tests.
     */
    private Node getInkyTarget(int pac_X, int pac_Y, int red_X, int red_Y) { // Trouver moyen de changer les valeurs brutes de détéction 
        int Target_X = Math.abs(red_X - pac_X);
        int Target_Y = Math.abs(red_Y - pac_Y);
        Node res1;
        if ((red_X + Target_X) * 2 >= 2 && (red_X + Target_X) * 2 <= 20 && (red_Y + Target_Y)*2 >= 4 && (red_Y + Target_Y)*2 <= 24) {
            if (Maze.plateau[red_Y + Target_Y][red_X + Target_X] != 1) {
                res1 = ListOfIntersection.getIntersection(red_X + Target_X, red_Y + Target_Y);
                if (res1.noeud != null) {
                    return res1;
                }
            }
        }
        if (((red_X + Target_X)) >= 2 && ((red_X + Target_X)) <= 20 && ((red_Y + Target_Y)) >= 4 && ((red_Y + Target_Y)) <= 24) {
            if (Maze.plateau[((red_Y + Target_Y) / 2)][((red_X + Target_X) / 2)] != 1) {
                res1 = ListOfIntersection.getIntersection(((red_X + Target_X) / 2), ((red_Y + Target_Y) / 2));
                if (res1.noeud != null) {
                    return res1;
                }
            }
        }
        return null;
    }
    
    
    /**
     * Détermine la cible du fantome en fonction de son etat actuel, lorsqu'il arrive a une intersection il effectue les choix suivant :
     *      - En mode idle, il va se diriger vers le coin inférieur droit et effectuer une ronde
     *      - En mode chase, il va utilisé la méthode getInkyTarget pour trouver sa cible, si la méthode renvoi null, il retourne vers le coin inférieur droit.
     *      - En mode fear, il choisi aléatoirement une direction.
     * 
     * Cette fonction retourne la direction a prendre a l'intersection actuelle du fantome, elle est lancée seulement si le fantome est sur une intersection.
     * @param pac Instance de pacman
     * @param red Instance du fantome rouge
     */
    public void blueBehavior(Pacman pac, Ghost red) {
        int Pos_X_Gho = getMonster_Case_X(this.x);
        int Pos_Y_Gho = getMonster_Case_Y(this.y);
        Node GhostPos = ListOfIntersection.getIntersection(Pos_X_Gho, Pos_Y_Gho);
        Node InkyPos = ListOfIntersection.getIntersection(20, 24);
        NoeudGraphe result;
        if (GhostPos.noeud != null) {
            if (!this.lastVisited.compare(GhostPos)) {
                switch (this.state) {
                    case "idle":

                        result = RechercheChemin.DiscoverPath(GhostPos, InkyPos, this);
                        this.newDirection = determineDirection(GhostPos.noeud, result);
                        break;
                    case "chase":
                        int Pos_X_pac = getMonster_Case_X(pac.x);
                        int Pos_Y_pac = getMonster_Case_Y(pac.y);
                        Node PacPos = ListOfIntersection.getIntersection(Pos_X_pac, Pos_Y_pac);
                        if (ListOfIntersection.testIntersection(Pos_X_Gho, Pos_Y_Gho) != null) {
                            if (PacPos.noeud != null) {
                                int Pos_X_red = getMonster_Case_X(red.x);
                                int Pos_Y_red = getMonster_Case_Y(red.y);
                                Node Objectif = getInkyTarget(Pos_X_pac, Pos_Y_pac, Pos_X_red, Pos_Y_red);
                                if (Objectif != null) {
                                    if (Objectif.noeud != null) {
                                        result = RechercheChemin.DiscoverPath(GhostPos, Objectif, this);
                                        this.newDirection = determineDirection(GhostPos.noeud, result);
                                    }
                                } else {
                                    result = RechercheChemin.DiscoverPath(GhostPos, InkyPos, this);
                                    this.newDirection = determineDirection(GhostPos.noeud, result);
                                }
                            }
                        }
                        break;
                    case "fear":
                        break;
                }
            }
        }
    }
}
