package Model;

/**
 * Created by thibaultgeoffroy on 29/02/2016.
 */
public class PinkGhost extends Ghost {
    public PinkGhost(double x, double y, double size, double speed, int direction) {
        super(x, y, size, speed, direction);
    }
    
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
    
    public void pinkBehavior(Pacman pac) {
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
                        this.newDirection = determineDirection(GhostPos.noeud, result);
                        break;
                    case "chase":
                        int Pos_X_pac = getMonster_Case_X(pac.x);
                        int Pos_Y_pac = getMonster_Case_Y(pac.y);
                        Node PacPos = ListOfIntersection.getIntersection(Pos_X_pac, Pos_Y_pac);
                        if (PacPos.noeud != null) {
                            Node PP = findPacmanDestination(PacPos, pac.direction);
                            PP = findPacmanDestination(PP, pac.direction);
                            if (ListOfIntersection.testIntersection(Pos_X_Gho, Pos_Y_Gho) != null) {
                                if (PP.noeud != null) {
                                    result = RechercheChemin.DiscoverPath(GhostPos, PP, this);
                                    this.newDirection = determineDirection(GhostPos.noeud, result);
                                } else {
                                    result = RechercheChemin.DiscoverPath(GhostPos, PinkyPos, this);
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
