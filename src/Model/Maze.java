package Model;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by thibaultgeoffroy on 25/02/2016.
 */

public class Maze {

    public static int[][] plateau;
    public static int[][] save_plateau;

    /**
     * Compte le nombre de 1 qui se situe sur les cases voisines (non située en
     * diagonale/ en forme de + en partant de i,x) des coordonées fourni en
     * paramètre
     *
     * @param i coordonnée rangée
     * @param x coordonnée colonne
     * @return nombre de 1 autour de i,x
     */
    public static int checkWall(int i, int x, int ValueToTest) {
        int cmpt = 0;
        for (int Subi = -1; Subi < 2; Subi++) {
            for (int Subx = -1; Subx < 2; Subx++) {
                // Exclusion des combinaison interdite ( diagonale )
                if ((Subi != Subx) && (Subi != Subx * -1)) {
                    // Test pour éviter sortie du tableau
                    if (((i + Subi > -1) && (i + Subi < plateau.length)) && ((x + Subx > -1) && (x + Subx < plateau[0].length))) {
                        if (plateau[i + Subi][x + Subx] == ValueToTest) {
                            cmpt++;
                        } else if (ValueToTest == 2) {
                            if (plateau[i + Subi][x + Subx] == 0 || plateau[i + Subi][x + Subx] == 3) {
                                cmpt++;
                            }
                        }
                    }
                }
            }
        }
        return cmpt;
    }

    /**
     * Construit les cases a l'intérieur de Gmap, possibilité d'initialisé gmap
     * ici, et vérifie le format du fichier txt.
     *
     * @param file fichier contenant un "tableau" de 0 et de 1
     * @throws IOException si FileNotFound
     */
    public static void initMapArray(String file) throws IOException {
        int[] data= testFileFormat(file);
        if (data[0] == 1) {
            plateau = new int[data[1]][data[2]];
            InputStream ips = Model.class.getResourceAsStream(file);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(ips))) {
                String line;
                int i = 0;
                while ((line = br.readLine()) != null) {
                    for (int index = 0; index < line.length(); index++) {
                        char aChar = line.charAt(index);
                        plateau[i][index] = (((int) (aChar)) - 48);
                    }
                    i++;
                }
                br.close();
            }
        }
        save_plateau = plateau.clone();
    }

    /**
     * Teste le format du fichier txt, les rangs du fichiers doivent tous etre
     * de meme longueur (ie: toute les rangées ont le meme nombre de colonnes)
     *
     * @param file fichier txt à testé
     * @return boolean vrai si fichier valide, faux sinon.
     * @throws IOException si FileNotFound
     */
    public static int[] testFileFormat(String file) throws IOException {
        int[] res = new int[3];
        int cmptLine;
        int RowLength;
        InputStream ips = Model.class.getResourceAsStream(file);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(ips))) {
            String line;
            cmptLine = 0;
            RowLength = -2;
            while ((line = br.readLine()) != null) {
                cmptLine++;
                if (RowLength == -2) {
                    RowLength = line.length();
                } else if (RowLength != line.length()) {
                    res[0] = 0;
                    return res;
                }
            }
            br.close();
        }
        res[0] = 1;
        res[1] = cmptLine;
        res[2] = RowLength;
        return res;

    }
}
