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
     * @param experiment_date
     * Date to create the new trial
     * @param count
     * The result of the trial
     */
    public IntCount(String experimenter, Date experiment_date, Integer enableGeo, Integer count) {
        super(experimenter, experiment_date, enableGeo);
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
