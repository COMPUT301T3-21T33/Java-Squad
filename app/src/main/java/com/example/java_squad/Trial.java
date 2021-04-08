package com.example.java_squad;

import java.io.Serializable;
import java.util.Date;
/**
 * Trial class. The parent class for all other trial classes
 */
public class Trial{
    private static String trialID;
    private String experimenter;
    private Date experiment_date;
    private Integer enableGeo;
    private Double longitude;
    private Double latitude;
    /**
     * Constructor for Binomial class.
     * @param experimenter
     * Person who add the new trial to the experiment.
     * @param experiment_date
     * Date to create the new trial
     */
    public Trial(String experimenter, Date experiment_date, Integer enableGeo, String trialID, Double longitude, Double latitude) {
        this.experimenter = experimenter;
        this.experiment_date = experiment_date;
        this.enableGeo = enableGeo;
        this.trialID = trialID;
        this.longitude = longitude;
        this.latitude = latitude;
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



    public Trial(boolean parseBoolean, String value, boolean parseBoolean1, String value1, String toString) {
    }

    public Trial(boolean parseBoolean, String value, float parseFloat, String value1, String toString) {
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
    public Date getExperiment_date() {
        return experiment_date;
    }
    /**
     * experiment_date setter
     */
    public void setExperiment_date(Date experiment_date) {
        this.experiment_date = experiment_date;
    }

    public static String getTrialID(){
        return trialID;
    }

}