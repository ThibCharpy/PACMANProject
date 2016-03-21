package Model;

import java.io.Serializable;

/**
 * Un score est un couple de valeur A gauche le nom de type String et à droite
 * le score de type Integer
 *
 */
public class Score implements Serializable {

    private Couple<String, Integer> value;

    /**
     * Constructeur par défaut de la classe Score
     */
    public Score() {
        value = null;
    }

    /**
     * Constructeur de la classe Score.
     *
     * @param name Nom associé au score.
     * @param score Score.
     */
    public Score(String name, Integer score) {
        value = new Couple<>(name, score);
    }

    public Couple<String, Integer> getScore() {
        return value;
    }

    public void setScore_Name(String name) {
        value.setLeft(name);
    }

    public void setScore_Score(Integer score) {
        value.setRight(score);
    }

    public void setScore(String name, Integer score) {
        value.setLeft(name);
        value.setRight(score);
    }

    public String getScore_Name() {
        return value.getLeft();
    }

    public Integer getScore_Score() {
        return value.getRight();
    }

    /**
     * Comparateur de score
     *
     * @param s score à comparer à this
     * @return -1 si this inférieur, 0 si égal, 1 si this suprérieur
     */
    public int compareTo(Score s) {
        if (value.getRight() < s.getScore().getRight()) {
            return -1;
        } else {
            if (value.getRight() == s.getScore().getRight()) {
                return 0;
            }
            return 1;
        }
    }

    public String toString() {
        return value.getLeft() + ":     " + value.getRight();
    }
}
