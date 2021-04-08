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
     * @param unit
     * The measurement experiment unit
     * @param amount
     * The value of the trial
     */
    public Measurement(String experimenter,String trailID, String unit, double amount) {
        super(experimenter,trailID);
        this.unit = unit;
        this.amount = amount;
    }

    public Measurement(String unit, double amount) {
        this.unit = unit;
        this.amount = amount;
    }

    public Measurement() {
    }

    /**
     * get experiment unit
     */
    public String getUnit() {
        return unit;
    }
    /**
     * set experiment result
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }
    /**
     * get experiment amount
     */
    public double getAmount() {
        return amount;
    }
    /**
     * set experiment amount
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }
}
