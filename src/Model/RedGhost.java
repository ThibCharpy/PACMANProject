package Model;

/**
 * Created by thibaultgeoffroy on 29/02/2016.
 */
public class RedGhost extends Ghost {
    public RedGhost(double x, double y, double size, double speed, int direction) {
        super(x, y, size, speed, direction);
    }

    public void redBehavior(Pacman pac) {
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

                        if (ListOfIntersection.testIntersection(Pos_X_Gho, Pos_Y_Gho) != null) {
                            if (PacPos.noeud != null) {
                                result = RechercheChemin.DiscoverPath(GhostPos, PacPos, this);
                                this.newDirection = determineDirection(GhostPos.noeud, result);
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