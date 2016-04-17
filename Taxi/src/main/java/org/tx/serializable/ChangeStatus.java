package org.tx.serializable;

/**
 * Created by user on 14.04.16.
 */
public class ChangeStatus {
    private Integer user_id;
    private String status;

    public ChangeStatus(){}

    public ChangeStatus(Integer user_id, String status){
        this.user_id = user_id;
        this.status = status;
    }
    public int getUser_id(){ return user_id;}
    public void setUser_id(int value){ this.user_id = value;}

    public String getStatus(){ return status;}
    public void setStatus(String status){ this.status = status;}

    public boolean checkFields(){
        return user_id != null && status != null;
    }

}
