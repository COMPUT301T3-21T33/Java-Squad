package com.example.java_squad;

import java.util.ArrayList;
import java.util.List;

/**
 * Experiment class. Handles / Models everything for experiments aside from their statistics and questions
 * WIP. Written by Michael Harbidge
 */
public class Experiment {
    //Owner is assigned when constructing the Experiment, then cannot be changed.
    private User owner;
    private String description = "";
    private String rules = "";

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

    //minimum number of trials before results are considered
    private int minTrials;
    private List<Trial> trials = new ArrayList<>();

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
    Experiment(User owner, String description, String rules, int minTrials){
        this.owner = owner;
        this.description = description;
        this.rules = rules;
        this.minTrials = minTrials;
    }

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

}
