package Model;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by thibault on 22/01/16.
 */

public class Score_Tab extends Model implements Serializable {
    private ArrayList<Score> tab;

    public Score_Tab(){
        tab = new ArrayList<Score>();
    }

    public Score_Tab( String path ) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(path);
        ObjectInputStream o = new ObjectInputStream(fis);
        this.setScore_Tab_tab((ArrayList<Score>) o.readObject());
        fis.close();
    }

    public Iterator<Score> iterator(){
        return tab.iterator();
    }

    public Integer getScore(int n) throws NoMoreScoreException {
        assert(!tab.isEmpty());
        try {
            return tab.get(n).getScore().getRight();
        }catch(IndexOutOfBoundsException e){
            throw new NoMoreScoreException();
        }
    }

    public void add_Score(Score s){
        Iterator<Score> it = iterator();
        int pos=0;
        if(tab.isEmpty()) {
            tab.add(s);
        }else {
            Score tmp;
            while(it.hasNext()){
                tmp = it.next();
                if(tmp.getScore_Score().intValue() < s.getScore_Score().intValue()){
                    tab.add(pos,s);
                    return;
                }else{
                    if(tmp.getScore_Score().intValue() == s.getScore_Score().intValue()){
                        tab.add(pos+1,s);
                        return;
                    }
                }
                pos++;
            }
        }
    }

    public ArrayList<Score> getScore_Tab_tab(){
        return tab;
    }

    public void setScore_Tab_tab(ArrayList<Score> al){
        tab=al;
    }

    public void writeScore_Tab(String path) throws IOException {
        FileOutputStream fos = new FileOutputStream(path);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        System.out.println();
        oos.writeObject(this.getScore_Tab_tab());
    }

    public String toString(){
        String s="";
        Iterator<Score> it = iterator();
        int pos=0;
        Score tmp;
        while (it.hasNext()) {
            tmp=it.next();
            s+=(pos+1)+"."+tmp.toString()+"\n";
        }
        return s;
    }
}
