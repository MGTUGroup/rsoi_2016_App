package org.psg.serializable;

/**
 * Created by user on 14.04.16.
 */
public class ChangeStatus {
    private Integer value;
    private String status;

    public ChangeStatus(){}

    public ChangeStatus(Integer value, String status){
        this.value = value;
        this.status = status;
    }
    public int getValue(){ return value;}
    public void setValue(int value){ this.value = value;}

    public String getStatus(){ return status;}
    public void setStatus(String status){ this.status = status;}

    public boolean checkFields(){
        return value != null && status != null;
    }

}
