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
     * @param experiment_date
     * Date to create the new trial
     * @param result
     * The result of the trial, pass or fail
     */

    public Binomial(String experimenter,  String trialID, Integer enableGeo, Double longitude, Double latitude, String result) {
        super(experimenter,trialID, enableGeo, longitude, latitude);
        this.result = result;
    }

    /**
     * get experiment result
     */
    public String getResult() {
        return result;
    }
    /**
     * set experiment result
     */
    public void setResult(String result) {
        this.result = result;
    }
}
