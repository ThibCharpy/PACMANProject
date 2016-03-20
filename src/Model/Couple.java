package Model;

import java.io.Serializable;

public class Couple<E,F> implements Serializable{
    private E left;
    private F right;

    
    public Couple(){
        left = null;
        right = null;
    }

    public Couple(E e, F f){
        left = e;
        right = f;
    }

    public Couple(Couple<E,F> c){
        left = c.left;
        right = c.right;
    }

    public E getLeft(){
        return left;
    }

    public F getRight(){
        return right;
    }

    public void setLeft(E e){
        left = e;
    }

    public void setRight(F f){
        right = f;
    }
}
