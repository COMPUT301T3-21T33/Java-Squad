package com.example.java_squad;

import java.util.Date;
/**
 * Count class.
 */
public class Count extends Trial{
    private String object;
    private Integer count;
    /**
     * Constructor for Count class.
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
    public Count(String experimenter, String trialID, Integer enableGeo, Double longitude, Double latitude, String object, Integer count) {
        super(experimenter, trialID, enableGeo, longitude, latitude);
        this.object = object;
        this.count = count;
    }
    /**
     * An empty constructor for Count
     */
    public Count(){}


    /**
     * Returns the experiment object.
     * @return
     * Returns the object
     */
    public String getObject() {
        return object;
    }
    /**
     * set experiment object
     * @param object
     * the object of the experiment trial
     */
    public void setObject(String object) {
        this.object = object;
    }
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
