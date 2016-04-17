package org.order.serializableObject;

/**
 * Created by user on 14.04.16.
 */
public class PostCoordinates {
    private Integer taxi_id;
    private Coordinate coordinate;

    public PostCoordinates(){}

    public PostCoordinates(Integer taxi_id, Coordinate coordinate){
        this.taxi_id = taxi_id;
        this.coordinate = coordinate;
    }

    public Integer getTaxi_id(){ return taxi_id;}
    public void setTaxi_id(Integer taxi_id){ this.taxi_id = taxi_id;}

    public Coordinate getCoordinate(){ return coordinate; }
    public void setCoordinate(Coordinate coordinate){ this.coordinate = coordinate;}

    public boolean chaeckFields(){
        return taxi_id != null&& coordinate != null;
    }

}
