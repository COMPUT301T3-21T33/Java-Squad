package com.example.java_squad.user;


import com.example.java_squad.Experimental;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private static User user;
    private String username;
    private String contact;
    private String userID;

    public User(){}

    public static User getUser(){
        if (user == null){
            user = new User();
        }
        return user;
    }

    ArrayList<Experimental> ownedExperimentList;
    ArrayList<Experimental> followedExperimentList;

    public User(String username, String contact, String userID) {
        this.username = username;
        this.contact = contact;
        this.userID = userID;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getContact(){
        return contact;
    }

    public void setContact(String contact){
        this.contact = contact;
    }

    public String getUserID(){
        return userID;
    }

    public void setUserID(String userID){
        this.userID = userID;
    }

    public ArrayList<Experimental> getOwnedExperimentList(){
        return ownedExperimentList;
    }

    public void setOwnedExperimentList(ArrayList<Experimental> ownedExperimentList){
        this.ownedExperimentList = ownedExperimentList;
    }

    public ArrayList<Experimental> getFollowedExperimentList(){
        return followedExperimentList;
    }

    public void setFollowedExperimentList(ArrayList<Experimental> followedExperimentList){
        this.followedExperimentList = followedExperimentList;
    }




}
