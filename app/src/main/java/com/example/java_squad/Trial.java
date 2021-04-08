package com.example.java_squad;

import java.io.Serializable;
import java.util.Date;
/**
 * Trial class. The parent class for all other trial classes
 */
public class Trial implements  Serializable{
    private String experimenter;
    private String trailID;

    /**
     * Constructor for Binomial class.
     * @param experimenter
     * Person who add the new trial to the experiment.
     */

    public Trial(String experimenter, String trailID) {
    }

    public Trial() {
    }

    public String getTrailID() {
        return trailID;
    }

    public void setTrailID(String trailID) {
        this.trailID = trailID;
    }

    /**
     * experimenter getter
     */
    public String getExperimenter() {
        return experimenter;
    }
    /**
     * experimenter setter
     */
    public void setExperimenter(String experimenter) {
        this.experimenter = experimenter;
    }


}