package com.example.java_squad;

import java.util.Date;

public class IntCount extends Trial{
    private Integer count;

    public IntCount(String experimenter, Date experiment_date, Integer count) {
        super(experimenter, experiment_date);
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
