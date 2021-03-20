package com.example.java_squad.user;


import com.example.java_squad.Experiment;

import java.util.ArrayList;

public class User{
    private String username;
    private String contact;
    private String userID;

    ArrayList<Experiment> ownedExperimentList;
    ArrayList<Experiment> followedExperimentList;

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

    public ArrayList<Experiment> getOwnedExperimentList(){
        return ownedExperimentList;
    }

    public void setOwnedExperimentList(ArrayList<Experiment> ownedExperimentList){
        this.ownedExperimentList = ownedExperimentList;
    }

    public ArrayList<Experiment> getFollowedExperimentList(){
        return followedExperimentList;
    }

    public void setFollowedExperimentList(ArrayList<Experiment> followedExperimentList){
        this.followedExperimentList = followedExperimentList;
    }




}
