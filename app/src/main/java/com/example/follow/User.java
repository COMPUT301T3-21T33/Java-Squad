package com.example.follow;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String email;
    private ArrayList<String> followedExperiment = new ArrayList<>();

    public User(String username, String email, ArrayList<String> followedExperiment) {
        this.username = username;
        this.email = email;
        this.followedExperiment = followedExperiment;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<String> getFollowedExperiment() {
        return followedExperiment;
    }

    public void setFollowedExperiment(ArrayList<String> followedExperiment) {
        this.followedExperiment = followedExperiment;
    }
}
