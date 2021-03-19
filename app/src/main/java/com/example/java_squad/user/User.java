package com.example.java_squad.user;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String ID;

    public User(String username, String ID){
        this.username = username;
        this.ID = ID;
    }

    public String getUsername(){
        return username;
    }

    public String getID(){
        return ID;
    }
}
