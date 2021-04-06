package com.example.java_squad;

import java.io.Serializable;
import java.util.Date;
/**
 * Trial class. The parent class for all other trial classes
 */
public class Trial implements  Serializable{
    private String experimenter;
    private Date experiment_date;
    private Integer enableGeo;
    /**
     * Constructor for Binomial class.
     * @param experimenter
     * Person who add the new trial to the experiment.
     * @param experiment_date
     * Date to create the new trial
     */
    public Trial(String experimenter, Date experiment_date,Integer enableGeo) {
        this.experimenter = experimenter;
        this.experiment_date = experiment_date;
        this.enableGeo = enableGeo;
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

}