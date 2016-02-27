package Model;

import java.io.Serializable;

/**
 * Created by thibault on 28/01/16.
 */

public class Score implements Serializable {
    //un score est un couple de valeur
    //a gauche le nom de type String et à droite le score de type Integer
    private Couple<String,Integer> value;

    public Score(){
        value=null;
    }

    public Score(String name, Integer score){
        value = new Couple<String,Integer>(name,score);
    }

    public Couple<String, Integer> getScore(){
        return value;
    }

    public void setScore_Name(String name){
        value.setLeft(name);
    }

    public void setScore_Score(Integer score){
        value.setRight(score);
    }

    public void setScore(String name,Integer score){
        value.setLeft(name);
        value.setRight(score);
    }

    public String getScore_Name(){
        return value.getLeft();
    }

    public Integer getScore_Score(){
        return value.getRight();
    }

    public int compareTo(Score s){
        if(value.getRight()<s.getScore().getRight()){
            return -1;
        }else{
            if(value.getRight()==s.getScore().getRight()){
                return 0;
            }
            return 1;
        }
    }

    public String toString(){
        return value.getLeft()+":     "+value.getRight();
    }
}
