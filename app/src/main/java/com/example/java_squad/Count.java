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
     * @param object
     * The object for experiment
     * @param count
     * The experiment result
     */
    public Count(String experimenter,String trailID, String object, Integer count) {
        super(experimenter,trailID);
        this.object = object;
        this.count = count;
    }

    public Count() {
    }

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
