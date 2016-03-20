package Model;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Score_Tab extends Model implements Serializable {

    private ArrayList<Score> tab;

    /**
     * Constructeur par défault de la classe Score_tab.
     */
    public Score_Tab() {
        tab = new ArrayList<>();
    }

    /**
     * Constructeur de la classe Score_tab.
     *
     * @param path Path vers le fichier de sauvegarde des scores.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Score_Tab(String path) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(path);
        ObjectInputStream o = new ObjectInputStream(fis);
        this.setScore_Tab_tab((ArrayList<Score>) o.readObject());
        fis.close();
    }

    /**
     * Itérateur sur la tableau de sauvegarde des scores.
     *
     * @return Itérateur sur tableau de sauvearde
     */
    public Iterator<Score> iterator() {
        return tab.iterator();
    }

    /**
     * Getter de Score.
     *
     * @param n place dans le tableau de score.
     * @return Score si il existe.
     * @throws NoMoreScoreException
     */
    public Score getScore(int n) throws NoMoreScoreException {
        assert (!tab.isEmpty());
        try {
            return tab.get(n);
        } catch (IndexOutOfBoundsException e) {
            throw new NoMoreScoreException();
        }
    }

    /**
     * Méthode d'ajout d'un Score dans le tableau de Score.
     *
     * @param s Score à ajouter.
     */
    public void add_Score(Score s) {
        Iterator<Score> it = iterator();
        int pos = 0;
        if (tab.isEmpty()) {
            tab.add(s);
        } else {
            Score tmp;
            while (it.hasNext()) {
                tmp = it.next();
                if (tmp.getScore_Score().intValue() < s.getScore_Score().intValue()) {
                    tab.add(pos, s);
                    return;
                } else if (tmp.getScore_Score().intValue() == s.getScore_Score().intValue()) {
                    tab.add(pos + 1, s);
                    return;
                }
                pos++;
            }
        }
    }

    
    public ArrayList<Score> getScore_Tab_tab() {
        return tab;
    }

    public void setScore_Tab_tab(ArrayList<Score> al) {
        tab = al;
    }

    /**
     * Méthode sauvegardant tab dans un fichier texte.
     * @param path Path vers le fichier de sauvegarde.
     * @throws IOException 
     */
    public void writeScore_Tab(String path) throws IOException {
        FileOutputStream fos = new FileOutputStream(path);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        System.out.println();
        oos.writeObject(this.getScore_Tab_tab());
        oos.close();
        fos.close();
    }

    public String toString() {
        String s = "";
        Iterator<Score> it = iterator();
        int pos = 0;
        Score tmp;
        while (it.hasNext()) {
            tmp = it.next();
            s += (pos + 1) + "." + tmp.toString() + "\n";
        }
        return s;
    }
}
