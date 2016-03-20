package Model;

import java.util.LinkedList;

/**
 * Classe contenant une methode static pour la recherche du plus court chemin
 * entre deux intersections
 *
 */
public class RechercheChemin {

    public LinkedList<Node> IntersectionList;

    public RechercheChemin(LinkedList<Node> list) {
        this.IntersectionList = new LinkedList((LinkedList<Node>) list.clone());
    }

    /**
     * Cherche dans la liste de node celle qui est classé comme node de depart
     *
     * @return node "depart" de la liste de node
     */
    public Node getStartNode() {
        for (Node a : IntersectionList) {
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
    private void Explore(Node n) {
        n.noeud.closed = true;
        for (Node voisin : n.noeud.getVoisin()) {
            if (voisin.noeud.isEnd) {
                if (!voisin.noeud.closed) {
                    voisin.noeud.bestHeuristicParent = n.noeud;
                    voisin.noeud.Heuristiccost = n.noeud.Heuristiccost + voisin.distance;
                    voisin.noeud.closed = true;
                    return;
                } else if (voisin.noeud.Heuristiccost > n.noeud.Heuristiccost + voisin.distance) {

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
     * @return Renvoi la prochaine intersection à atteindre pour arriver à cet
     * objectif
     */
    private NoeudGraphe ExtractPath(Node depart, Node objectif) {
        LinkedList<NoeudGraphe> chemin = new LinkedList<>();
        NoeudGraphe n = objectif.noeud;
        while (n != depart.noeud && n.bestHeuristicParent != null) {
            chemin.addFirst(n);
            n = n.bestHeuristicParent;
        }
        if (!chemin.isEmpty()) {
            return chemin.getFirst();
        } else {
            return depart.noeud;
        }
    }

    /**
     * Change le noeud de départ de l'algorithme en supprimant la node
     * précédement visitée par une entité ( pour évité un retour sur ses pas )
     *
     * @param depart noeud de départ de l'algorithme.
     * @param actual Insance de l'entité utilisant l'algorithme.
     * @return le noeud retiré au noeud de départ ( pour pouvoir le réintégré a
     * la fin de l'exécution de l'algorithme )
     */
    private Node setDepart(Node depart, Ghost actual) {
        Node remember = null;
        if (actual.lastVisited != null && depart.noeud.getVoisin().size() > 1) {
            for (int i = 0; i < depart.noeud.getVoisin().size(); i++) {
                if (depart.noeud.getVoisin().get(i).compare(actual.lastVisited)) {
                    remember = depart.noeud.getVoisin().get(i);
                    depart.noeud.getVoisin().remove(i);
                    return remember;
                }
            }
        }
        return remember;
    }

    /**
     * Méthode static public, lance le processus de detection du chemin le plus
     * court entre les deux nodes en parametre
     *
     * @param depart node de départ pour de l'algorithme de recherche
     * @param objectif node d'arrivée pour de l'algorithme de recherche
     * @param actual ghost sur lequel est lancé l'algo
     * @return la prochaine intersection a atteindre pour arrivée a l'objectif (
     * progression pas a pas vers la solution )
     */
    public NoeudGraphe DiscoverPath(Node depart, Node objectif, Ghost actual) {
        int X = actual.getMonster_Case_X(actual.x);
        int Y = actual.getMonster_Case_Y(actual.y);
        resetInfoGraphe();
        depart.noeud.isStart = true;
        objectif.noeud.isEnd = true;
        if (depart.compare(objectif)) {
            actual.lastVisited = depart;
            return depart.noeud.getVoisin().getFirst().noeud;
        } else {
            Node memory = setDepart(depart, actual);
            Explore(depart);

            NoeudGraphe solution = ExtractPath(depart, objectif);
            if (memory != null) {
                depart.noeud.getVoisin().add(memory);
            }
            actual.lastVisited = depart;
            return solution;
        }
    }

    /**
     * Reinitialise toute les informations nécéssaire au fonctionnement de
     * l'algorithme, coût heuristique, meuilleur père etc...
     */
    private void resetInfoGraphe() {
        for (Node a : IntersectionList) {
            a.noeud.resetGrapheInfo();
            for (Node voisin : a.noeud.getVoisin()) {
                voisin.noeud.resetGrapheInfo();
            }
        }
    }

}
