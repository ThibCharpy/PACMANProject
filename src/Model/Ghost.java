package Model;

public abstract class Ghost extends Monster {

    public Node lastVisited;
    public NoeudGraphe save_objective;
    public String state;
    public RechercheChemin recherche;

    /**
     * Constructeur de la classe abstraite Ghost
     *
     * @param x Coordonnée X
     * @param y Coordonnée Y
     * @param size taille du fantome
     * @param speed vitesse du fantome
     * @param direction direction initiale du fantome
     */
    public Ghost(double x, double y, double size, double speed, int direction) {
        super(x, y, size, speed, direction);
        state = "chase";
        this.lastVisited = ListOfIntersection.getIntersectionAndClosest(2, 14);
        recherche = new RechercheChemin(ListOfIntersection.IntersectionList);
    }

    /**
     * Fonction vérifiant si l'état d'un fantome est "fear"
     *
     * @return vrai si c'est le cas, faux sinon.
     */
    @Override
    public boolean afraid() {
        return state.equals("fear");
    }

    /**
     * Fonction vérifiant si l'état d'un fantome est "chase".
     *
     * @return vrai si c'est le cas, faux sinon.
     */
    public boolean chasing() {
        return state.equals("chase");
    }

    /**
     * Fonction vérifiant si l'état d'un fantome est "eated".
     *
     * @return vrai si c'est le cas, faux sinon.
     */
    @Override
    public boolean eaten() {
        return state.equals("eated");
    }

    /**
     * Fonction déclenchant le mode "fear", si le fantome n'est pas déjà en état
     * "eated".
     */
    @Override
    public void startFear() {
        if (!eaten()) {
            state = "fear";
        }
    }

    /**
     * Fonction déclenchant le mode "chase" chez un fantome.
     */
    @Override
    public void startChase() {
        state = "chase";
    }

    /**
     * Fonction déclenchant le mode "eated" chez un fantome.
     */
    @Override
    public void startEaten() {
        state = "eated";

    }

    /**
     * Fonction qui fait passer un fantome du mode "fear" au mode "chase".
     */
    @Override
    public void fromFearToChase() {
        if (afraid()) {
            startChase();
        }
    }

    /**
     * Fonction qui fait passer un fantome du mode "eated" au mode "chase".
     */
    @Override
    public void fromDeathToChase() {
        if (eaten()) {
            startChase();
        }
    }

    @Override
    public void interact() {
    }

    /**
     * Fonction définissant le comportement d'un fantome.
     *
     * @param pac instance de pacman.
     * @param red instance du fantome rouge.
     */
    @Override
    public abstract void behavior(Pacman pac, Ghost red);

    /**
     * Méthode utilisé pour déterminé la direction entre deux intersections( une
     * de depart et une d'arrivé ) situées sur la même ligne ou colonne
     *
     * @param start Intersection de depart
     * @param goal Intersection d'arrivée
     * @return un entier relatif (meme que pour la direction des monstres) à la
     * direction a prendre pour aller de start a goal.
     */
    public int determineDirection(NoeudGraphe start, NoeudGraphe goal) {
        if (start.getCoordX() < goal.getCoordX() && start.getCoordY() == goal.getCoordY()) {
            return 4; // Droite
        }
        if (start.getCoordX() > goal.getCoordX() && start.getCoordY() == goal.getCoordY()) {
            return 3; // Gauche
        }
        if (start.getCoordY() < goal.getCoordY() && start.getCoordX() == goal.getCoordX()) {
            return 2; // Bas
        }
        if (start.getCoordY() > goal.getCoordY() && start.getCoordX() == goal.getCoordX()) {
            return 1; // Haut
        }
        return this.direction;
    }

    /**
     * Mesure la distance entre deux intersections
     *
     * @param GhostPos intersection de depart
     * @param PacPos intersection d'arrivée
     * @return un entier représentant la distance entre deux intersection ( en
     * nombre de case du tableau )
     */
    public int mesureDistance(Node GhostPos, Node PacPos) {
        return (Math.abs(PacPos.noeud.getCoordX() - GhostPos.noeud.getCoordX()) + Math.abs(PacPos.noeud.getCoordY() - GhostPos.noeud.getCoordY()));
    }

}
