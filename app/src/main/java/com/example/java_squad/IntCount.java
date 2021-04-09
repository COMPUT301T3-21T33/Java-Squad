package com.example.java_squad;

import java.util.Date;
/**
 * IntCount class.
 */
public class IntCount extends Trial{
    private Integer count;
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
    public IntCount(String experimenter, String trialID,Integer enableGeo, Double longitude, Double latitude, Integer count) {
        super(experimenter, trialID,enableGeo, longitude, latitude);
        this.count = count;
    }
    /**
     * An empty constructor for IntCount
     */
    public IntCount(){}
    /**
     * Returns the int Count of the experiment trial.
     * @return
     * Return count value
     */
    public Integer getCount() {
        return count;
    }
    /**
     * set experiment count
     * @param count
     * the count of the experiment trial
     */
    public void setCount(Integer count) {
        this.count = count;
    }
}
