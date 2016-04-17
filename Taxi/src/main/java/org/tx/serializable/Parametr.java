package org.tx.serializable;

/**
 * Created by user on 11.04.16.
 */
public class Parametr {

    private Integer user_id;

    public Parametr(){}

    public Parametr(Integer user_id){
        this.user_id = user_id;
    }

    public Integer getUser_id(){ return user_id;}
    public void setUser_id(Integer user_id){ this.user_id = user_id;}

    public boolean checkFields(){
        return user_id!=null;
    }

}
