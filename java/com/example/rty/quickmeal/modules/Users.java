package com.example.rty.quickmeal.modules;

/**
 * Created by rty on 12/18/17.
 */

public class Users {
    private String user;
    private String name;
    private String email;


    public Users() {

    }
    public Users(String user) {
        this.user= user;
    }
    public Users(String user, String name, String email) {
        this.user= user;
        this.name= name;
        this.email= email;
    }
    public String getUser(){
        return  user;
    }

    public void setUser(){
        this.user= user;
    }

    public String getName(){
        return  name;
    }

    public void setName(){
        this.user= user;
    }

    public String getEmail(){
        return  email;
    }

    public void setEmail(){
        this.email= email;
    }
}
