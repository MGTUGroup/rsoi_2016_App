package org.order.serializableObject;

/**
 * Created by user on 13.04.16.
 */
public class AddTaxi {
    private Integer pass_id;
    private Integer taxi_id;

    public AddTaxi(){}

    public AddTaxi(Integer pass_id, Integer taxi_id){
        this.pass_id = pass_id;
        this.taxi_id = taxi_id;
    }

    public Integer getPass_id(){ return pass_id;}
    public void setPass_id(Integer id){ this.pass_id = id;}

    public Integer getTaxi_id(){ return taxi_id;}
    public void setTaxi_id(Integer id){ this.taxi_id = id;}

    public boolean chaekFields(){
        return (pass_id != null && taxi_id != null);
    }
}
