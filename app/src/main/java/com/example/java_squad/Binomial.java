package com.example.java_squad;

import java.util.Date;
/**
 * Binomial class.
 */
public class Binomial extends Trial{
    private String result;

    public Binomial(String experimenter,String trailID) {
        super(experimenter,trailID);
    }

    public Binomial() {
    }

    /**
     * Constructor for Binomial class.
     * @param experimenter
     * Person who add the new trial to the experiment.

     * @param result
     * The result of the trial, pass or fail
     */
    public Binomial(String experimenter,String trailID,String result) {
        super(experimenter, trailID);
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
