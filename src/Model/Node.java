package Model;

/**
 * Created by thibaultgeoffroy on 25/02/2016.
 */
public class Node {
    /**
     * Noeud contenant les information sur une intersection du jeu
     */
    public NoeudGraphe noeud;

    /**
     * distance par rapport a son voisin
     */
    public int distance = 0;

    /**
     * Constructeur de node avec distance, utilsé pour les nodes secondaire ( ie : node voisine d'un NoeudGraphe )
     * @param a Noeud contenant les information sur une intersection du jeu
     * @param dist distance par rapport a son voisin
     */
    public Node(NoeudGraphe a, int dist){
        this.noeud = a;
        this.distance = dist;
    }

    /**
     * Constructeur de Node sans distance, utilisé pour les nodes principale
     * @param a Noeud contenant les information sur une intersection du jeu
     */
    public Node(NoeudGraphe a){
        this.noeud = a;
    }

    /**
     * Affichage des informations de la node ainsi que du noeud
     */
    public void Affichage(){
        System.out.println(this.noeud.getTypeOf() + "  :  " + this.noeud.getCoordY() + " ; " + this.noeud.getCoordX()+ " -> " + this.distance);
    }

    boolean compare(Node Pos) {
        return (this.noeud.getCoordX() == Pos.noeud.getCoordX() && this.noeud.getCoordY() == Pos.noeud.getCoordY());
    }
}
