package com.example.java_squad;

import java.util.Date;

public class Binomial extends Trial{
    private String result;

    public Binomial(String experimenter, Date experiment_date, String result) {
        super(experimenter, experiment_date);
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
