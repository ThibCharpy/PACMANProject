package Model;

/**
 * Created by thibaultgeoffroy on 29/02/2016.
 */
public class RedGhost extends Ghost {

    /**
     * Constructeur de la classe RedGhost
     *
     * @param x Coordonnée X
     * @param y Coordonnée Y
     * @param size taille du fantome
     * @param speed vitesse du fantome
     * @param direction direction initiale du fantome
     */
    public RedGhost(double x, double y, double size, double speed, int direction) {
        super(x, y, size, speed, direction);
    }

    /**
     * Détermine la cible du fantome en fonction de son etat actuel, lorsqu'il
     * arrive a une intersection il effectue les choix suivant : - En mode idle,
     * il va se diriger vers le coin inférieur gauche et effectuer une ronde -
     * En mode chase, il va toujours suivre le pacman. - En mode fear, il choisi
     * aléatoirement une direction.
     *
     * Cette fonction retourne la direction a prendre a l'intersection actuelle
     * du fantome, elle est lancée seulement si le fantome est sur une
     * intersection.
     *
     * @param pac Instance de pacman
     *
     *
     */
    @Override
    public void behavior(Pacman pac, Ghost red) {
        int Pos_X_Gho = getMonster_Case_X(this.x + (width / 2));
        int Pos_Y_Gho = getMonster_Case_Y(this.y + (height / 2));
        Node GhostPos = ListOfIntersection.getIntersectionAndClosest(Pos_X_Gho, Pos_Y_Gho);
        NoeudGraphe result = null;
        Node BlinkyPos = ListOfIntersection.getIntersectionAndClosest(20, 4);
        if (GhostPos.noeud != null) {
            if (!this.lastVisited.compare(GhostPos)) {
                switch (this.state) {
                    case "idle":
                        result = recherche.DiscoverPath(GhostPos, BlinkyPos, this);
                        this.save_objective = result;
                        this.newDirection = determineDirection(GhostPos.noeud, result);
                        break;
                    case "chase":
                        int Pos_X_pac = getMonster_Case_X(pac.x + (width / 2));
                        int Pos_Y_pac = getMonster_Case_Y(pac.y + (height / 2));
                        Node PacPos = ListOfIntersection.getIntersectionAndClosest(Pos_X_pac, Pos_Y_pac);
                        if (!(Pos_X_pac == Pos_X_Gho && Pos_Y_pac == Pos_Y_Gho)) {
                            if (PacPos.noeud != null) {
                                result = recherche.DiscoverPath(GhostPos, PacPos, this);
                                this.save_objective = result;
                                this.newDirection = determineDirection(GhostPos.noeud, result);
                            }
                        }
                        break;
                    case "fear":
                        int x;
                        Node randomNode;
                        if (GhostPos.noeud.voisins.size() > 1) {
                            x = (int) (Math.random() * GhostPos.noeud.voisins.size());
                            randomNode = GhostPos.noeud.voisins.get(x);
                        } else {
                            randomNode = GhostPos.noeud.voisins.get(0);
                        }
                        result = recherche.DiscoverPath(GhostPos, randomNode, this);
                        this.save_objective = result;
                        this.newDirection = determineDirection(GhostPos.noeud, result);
                        break;
                    case "eated":
                        result = recherche.DiscoverPath(GhostPos, this.PrisonCenter, this);
                        this.save_objective = result;
                        this.newDirection = determineDirection(GhostPos.noeud, result);
                        break;
                }
            } else if (this.save_objective.compare(GhostPos.noeud)) {
                result = recherche.DiscoverPath(GhostPos, BlinkyPos, this);
                this.save_objective = result;
                this.newDirection = determineDirection(GhostPos.noeud, result);
            }
        }
    }
}
