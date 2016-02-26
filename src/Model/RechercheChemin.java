package Model;

import java.util.LinkedList;

/**
 * Created by thibaultgeoffroy on 25/02/2016.
 */
public class RechercheChemin {
    static int nbNodeParcouru = 0; // outils de test
    static int nbCheminAlt = 0;

    /**
     * Cherche dans la liste de node celle qui est classé comme node de depart
     *
     * @return node "depart" de la liste de node
     */
    public static Node getStartNode() {
        for (Node a : ListOfIntersection.IntersectionList) {
            if (a.noeud.isStart) {
                return a;
            }
        }
        return null;
    }

    /**
     * Algorithme de recherche de plus court chemin : On explore chaque fils de
     * la node en parametre, si elle n'est pas fermé et que le cout pour
     * l'atteindre depuis la node de départ n'est pas inférieur on la passe.
     * Sinon on actualise le cout, on defini la node en parametre comme son
     * "père" ( dans le sens du chemin final ) et on relance la fonction sur ce
     * fils.
     *
     * @param n node a explorer
     */
    private static void Explore(Node n) {
        n.noeud.closed = true;
        nbNodeParcouru++;
        for (Node voisin : n.noeud.getVoisin()) {
            if (voisin.noeud.isEnd) {
                if (!voisin.noeud.closed) {
                    voisin.noeud.bestHeuristicParent = n.noeud;
                    voisin.noeud.Heuristiccost = n.noeud.Heuristiccost + voisin.distance;
                    voisin.noeud.closed = true;
                    return;
                } else if (voisin.noeud.Heuristiccost > n.noeud.Heuristiccost + voisin.distance) {
                    nbCheminAlt++; // Nombre de chemin different menant a la solution, pas tous optimaux
                    voisin.noeud.bestHeuristicParent = n.noeud;
                    voisin.noeud.Heuristiccost = n.noeud.Heuristiccost + voisin.distance;
                    return;
                }
            }
            if (!voisin.noeud.closed || voisin.noeud.Heuristiccost > n.noeud.Heuristiccost + voisin.distance) {
                voisin.noeud.bestHeuristicParent = n.noeud;
                voisin.noeud.Heuristiccost = n.noeud.Heuristiccost + voisin.distance;
                Explore(voisin);

            }

        }

    }

    /**
     * Extrait le chemin optimal en suivant les "bestHeuristicParent" a partir
     * de la node objectif.
     *
     * @param depart node de départ de l'algorithme de recherche
     * @param objectif node d'arrivée de l'algorithme de recherche
     * @return Une liste contenant le chemin a suivre pour arriver à l'objectif
     */
    private static LinkedList<NoeudGraphe> ExtractPath(Node depart, Node objectif) {
        LinkedList<NoeudGraphe> chemin = new LinkedList<>();
        NoeudGraphe n = objectif.noeud;
        while (n != depart.noeud) {
            chemin.addFirst(n);
            n = n.bestHeuristicParent;
        }
        return chemin;
    }

    /**
     * Méthode static public, lance le processus de detection du chemin le plus
     * court entre les deux nodes en parametre
     *
     * @param depart node de départ pour de l'algorithme de recherche
     * @param objectif node d'arrivée pour de l'algorithme de recherche
     * @return
     */
    public static LinkedList<NoeudGraphe> DiscoverPath(Node depart, Node objectif, Ghost actual) {
        resetInfoGraphe();
        depart.noeud.isStart = true;
        objectif.noeud.isEnd = true;
        Node remember = ExploreFirstIt(depart, actual);
        Explore(depart);
        LinkedList<NoeudGraphe> solution = ExtractPath(depart, objectif);
        if (remember != null) {
            depart.noeud.getVoisin().add(remember);
        }
        return solution;
    }

    /**
     * Reinitialise toute les informations nécéssaire au fonctionnement de
     * l'algorithme, coût heuristique, meuilleur père etc...
     */
    private static void resetInfoGraphe() {
        for (Node a : ListOfIntersection.IntersectionList) {
            a.noeud.resetGrapheInfo();
            for (Node voisin : a.noeud.getVoisin()) {
                voisin.noeud.resetGrapheInfo();
            }
        }
    }

    private static Node ExploreFirstIt(Node depart, Ghost actual) {
        int cmpt = 0;
        Node remember = null;
        if (actual.lastVisited != null) {
            for (Node element : depart.noeud.getVoisin()) {
                if (element.compare(actual.lastVisited)) {
                    remember = element;
                    depart.noeud.getVoisin().remove(cmpt);
                    return remember;
                }
                cmpt++;
            }
        }

        return remember;
    }

}
