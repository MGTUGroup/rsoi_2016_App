package org.order.serializableObject;

/**
 * Created by user on 11.04.16.
 */

public class MakeOrder {

    private Integer pass_id;
    private Coordinate coordinate;

    public MakeOrder(){}

    public MakeOrder(Integer pass_id, Coordinate cr){
        this.pass_id = pass_id;
        this.coordinate = cr;
    }

    public Integer getPass_id(){ return pass_id;}
    public void setPass_id(Integer id){ this.pass_id = id;}

    public Coordinate getCoordinate(){ return coordinate;}
    public void setCoordinate(Coordinate cr){ this.coordinate = cr;}

    public boolean checkFields(){
        return (pass_id != null && coordinate != null);
    }
}
