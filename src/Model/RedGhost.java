package Model;

/**
 * Created by thibaultgeoffroy on 29/02/2016.
 */
public class RedGhost extends Ghost {
    public RedGhost(double x, double y, double size, double speed, int direction) {
        super(x, y, size, speed, direction);
    }

   /**
     * Détermine la cible du fantome en fonction de son etat actuel, lorsqu'il arrive a une intersection il effectue les choix suivant :
     *      - En mode idle, il va se diriger vers le coin inférieur gauche et effectuer une ronde
     *      - En mode chase, il va toujours suivre le pacman.
     *      - En mode fear, il choisi aléatoirement une direction.
     * 
     * Cette fonction retourne la direction a prendre a l'intersection actuelle du fantome, elle est lancée seulement si le fantome est sur une intersection.
     * @param pac Instance de pacman 
     * 
     */
    @Override
    public void behavior(Pacman pac, Ghost red) {
        int Pos_X_Gho = getMonster_Case_X(this.x);
        int Pos_Y_Gho = getMonster_Case_Y(this.y);
        Node GhostPos = ListOfIntersection.getIntersection(Pos_X_Gho, Pos_Y_Gho);
        NoeudGraphe result;
        Node BlinkyPos = ListOfIntersection.getIntersection(20, 4);
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
                                result = RechercheChemin.DiscoverPath(GhostPos, PacPos, this);
                                this.newDirection = determineDirection(GhostPos.noeud, result);
                            }                 
                        break;
                    case "fear":
                        int x;
                        Node randomNode;
                        if(GhostPos.noeud.voisins.size() > 1){                          
                            x = (int) (Math.random()*GhostPos.noeud.voisins.size());
                            randomNode = GhostPos.noeud.voisins.get(x);
                        }else{
                            randomNode = GhostPos.noeud.voisins.get(0);
                        }
                        result = RechercheChemin.DiscoverPath(GhostPos, randomNode, this);
                        this.newDirection = determineDirection(GhostPos.noeud, result);
                        break;
                    case "eated" :
                        result = RechercheChemin.DiscoverPath(GhostPos, this.PrisonCenter, this);
                        this.newDirection = determineDirection(GhostPos.noeud, result);
                        break;
                }
            }
        }
    }
}