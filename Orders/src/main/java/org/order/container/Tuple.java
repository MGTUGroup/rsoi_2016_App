package org.order.container;

/**
 * Created by user on 14.04.16.
 */
public class Tuple {
    private double a;
    private int b;


    public Tuple(double a, int b){
        this.a = a;
        this.b = b;
    }
    public double getA(){ return a;}
    public void setA(double a){ this.a = a; }

    public int getB(){ return b;}
    public void setB(int b){ this.b = b; }
}
