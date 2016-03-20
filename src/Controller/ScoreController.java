package Controller;

import Model.*;
import View.View;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ScoreController extends Controller {

    private final String path_Field;

    private Score_Tab st;

    /**
     * Constructeur de la classe ScoreController
     * @param v vue associer a l'affichage du score
     * @param path chemin vers le fichier de sauvegarde des scores
     */
    public ScoreController(View v, String path){
        super(v);
        File f = new File(path);
        if(f.exists()){
            try {
                System.out.println("Fichier trouvé => ouverture fichier");
                st = new Score_Tab(path);
            } catch (IOException | ClassNotFoundException e) {
            }
        }else{
            st = new Score_Tab();
        }
        path_Field = path;
    }

    /**
     * Méthode gérant l'action lié a l'appui sur un bouton
     * @param s Stage lié au bouton
     * @param v View lié au bouton
     */
    @Override
    public void btn_Action(Stage s, View v){
        v.start(s);
    }

    /**
     * Méthode gérant la création du fichier de sauvegarde des scores si il n'existe pas, sauvegarde le score.
     */
    public void saveScore(){
        File f = new File(path_Field);
        if(!(f.exists())){
            Path myFile = Paths.get(path_Field);
            try {
                Path file = Files.createFile(myFile);
            } catch (IOException e) {
            }
        }
        try {
            st.writeScore_Tab(path_Field);
        } catch (IOException e) {
        }
    }

    /**
     * Getter de score
     * @param n
     * @return 
     */
    public Score getSt(int n) {
        try {
            return st.getScore(n);
        } catch (NoMoreScoreException nme) {
            return null;
        }
    }
}
