package com.example.java_squad;

import com.example.java_squad.user.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Experimental class. Handles / Models everything for experiments aside from their statistics and questions
 * !!Renamed to Experimental to merge code and work with search functionality
 * WIP. Written by Michael Harbidge
 */
public class Experimental implements Serializable {
    //Owner is assigned when constructing the Experiment, then cannot be changed.
    private User owner;
    private String name = "";
    private String description = "";
    private String rules = "";
    private String expID;

    //private Location location
    //dont know how to implement this yet

    //whether the experiment can still be contributed to.
    private boolean active = true;
    //visibility to other users.
    private boolean published = false;

    //users who have subscribed to the project
    private List<User> subscibers = new ArrayList<>();
    //users who have contributed to the project (may not be needed)
    private List<User> contributors = new ArrayList<>();

    //Type of trials recorded by experiment.
    private int type;

    //minimum number of trials before results are considered
    private int minTrials;
    public ArrayList<Trial> trials = new ArrayList<>();

    private List<Question> questions = new ArrayList<>();

    /**
     * Constructor for Experiment class. Still WIP, as location is not added, and there is no input validation.
     * Experiment starts as active but not published. Can be changed after.
     * @param owner
     * User who owns the experiment. Normally cannot be changed after construction.
     * @param description
     * Description of the experiment.
     * @param rules
     * Rules of the experiment.
     * @param minTrials
     * Minimum number of trials for the results/stats to be calculated.
     */
    Experimental(User owner,String name, String description, String rules, int type, int minTrials, String expID){
        this.name = name;
        this.owner = owner;
        this.description = description;
        this.rules = rules;
        this.type = type;
        this.minTrials = minTrials;
        this.expID = expID;
    }

    public Experimental() {

    }

    public String getOwnerName() { return owner.getUsername(); };

    /**
     * Updates name of the experiment.
     * @param newName
     * the new name, in a string.
     */
    public void setName(String newName) { name = newName; }

    public String getName() { return name; }

    /**
     * Updates the description with a new string.
     * @param newDesc
     * New description of the experiment.
     */
    public void setDescription(String newDesc){ description = newDesc; }

    public String getDescription(){ return description; }

    /**
     * Updates the rules of the experiment
     * @param newRules
     * New rules string.
     */
    public void setRules(String newRules){ rules = newRules; }

    public String getRules(){ return rules; }

    /**
     * Sets the minimum number of trials before results are considered.
     * @param newTrials
     * number of trials.
     */
    public void setMinTrials(int newTrials) { minTrials = newTrials;}

    public int getMinTrials() {return minTrials;}

    /**
     * changes the type of trials recorded by the experiment.
     * !!!SHOULD NOT BE CALLED AFTER TRIALS ARE RECORDED!!!
     * @param newType
     * type of trials to record in integer format.
     * 0 = Count (how many did you see)
     * 1 = Binomial Trial (Pass / Fail)
     * 2 = non-negative integer counts (each trial has 0 or more)
     * 3 = measurement trials (like the temperature)
     */
    public void setType(int newType) { type = newType; }

    /**
     * Returns the type of trial of the experiment.
     * @return
     * type of trials to record in integer format.
     * 0 = Count (how many did you see)
     * 1 = Binomial Trial (Pass / Fail)
     * 2 = non-negative integer counts (each trial has 0 or more)
     * 3 = measurement trials (like the temperature)
     */
    public int getType() { return type; }

    /**
     * Returns the type of trial of the experiment in string format.
     * @return
     * String with the type of trial.
     */
    public String getTypeString() {
        if (type == 0)
            return "Count";
        else if (type == 1)
            return "Binomial";
        else if (type == 2)
            return "Non-Negative Integer Count";
        else if (type == 3)
            return "Measurement";

        return "Error, invalid type";
    }

    /**
     * Changes the public visibility of the experiment. Experiment List looks at publicity and lists
     * the experiment if it is published.
     * @param published
     * Boolean published (true) or not (false)
     */
    public void setPublished(boolean published){
        this.published = published;
    }

    /**
     * Returns publicity of the Experiment.
     * @return
     * Boolean published
     */
    public boolean getPublished(){
        return published;
    }

    /**
     * Ends the experiment, meaning no more results can be contributed.
     */
    public void endExperiment(){
        active = false;
    }

    public Boolean getActive() { return active; }

    /**
     * subscribes a user to the experiment. Must not already be subscibed to the experiment.
     * @param user
     * User to subscribe.
     */
    public void addSubscriber(User user){
        if (subscibers.contains((User) user))
            throw new IllegalArgumentException();
        else
            subscibers.add(user);
    }

    /**
     * unsubscribes a user from the exeperiment. Must be already subscribed.
     * @param user
     * User to unsubscribe.
     */
    public void removeSubsciber(User user){
        if (!subscibers.contains((User) user))
            throw new IllegalArgumentException();
        else
            subscibers.remove((User) user);
    }

    /**
     * adds a contributor to the experiment.
     * @param user
     * User to add.
     */
    public void addContributor(User user){
        if (contributors.contains((User) user))
            throw new IllegalArgumentException();
        else
            contributors.add(user);
    }

    /**
     * removes a user from contributors list. DOES NOT REMOVE THEIR EXPERIMENTS YET
     * @param user
     * User to remove.
     */
    public void removeContributor(User user){
        if (!contributors.contains((User) user))
            throw new IllegalArgumentException();
        else
            contributors.remove((User) user);
    }

    public String getExpID() {
        return expID;
    }

}
