package com.example.java_squad;

import java.util.Date;
/**
 * Binomial class.
 */
public class Binomial extends Trial{
    private String result;

    /**
     * Constructor for Binomial class.
     * @param experimenter
     * Person who add the new trial to the experiment.
     * @param trialID
     * The unique id for each trial
     * @param enableGeo
     * An integer indicating if the experiment requires geo location
     * @param longitude
     * longitude of geo location
     * @param latitude
     * latitude of geo location
     */

    public Binomial(String experimenter,  String trialID, Integer enableGeo, Double longitude, Double latitude, String result) {
        super(experimenter,trialID, enableGeo, longitude, latitude);
        this.result = result;
    }
    /**
     * An empty constructor for Binomial
     */
    public Binomial(){}
    /**
     * Returns the result of the experiment trial.
     * @return
     * Return the result
     */
    public String getResult() {
        return result;
    }
    /**
     * set experiment result
     * @param result
     * the result of the experiment trial
     */
    public void setResult(String result) {
        this.result = result;
    }
}
