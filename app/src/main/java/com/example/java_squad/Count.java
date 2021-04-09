package com.example.java_squad;

import java.util.Date;
/**
 * Count class.
 */
public class Count extends Trial{
    private String object;
    private Integer count;

    public Count(String experimenter, String trialID, Integer enableGeo, Double longitude, Double latitude, String object, Integer count) {
        super(experimenter, trialID, enableGeo, longitude, latitude);
        this.object = object;
        this.count = count;
    }

//    public Count(String s, Object o, String s1, Integer integer) {
//        super();
//    }

    /**
     * Constructor for Count class.
     * @param experimenter
     * Person who add the new trial to the experiment.
     * @param object
     * The object for experiment
     * @param count
     * The experiment result
     */

    /**
     * get experiment object
     */
    public String getObject() {
        return object;
    }
    /**
     * set experiment object
     */
    public void setObject(String object) {
        this.object = object;
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
