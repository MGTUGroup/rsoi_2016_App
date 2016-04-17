package org.order.container;

/**
 * Created by user on 13.04.16.
 */
public class TaxiData {
    private int x;
    private int y;
    private boolean calculatoin = false;
    private double lengthWay = 0;
    private Integer pass_id;
    private double calcWay(int x1, int y1, int x2, int y2){
        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    public TaxiData(int x, int y){
        this.x = x;
        this.y = y;
    }


    public void addCoordinate(int x, int y){
        if(calculatoin)
            lengthWay += calcWay(this.x, this.y, x, y);
        this.x = x;
        this.y = y;
    }

    public int getX(){ return x;}
    public void setX(int x){ this.x = x; }

    public int getY(){ return y;}
    public void setY(int y){ this.y = y; }

    public boolean isCalculation(){ return calculatoin;}
    public void setCalculatoin(boolean flag){ this.calculatoin = flag; }

    public double getLenghtWay(){ return lengthWay;}
    public void setLengthWay(int lenght){ this.lengthWay = lenght; }

    public Integer getPassengerId(){ return pass_id;}
    public void setPassengerId(Integer id){ this.pass_id = id; }




}
