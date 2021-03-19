package com.example.java_squad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

/**
 * Activity for entering info for a new experiment or updating an old one.
 */
public class ExperimentConstructor extends AppCompatActivity {

    EditText expName;
    EditText expDesc;
    EditText expRules;
    RadioGroup trialType;
    EditText minTrials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiment_constructor);

        expName = findViewById(R.id.editText_name);
        expDesc = findViewById(R.id.editText_description);
        expRules = findViewById(R.id.editText_rules);
        minTrials = findViewById(R.id.editText_minTrials);
    }

    /**
     * Called when the cancel button is pressed. Returns to the previous activity.
     * @param view
     * Button being pressed.
     */
    public void cancelButton(View view){
        finish();
    }

    public void submitButton(View view){
        String newName = expName.getText().toString();
        String newDesc = expDesc.getText().toString();
        String newRules = expRules.getText().toString();
        int newMinTrials = Integer.parseInt(minTrials.getText().toString());

        //not working yet
        //Experimental newExperiment = new Experimental(User owner, newName, newDesc, newRules, 0 , newMinTrials);
    }
}