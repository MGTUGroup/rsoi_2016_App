package org.tx.serializable;

/**
 * Created by user on 14.04.16.
 */
public class RadiusCoordinate {
    private Integer radius;
    private Coordinate coordinate;

    public RadiusCoordinate(){}

    public RadiusCoordinate(int radius, Coordinate coordinate){
        this.radius = radius;
        this.coordinate = coordinate;
    }

    public Coordinate getCoordinate(){ return coordinate; }
    public void setCoordinate(Coordinate coordinate){ this.coordinate = coordinate;}

    public Integer getRadius(){ return radius;}
    public void setRadius(int radius){ this.radius = radius;}

    public boolean chaeckFields(){
        return coordinate != null && radius != null;
    }
}
