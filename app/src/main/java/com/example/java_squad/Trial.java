package com.example.java_squad;

import java.io.Serializable;
import java.util.Date;
/**
 * Trial class. The parent class for all other trial classes
 */
public class Trial implements Serializable{
    private String experimenter;
    private Integer enableGeo;
    private Double longitude;
    private Double latitude;

    private String trialID;
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
    /**
     * Empty Constructor for Binomial class.
     */
    public Trial(){}
    /**
     * Returns the longitude of the trial.
     * @return
     * Return longitude in Double
     */
    public Double getLongitude() {
        return longitude;
    }
    /**
     * Changes the longitude of the location of the trial.
     * @param longitude
     * Double longitude
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    /**
     * Returns the latitude of the trial.
     * @return
     * return latitude in Double
     */
    public Double getLatitude() {
        return latitude;
    }
    /**
     * Changes the latitude of the location of the trial.
     * @param latitude
     * Double latitude
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * Returns an integer to check if the geo location is required.
     * @return
     * Return int 0 if geo location is disabled and 1 if the location is enabled
     */
    public Integer getEnableGeo() {
        return enableGeo;
    }
    /**
     * Changes the latitude of the location of the trial.
     * @param enableGeo
     * Int 0 if geolocation is disabled, 1 if geolocation is enabled
     */
    public void setEnableGeo(Integer enableGeo) {
        this.enableGeo = enableGeo;
    }

    /**
     * Returns the experimenter's name in String type.
     * @return
     * Returns experimenter's name
     */
    public String getExperimenter() {
        return experimenter;
    }
    /**
     * Set experimenter of the trial
     * @param experimenter
     * String of the experimenter's name
     */
    public void setExperimenter(String experimenter) {
        this.experimenter = experimenter;
    }

    /**
     * Set TrialID for the trial
     * @param trialID
     * The unique trialID
     */
    public void setTrialID(String trialID) {
        this.trialID = trialID;
    }

    /**
     * Returns the unique TrialID for the trial
     * @return
     * Returns trial ID in String
     */
    public String getTrialID(){
        return trialID;
    }
}