package Controller;

import Model.*;
import View.View;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by thibault on 27/02/16.
 */
public class ScoreController extends Controller {

    private String path_Field;

    private Score_Tab st;

    public ScoreController(View v, String path){
        super(v);
        File f = new File(path);
        if(f.exists()){
            try {
                System.out.println("Fichier trouvé => ouverture fichier");
                st = new Score_Tab(path);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else{
            st = new Score_Tab();
        }
        path_Field = path;
    }

    public void btn_Action(Stage s, View v){
        v.start(s);
    }

    public void saveScore(){
        File f = new File(path_Field);
        if(!(f.exists())){
            System.out.println("Fichier non trouvé pour sauvegarde => Creation score.txt");
            Path myFile = Paths.get(path_Field);
            try {
                Path file = Files.createFile(myFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Sauvegarde Liste dans score.txt");
        try {
            st.writeScore_Tab(path_Field);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Score getSt(int n) {
        try {
            return st.getScore(n);
        } catch (NoMoreScoreException nme) {
            return null;
        }
    }
}
