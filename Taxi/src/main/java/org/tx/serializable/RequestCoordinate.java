package org.tx.serializable;

/**
 * Created by user on 14.04.16.
 */
public class RequestCoordinate {
    private Integer user_id;
    private Coordinate coordinate;

    public RequestCoordinate(){}

    public RequestCoordinate(Integer user_id, Coordinate coordinate){
        this.user_id = user_id;
        this.coordinate = coordinate;
    }

    public Integer getUser_id(){ return user_id;}
    public void setUser_id(Integer user_id){ this.user_id = user_id;}

    public Coordinate getCoordinate(){ return coordinate; }
    public void setCoordinate(Coordinate coordinate){ this.coordinate = coordinate;}

    public boolean chaeckFields(){
        return user_id != null&& coordinate != null;
    }



}
