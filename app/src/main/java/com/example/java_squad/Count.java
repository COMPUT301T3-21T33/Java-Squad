package com.example.java_squad;

import java.util.Date;

public class Count extends Trial{
    private String object;
    private Integer count;

    public Count(String experimenter, Date experiment_date, String object, double count) {
        super(experimenter, experiment_date);
        this.object = object;
        this.count = count;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
