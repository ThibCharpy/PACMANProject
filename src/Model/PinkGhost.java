package Model;

/**
 * Created by thibaultgeoffroy on 29/02/2016.
 */
public class PinkGhost extends Ghost {
    public PinkGhost(double x, double y, double size, double speed, int direction) {
        super(x, y, size, speed, direction);
    }
    
    /**
     * Méthode utilisée pour essayer de prédir la destination du pacman.
     *      En fonction de la direction actuelle du pacman, on trouve la prochaine intersection dans cette direction.
     *      En itérant dans le tableau dans la direction fournie par pacman, on retourne la premiere intersection trouvée.
     * @param PacPos posiion du pacman, ou intersection la plus proche si il n'est pas sur une intersection.
     * @param direction direction actuelle du pacman
     * @return la prochaine intersection sur le chemin du pacman, ou sa position actuelle si il n'y en a pas.
     */
    public Node findPacmanDestination(Node PacPos, int direction) {
        NoeudGraphe found = null;
        int cmpt = 1;
        int CoordX = PacPos.noeud.getCoordX();
        int CoordY = PacPos.noeud.getCoordY();
        switch (direction) {
            case 4: // Cas Droite
                while (found == null && CoordX + cmpt < Maze.plateau[0].length && (Maze.plateau[CoordY][CoordX + cmpt]) != 1) {
                    found = ListOfIntersection.testIntersection(CoordX + cmpt, CoordY);
                    cmpt++;
                }
                break;
            case 3: // Cas Gauche
                while (found == null && CoordX - cmpt > 0 && (Maze.plateau[CoordY][CoordX - cmpt]) != 1) {
                    found = ListOfIntersection.testIntersection(CoordX - cmpt, CoordY);
                    cmpt++;
                }
                break;
            case 1: // Cas Haut
                while (found == null && CoordY + cmpt < Maze.plateau.length && (Maze.plateau[CoordY + cmpt][CoordX]) != 1) {
                    found = ListOfIntersection.testIntersection(CoordX, CoordY + cmpt);
                    cmpt++;
                }
                break;
            case 2: // Cas Bas
                while (found == null && CoordX - cmpt > 0 && (Maze.plateau[CoordY - cmpt][CoordX]) != 1) {
                    found = ListOfIntersection.testIntersection(CoordX, CoordY - cmpt);
                    cmpt++;
                }
                break;
            default:
                return PacPos;
        }
        if (found != null) {
            return new Node(found, cmpt);
        } else {
            return PacPos;
        }
    }
   /**
     * Détermine la cible du fantome en fonction de son etat actuel, lorsqu'il arrive a une intersection il effectue les choix suivant :
     *      - En mode idle, il va se diriger vers le coin supérieur gauche et effectuer une ronde
     *      - En mode chase, il va utilisé la findPacmanDestination pour détérminer sa cible, une fois en partant de la position du pacman, puis de la position déterminée précédement. Si celle ci est null, on utilise la précedente, si elle est null également, il retourne vers le coin supérieur gauche.
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
        Node PinkyPos = ListOfIntersection.getIntersection(3, 4);
        NoeudGraphe result;
        if (GhostPos.noeud != null) {

            if (!this.lastVisited.compare(GhostPos)) {

                switch (this.state) {
                    case "idle":
                        result = RechercheChemin.DiscoverPath(GhostPos, PinkyPos, this);
                        this.save_objective = result;
                        this.newDirection = determineDirection(GhostPos.noeud, result);
                        break;
                    case "chase":
                        int Pos_X_pac = getMonster_Case_X(pac.x);
                        int Pos_Y_pac = getMonster_Case_Y(pac.y);
                        Node PacPos = ListOfIntersection.getIntersection(Pos_X_pac, Pos_Y_pac);
                        if (PacPos.noeud != null) {
                            Node PP = findPacmanDestination(PacPos, pac.direction);
                            if (PP.noeud != null) {
                                result = RechercheChemin.DiscoverPath(GhostPos, PP, this);
                                this.save_objective = result;
                                this.newDirection = determineDirection(GhostPos.noeud, result);
                            } else {
                                result = RechercheChemin.DiscoverPath(GhostPos, PacPos, this);
                                this.save_objective = result;
                                this.newDirection = determineDirection(GhostPos.noeud, result);
                            }                         
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
                        this.save_objective = result;
                        this.newDirection = determineDirection(GhostPos.noeud, result);
                        break;
                    case "eated" :
                        result = RechercheChemin.DiscoverPath(GhostPos, this.PrisonCenter, this);
                        this.save_objective = result;
                        this.newDirection = determineDirection(GhostPos.noeud, result);
                        break;
                }
            }else if(this.save_objective.compare(GhostPos.noeud)){
               result = RechercheChemin.DiscoverPath(GhostPos, PinkyPos, this);
               this.save_objective = result;
               this.newDirection = determineDirection(GhostPos.noeud, result); 
            }
        }
    }
}
