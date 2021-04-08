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

     * @param count
     * The result of the trial
     */
    public IntCount(String experimenter, String trailID,Integer enableGeo, Double longitude, Double latitude, Integer count) {
        super(experimenter, trailID,enableGeo, longitude, latitude);
        this.count = count;
    }
    /**
     * get experiment count
     */
    public Integer getCount() {
        return count;
    }
    /**
     * set experiment count
     */
    public void setCount(Integer count) {
        this.count = count;
    }
}
