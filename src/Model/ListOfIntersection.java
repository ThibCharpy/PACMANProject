package Model;

import java.util.LinkedList;

/**
 * Created by thibaultgeoffroy on 25/02/2016.
 */
public class ListOfIntersection {
    /**
     *
     */
    public static LinkedList<Node> IntersectionList;

    /**
     * Fonction qui lance le processus de construction de la liste
     * d'intersection, à appeler avant toute recherche de chemin
     */
    public static void initialiseList() {
        IntersectionList = new LinkedList<>();
        for (int i = 0; i < Maze.plateau.length; i++) {
            for (int x = 0; x < Maze.plateau[0].length; x++) {
                if (Maze.plateau[i][x] == 2) {
                    getIntersectionType(i, x, 2);
                }
                if (Maze.plateau[i][x] == 3) {
                    getIntersectionType(i, x, 2);
                }
                if (Maze.plateau[i][x] == 4) {
                    getIntersectionType(i, x, 4);
                }
            }
        }
        addSpecialElements();
        for (Node element : IntersectionList) {
            getElemVoisin(element.noeud);
        }
    }

    private static void addSpecialElements() { // Trouver une méthode de remplacement générique pour tout type de carte
        IntersectionList.add(new Node(new NoeudGraphe(2,14,"excpetion")));
        IntersectionList.add(new Node(new NoeudGraphe(9,14,"excpetion")));
        IntersectionList.add(new Node(new NoeudGraphe(13,14,"excpetion")));
        IntersectionList.add(new Node(new NoeudGraphe(20,14,"excpetion")));
    }

    public void Affichage() {
        for (Node element : IntersectionList) {
            System.out.println(element.noeud.getTypeOf() + "  :  " + element.noeud.getCoordY() + " ; " + element.noeud.getCoordX() + " -> " + element.distance);
            getElemVoisin(element.noeud);
            for (Node fils : element.noeud.getVoisin()) {
                System.out.println("                " + fils.noeud.getTypeOf() + "  :  " + fils.noeud.getCoordY() + " ; " + fils.noeud.getCoordX() + " -> " + fils.distance);
            }
        }
    }

    /**
     * Teste si la case de coordonnée i,x dois etre une "Line Horizontal"
     *
     * @param i coordonnée rangée
     * @param x coordonnée colonne
     * @return vrai si Line Horizontal, faux sinon
     */
    private static boolean isLineH(int i, int x) {
        boolean LineHorizontal = false;
        if (((x - 1 > -1) && (x + 1 < Maze.plateau[0].length))) {
            if ((Maze.plateau[i][x - 1] == 1) && (Maze.plateau[i][x + 1] == 1)) {
                LineHorizontal = true;
            }
        }
        return LineHorizontal;
    }

    /**
     * Teste si la case de coordonnée i,x dois etre une "Line Vertical"
     *
     * @param i coordonnée rangée
     * @param x coordonnée colonne
     * @return vrai si Line Vertical, faux sinon
     */
    private static boolean isLineV(int i, int x) {
        boolean LineVertical = false;
        if (((i - 1 > -1) && (i + 1 < Maze.plateau.length))) {
            if ((Maze.plateau[i - 1][x] == 1) && (Maze.plateau[i + 1][x] == 1)) {
                LineVertical = true;
            }
        }
        return LineVertical;
    }

    /**
     * Fonction créant une node principale dans la liste d'intersection en
     * fonction du type d'intersection qu'elle concerne
     *
     * @param i coordonnée i dans la premiere dimension du tableau
     * @param x coordonnée x dans la deuxieme dimension du tableau
     * @param ValuetoTest valeur a envoyer a checkwall pour tester la valeur des
     * cases voisines
     */
    private static void getIntersectionType(int i, int x, int ValuetoTest) {
        switch (Maze.checkWall(i, x, ValuetoTest)) {
            case 2:
                if ((!isLineV(i, x)) && (!isLineH(i, x))) {
                    IntersectionList.add(new Node(new NoeudGraphe(x, i, "Corner")));
                }
                break;
            case 3:
                IntersectionList.add(new Node(new NoeudGraphe(x, i, "Intersection3")));
                break;
            case 4:
                IntersectionList.add(new Node(new NoeudGraphe(x, i, "Intersection4")));
                break;
            default:
                break;
        }
    }

    /**
     * Fonction qui lance le processus de detection des voisins d'une node
     * principale
     *
     * @param element NoeudGraphe de la node dont on recherche les voisins
     */
    private static void getElemVoisin(NoeudGraphe element) {
        int found;
        int direction = 0;
        while (direction < 4) {
            found = 0;
            while (found == 0 && direction < 4) {
                found = findVoisin(element, direction);
                direction++;
            }
        }
    }

    /**
     * Determine si une case du tableau est une intersection ( ie : elle est
     * compris dans la liste d'intersection ). Si c'est le cas, crée une node
     * secondaire et l'ajoute comme voisin du NoeudGraphe en parametre
     *
     * @param coordX coordonnée X, correspond à la deuxieme dimension du tableau
     * @param coordY coordonnée Y, correspond à la premiere dimension du tableau
     * @param element Noeudgraphe isssu de la node en cours de test
     * @param dist distance ( en nombre de case du tableau ) par rapport a la
     * node en cours de test
     * @return vrai si c'est une intersection et qu'un voisin a été ajouté
     */
    private static boolean isAndAddIntersection(int coordX, int coordY, NoeudGraphe element, int dist) {
        for (Node element1 : IntersectionList) {
            if (element1.noeud.getCoordX() == coordX && element1.noeud.getCoordY() == coordY) {
                element.addVoisin(new Node(element1.noeud, dist));
                return true;
            }

        }
        return false;
    }

    public static Node getIntersection(int coordX, int coordY){
        for (Node element1 : IntersectionList) {
            if (element1.noeud.getCoordX() == coordX && element1.noeud.getCoordY() == coordY) {
                return element1;
            }
        }
        return findClosestIntersection(coordX, coordY);
    }

    /**
     * Méthode public pour vérifié si une case donnée du tableau est présente
     * dans la liste d'intersection
     *
     * @param coordX coordonnée X, correspond à la deuxieme dimension du tableau
     * @param coordY coordonnée Y, correspond à la premiere dimension du tableau
     * @return vrai si c'est une intersection et qu'un voisin a été ajouté
     */
    public static NoeudGraphe testIntersection(int coordX, int coordY) {
        for (Node element1 : IntersectionList) {
            if (element1.noeud.getCoordX() == coordX && element1.noeud.getCoordY() == coordY) {
                return element1.noeud;
            }
        }
        return null;
    }

    public static Node findClosestIntersection(int coordX, int coordY) {
        int direction = 0;
        LinkedList<Node> result = new LinkedList<>();
        while (direction < 4) {
            while (direction < 4) {
                result.add(findVoisin2(coordX, coordY, direction));
                direction++;
            }
        }
        int bestdistance = 0;
        Node ClosestInter = null;
        for (Node element : result) {
            if (element != null) {
                if ((element.distance < bestdistance || bestdistance == 0)) {
                    bestdistance = element.distance;
                    ClosestInter = element;
                }
            }
        }
        return ClosestInter;
    }

    private static Node findVoisin2(int CoordX, int CoordY, int direction) {
        NoeudGraphe found = null;
        int cmpt = 1;
        if (testdirection(CoordX, CoordY, direction)) {
            switch (direction) {
                case 0: // Cas Droite
                    while (found == null && CoordX + cmpt < Maze.plateau[0].length &&(Maze.plateau[CoordY][CoordX + cmpt]) != 1) {
                        found = testIntersection(CoordX + cmpt, CoordY);
                        cmpt++;
                    }
                    return new Node(found, cmpt);
                case 1: // Cas Gauche
                    while (found == null && CoordX - cmpt > 0 && (Maze.plateau[CoordY][CoordX - cmpt]) != 1) {
                        found = testIntersection(CoordX - cmpt, CoordY);
                        cmpt++;
                    }
                    return new Node(found, cmpt);
                case 2: // Cas Haut
                    while (found == null && CoordY + cmpt < Maze.plateau.length && (Maze.plateau[CoordY + cmpt][CoordX]) != 1) {
                        found = testIntersection(CoordX, CoordY + cmpt);
                        cmpt++;
                    }
                    return new Node(found, cmpt);
                case 3: // Cas Bas
                    while (found == null && CoordX - cmpt > 0 && (Maze.plateau[CoordY - cmpt][CoordX]) != 1) {
                        found = testIntersection(CoordX, CoordY - cmpt);
                        cmpt++;
                    }
                    return new Node(found, cmpt);
                default:
                    return null;
            }
        }
        return null;
    }

    /**
     * Verifie si la case suivant la direction compris en parametre n'est pas un
     * mur ( ie : test si la case dont le direction est contenue en parametre
     * est une case "circulable"
     *
     * @param element Noeudgraphe isssu de la node en cours de test.
     * @param direction direction à tester, 0 : Droite, 1 : Gauche, 2 : Haut, 3
     * : Bas.
     * @return vrai si la case n'est pas un mur
     */
    private static boolean testdirection(int CoordX, int CoordY, int direction) {
        switch (direction) {
            case 0: // Cas Droite
                return Maze.plateau[CoordY][CoordX + 1] != 1;

            case 1: // Cas Gauche
                return Maze.plateau[CoordY][CoordX - 1] != 1;

            case 2: // Cas Haut
                return Maze.plateau[CoordY + 1][CoordX] != 1;

            case 3: // Cas Bas
                return Maze.plateau[CoordY - 1][CoordX] != 1;

            default:
                return false;

        }
    }

    /**
     * Recheche le voisin de l'élement en parametre en fonction d'une direction,
     * progresse sur la ligne ou la colonne, appel isIntersection et renvoi la
     * distance quand on a un résultat.
     *
     * @param element Noeudgraphe isssu de la node en cours de test.
     * @param direction direction à tester, 0 : Droite, 1 : Gauche, 2 : Haut, 3
     * : Bas.
     * @return distance entre la node en cours de test et le voisin trouvé.
     */
    private static int findVoisin(NoeudGraphe element, int direction) {
        boolean found = false;
        int cmpt = 1;
        if (testdirection(element.getCoordX(), element.getCoordY(), direction)) {
            switch (direction) {
                case 0: // Cas Droite
                    while (!found && (Maze.plateau[element.getCoordY()][element.getCoordX() + cmpt]) != 1) {
                        found = isAndAddIntersection(element.getCoordX() + cmpt, element.getCoordY(), element, cmpt);
                        cmpt++;
                    }
                    return cmpt;
                case 1: // Cas Gauche
                    while (!found && (Maze.plateau[element.getCoordY()][element.getCoordX() - cmpt]) != 1) {
                        found = isAndAddIntersection(element.getCoordX() - cmpt, element.getCoordY(), element, cmpt);
                        cmpt++;
                    }
                    return cmpt;
                case 2: // Cas Haut
                    while (!found && (Maze.plateau[element.getCoordY() + cmpt][element.getCoordX()]) != 1) {
                        found = isAndAddIntersection(element.getCoordX(), element.getCoordY() + cmpt, element, cmpt);
                        cmpt++;
                    }
                    return cmpt;
                case 3: // Cas Bas
                    while (!found && (Maze.plateau[element.getCoordY() - cmpt][element.getCoordX()]) != 1) {
                        found = isAndAddIntersection(element.getCoordX(), element.getCoordY() - cmpt, element, cmpt);
                        cmpt++;
                    }
                    return cmpt;
                default:
                    return 0;
            }
        }
        return 0;
    }
}
