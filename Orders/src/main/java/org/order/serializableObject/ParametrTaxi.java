package org.order.serializableObject;

/**
 * Created by user on 11.04.16.
 */
public class ParametrTaxi {

    private Integer taxi_id;

    public ParametrTaxi(){}

    public ParametrTaxi(Integer taxi_id){
        this.taxi_id = taxi_id;
    }

    public Integer getTaxi_id(){ return taxi_id;}
    public void setTaxi_id(Integer taxi_id){ this.taxi_id = taxi_id;}

    public boolean checkFields(){
        return taxi_id!=null;
    }

}
