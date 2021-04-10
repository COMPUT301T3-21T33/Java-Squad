package com.example.java_squad;

public class QrCodeTrial {
    private String qrcode;

    private Trial trialTemplate;

    private Experimental experiment;

    QrCodeTrial(String qrcode, Trial trial, Experimental experiment) {
        this.qrcode = qrcode;
        trialTemplate = trial;
        this.experiment = experiment;
    }

    public String getQrcode() {
        return qrcode;
    }

    public Trial getTrialTemplate() {
        return trialTemplate;
    }

    /**
     * Adds an instance of the trial to the experiment.
     */
    public void addTrialToExp() {
        experiment.trials.add(trialTemplate);
    }
}
