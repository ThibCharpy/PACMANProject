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

    private String path_For_Save="src/Model/";
    private String name_For_Save="score.txt";

    private Score_Tab st;

    public ScoreController(View v){
        super(v);
        try{
            st = new Score_Tab(path_For_Save+name_For_Save);
        }catch(FileNotFoundException fe){
            st = new Score_Tab();
            System.out.print("Fichier non trouvé..  ");
            System.out.println("=> Creation score.txt..");
            Path myFile = Paths.get(path_For_Save+name_For_Save);
            try {
                Path file = Files.createFile(myFile);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btn_Action(Stage s, View v){
        v.start(s);
    }

    public void saveScore(){
        try {
            st.writeScore_Tab(path_For_Save+name_For_Save);
        } catch (FileNotFoundException e) {
            System.out.println("Fichier non trouvé pour sauvegarde..");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int  getSt(int n) {
        Integer num=0;
        try {
            num = st.getScore(n);
            return num.intValue();
        } catch (NoMoreScoreException nme) {
            return -1;
        }
    }
}
