package org.order.serializableObject;

/**
 * Created by user on 11.04.16.
 */
public class Coordinate {
    private int x;
    private int y;

    public Coordinate(){}

    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }
    public int getX(){ return x;}
    public void setX(int x){ this.x = x;}

    public int getY(){ return y;}
    public void setY(int y){ this.y = y;}



}
