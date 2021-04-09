package com.example.java_squad;

import java.io.Serializable;
import java.util.Date;
/**
 * Trial class. The parent class for all other trial classes
 */
public class Trial{
    private String experimenter;
    private Integer enableGeo;
    private Double longitude;
    private Double latitude;

    private String trialID;
    /**
     * Constructor for Binomial class.
     * @param experimenter
     * Person who add the new trial to the experiment.
     * @param experiment_date
     * Date to create the new trial
     */
    public Trial(String experimenter, String trialID, Integer enableGeo,Double longitude, Double latitude) {
        this.experimenter = experimenter;
        this.trialID = trialID;
        this.enableGeo = enableGeo;
        this.longitude = longitude;
        this.latitude = latitude;
    }
    public Trial(boolean parseBoolean, String value, boolean parseBoolean1, String value1) {
    }

    public Trial(boolean parseBoolean, String value, float parseFloat, String value1) {
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }


    public Integer getEnableGeo() {
        return enableGeo;
    }

    public void setEnableGeo(Integer enableGeo) {
        this.enableGeo = enableGeo;
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
    /**
     * experiment_date getter
     */

    public void setTrialID(String trialID) {
        this.trialID = trialID;
    }
    public String getTrialID(){
        return trialID;
    }
}