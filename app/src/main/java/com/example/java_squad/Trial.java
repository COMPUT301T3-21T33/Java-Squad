package com.example.java_squad;

import java.io.Serializable;
import java.util.Date;

public class Trial implements  Serializable{
    private String experimenter;
    private Date experiment_date;

    public Trial(String experimenter, Date experiment_date) {
        this.experimenter = experimenter;
        this.experiment_date = experiment_date;
    }

    public String getExperimenter() {
        return experimenter;
    }

    public void setExperimenter(String experimenter) {
        this.experimenter = experimenter;
    }

    public Date getExperiment_date() {
        return experiment_date;
    }

    public void setExperiment_date(Date experiment_date) {
        this.experiment_date = experiment_date;
    }
}