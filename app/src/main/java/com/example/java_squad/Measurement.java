package com.example.java_squad;

import java.util.Date;
/**
 * Measurement class.
 */
public class Measurement extends Trial{
    private String unit;
    private double amount;
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
    public Measurement(String experimenter, String trialID, Integer enableGeo, Double longitude, Double latitude, String unit, double amount) {
        super(experimenter,trialID, enableGeo, longitude, latitude);
        this.unit = unit;
        this.amount = amount;
    }
    /**
     * An empty constructor for Measurement
     */
    public Measurement(){}

    /**
     * get experiment unit
     * @return
     * Returns the unit
     */
    public String getUnit() {
        return unit;
    }
    /**
     * set experiment result
     * @param unit
     * The unit of the trail
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }
    /**
     * get experiment amount
     * @return
     * Returns the value of the trial
     */
    public double getAmount() {
        return amount;
    }
    /**
     * set experiment amount
     * @param amount
     * The experiment outcome of the trial
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }
}
