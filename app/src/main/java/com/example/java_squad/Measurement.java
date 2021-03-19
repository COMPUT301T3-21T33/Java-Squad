package com.example.java_squad;

import java.util.Date;

public class Measurement extends Trial{
    private String unit;
    private double amount;

    public Measurement(String experimenter, Date experiment_date, String unit, double amount) {
        super(experimenter, experiment_date);
        this.unit = unit;
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
