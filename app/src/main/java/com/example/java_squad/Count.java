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
     * @param experiment_date
     * Date to create the new trial
     * @param enableGeo
     * Indicator to check if the geo-location is enabled for this experiment.
     * @param object
     * The object for experiment
     * @param count
     * The experiment result
     */
    public Count(String experimenter, Date experiment_date, Integer enableGeo, String object, Integer count) {
        super(experimenter, experiment_date, enableGeo);
        this.object = object;
        this.count = count;
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
