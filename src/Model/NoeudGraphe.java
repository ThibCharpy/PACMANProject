package Model;

import java.util.LinkedList;

/**
 * Created by thibaultgeoffroy on 25/02/2016.
 */
public class NoeudGraphe {
    /**
     * coordonnée X, correspond à la deuxieme dimension du tableau
     */
    private int coordX;

    /**
     * coordonnée Y, correspond à la premiere dimension du tableau
     */
    private int coordY;

    /**
     * String contenant le type d'intersection où pointe les coordonnées
     */
    private String TypeOf;

    /**
     * Cout necessaire pour atteindre cette intersection par rapport a une intersecion de depart
     */
    public int Heuristiccost = 0;

    /**
     * Marque le fait que l'intersection a déjà été parcouru une fois dans l'algorithme de plus court chemin
     */
    public boolean closed = false;

    // public boolean blocked = false;

    /**
     * Marque le fait que cette intersection sois le point de depart de l'algorithme de plus court chemin
     */
    public boolean isStart = false;

    /**
     * Marque le fait que cette intersection sois le point d'arrivé de l'algorithme de plus court chemin
     */
    public boolean isEnd = false;

    /**
     * Intersection "parent" dans le résultat de l'algorithme de plus court chemin
     */
    public NoeudGraphe bestHeuristicParent;

    /**
     * Liste de voisin de l'intersection
     */
    private LinkedList<Node> voisins;

    /**
     * Constructeur de NoeudGraphe, principalement utilisé pour crée des nodes principales
     * @param X coordonnée X, correspond à la deuxieme dimension du tableau
     * @param Y coordonnée Y, correspond à la premiere dimension du tableau
     * @param type String contenant le type d'intersection où pointe les coordonnées
     */
    public NoeudGraphe(int X, int Y, String type){
        this.coordX = X;
        this.coordY = Y;
        this.TypeOf = type;
        this.voisins = new LinkedList<>();
    }

    /**
     * Constructeur de NoeudGraphe, principalement utilisé pour crée des nodes secondaire
     * @param X coordonnée X, correspond à la deuxieme dimension du tableau
     * @param Y coordonnée Y, correspond à la premiere dimension du tableau
     * @param type String contenant le type d'intersection où pointe les coordonnées
     * @param voisin Liste de voisin de l'intersection
     */
    public NoeudGraphe(int X, int Y, String type, LinkedList<Node> voisin){
        this.coordX = X;
        this.coordY = Y;
        this.TypeOf = type;
        this.voisins = voisin;
    }

    /**
     * Reinitialise les informations utilisées dans l'algorithme de plus court chemin
     */
    public void resetGrapheInfo(){
        this.Heuristiccost = 0;
        this.closed = false;
        this.isEnd = false;
        this.isStart = false;
        this.bestHeuristicParent = null;
    }

    public void Affichage(){
        System.out.println(this.getTypeOf() + "  :  " + this.getCoordY() + " ; " + this.getCoordX());
    }

    /**
     * @return the coordX
     */
    public int getCoordX() {
        return coordX;
    }

    /**
     * Ajoute un element en parametre a la liste de voisin
     * @param element voisin a ajouté
     */
    public void addVoisin(Node element){
        this.getVoisin().add(element);
    }
    /**
     * @param coordX the coordX to set
     */
    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }

    /**
     * @return the coordY
     */
    public int getCoordY() {
        return coordY;
    }

    /**
     * @param coordY the coordY to set
     */
    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }

    /**
     * @return the voisin
     */
    public LinkedList<Node> getVoisin() {
        return this.voisins;
    }

    /**
     * @param voisin the voisin to set
     */
    public void setVoisin(LinkedList voisin) {
        this.voisins = voisin;
    }

    /**
     * @return the TypeOf
     */
    public String getTypeOf() {
        return TypeOf;
    }

    /**
     * @param TypeOf the TypeOf to set
     */
    public void setTypeOf(String TypeOf) {
        this.TypeOf = TypeOf;
    }

}
