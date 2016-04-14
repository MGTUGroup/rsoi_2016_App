package org.psg.serializable;

/**
 * Created by user on 14.04.16.
 */
public class Login {
    private String email;
    private String password;

    public Login(){}

    public Login(String email, String password){
        this.email = email;
        this.password = password;
    }
    public String getEmail(){ return this.email; }
    public void setEmail(String email){ this.email = email;}

    public String getPassword(){ return password;}
    public void setPassword(String password){ this.password = password;}

    public boolean checkFields(){
        return email != null && password != null;
    }
}
