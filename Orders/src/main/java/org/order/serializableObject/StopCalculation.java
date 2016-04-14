package org.order.serializableObject;

/**
 * Created by user on 14.04.16.
 */
public class StopCalculation {
    private Integer pass_id;

    public StopCalculation(){}

    public StopCalculation(Integer pass_id){
        this.pass_id = pass_id;
    }

    public Integer getPass_id(){ return pass_id;}
    public void setPass_id(Integer pass_id){ this.pass_id = pass_id;}

    public boolean checkFields() {
        return pass_id != null;
    }
}
