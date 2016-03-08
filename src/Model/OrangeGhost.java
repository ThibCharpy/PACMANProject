package Model;

/**
 * Created by thibaultgeoffroy on 29/02/2016.
 */
public class OrangeGhost extends Ghost {
    public OrangeGhost(double x, double y, double size, double speed, int direction) {
        super(x, y, size, speed, direction);
    }
    
    
    /**
     * Détermine la cible du fantome en fonction de son etat actuel, lorsqu'il arrive a une intersection il effectue les choix suivant :
     *      - En mode idle, il va se diriger vers le coin inférieur gauche et effectuer une ronde
     *      - En mode chase, il va utilisé la méthode mesure distance entre sa position et celle du pacman, si il est trop proche ( moins de 8 case ) il va retourner vers le coin inférieur gauche, sinon il va suivre pacman.
     *      - En mode fear, il choisi aléatoirement une direction.
     * 
     * Cette fonction retourne la direction a prendre a l'intersection actuelle du fantome, elle est lancée seulement si le fantome est sur une intersection.
     * @param pac Instance de pacman 
     * 
     */
    public void orangeBehavior(Pacman pac) {
        int Pos_X_Gho = getMonster_Case_X(this.x);
        int Pos_Y_Gho = getMonster_Case_Y(this.y);
        Node GhostPos = ListOfIntersection.getIntersection(Pos_X_Gho, Pos_Y_Gho);
        Node BlinkyPos = ListOfIntersection.getIntersection(3, 24);
        NoeudGraphe result;
        if (GhostPos.noeud != null) {
            if (!this.lastVisited.compare(GhostPos)) {
                switch (this.state) {
                    case "idle":
                        result = RechercheChemin.DiscoverPath(GhostPos, BlinkyPos, this);
                        this.newDirection = determineDirection(GhostPos.noeud, result);
                        break;
                    case "chase":
                        int Pos_X_pac = getMonster_Case_X(pac.x);
                        int Pos_Y_pac = getMonster_Case_Y(pac.y);
                        Node PacPos = ListOfIntersection.getIntersection(Pos_X_pac, Pos_Y_pac);
                        if (PacPos.noeud != null) {
                            if (mesureDistance(GhostPos, PacPos) >= 8) {
                                if (ListOfIntersection.testIntersection(Pos_X_Gho, Pos_Y_Gho) != null) {
                                    result = RechercheChemin.DiscoverPath(GhostPos, PacPos, this);
                                    this.newDirection = determineDirection(GhostPos.noeud, result);
                                }
                            } else {
                                result = RechercheChemin.DiscoverPath(GhostPos, BlinkyPos, this);
                                this.newDirection = determineDirection(GhostPos.noeud, result);
                            }
                        }else{
                            result = RechercheChemin.DiscoverPath(GhostPos, BlinkyPos, this);
                            this.newDirection = determineDirection(GhostPos.noeud, result);
                        }
                        break;
                    case "fear":

                        break;

                }
            }
        }
    }
}
