package com.example.java_squad;

/**
 * Class representing a Barcode and its associated Trial result for a specific experiment.
 * Currently the barcode is stored as a string which represents the information extracted from the barcode.
 * The trial represents a template version, where an instance of this trial result is added when someone scans the barcode (as experimenter).
 * Elements of this class should NOT be changed without creating a new instance of BarcodeTrial.
 */
public class BarcodeTrial {
    private String barcode;

    private Trial trialTemplate;

    private Experimental experiment;

    BarcodeTrial(String barcode, Trial trial, Experimental experiment){
        this.barcode = barcode;
        trialTemplate = trial;
        this.experiment = experiment;
    }

    public String getBarcode() { return barcode; }

    public Trial getTrialTemplate() {return trialTemplate; }

    /**
     * Adds an instance of the trial to the experiment.
     */
    public void addTrialToExp() {
        experiment.trials.add(trialTemplate);
    }
}
