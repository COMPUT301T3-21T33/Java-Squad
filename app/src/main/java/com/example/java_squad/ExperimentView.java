package com.example.java_squad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

public class ExperimentView extends AppCompatActivity {
    public Experimental currentExperiment;

    TextView expName;
    //needs expLocation once implemented
    //needs date
    TextView expOwner;
    TextView expType;
    ToggleButton expPublished;
    TextView expActive;
    TextView expDescription;
    TextView expRules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiment_view);

        expName = findViewById(R.id.exp_name);
        expOwner = findViewById(R.id.exp_owner);
        expType = findViewById(R.id.exp_type);
        expPublished = findViewById(R.id.toggleButton_published);
        expActive = findViewById(R.id.exp_active);
        expDescription = findViewById(R.id.exp_description);
        expRules = findViewById(R.id.exp_rules);

        expName.setText(currentExperiment.getName());
        //expOwner.setText();  Whatever the func is to get owners name
        //expType.setText(); needs work
        expPublished.setChecked(currentExperiment.getPublished());

        if (currentExperiment.getActive())
            expActive.setText("Status: In progress");
        else
            expActive.setText("Status: Finished");

        expDescription.setText(currentExperiment.getDescription());
        expRules.setText(currentExperiment.getRules());


    }

    public void togglePublished(View view) {
        if (currentExperiment.getPublished())
            currentExperiment.setPublished(false);
        else
            currentExperiment.setPublished(true);
    }

    public void endExperiment(View view) {
        currentExperiment.endExperiment();
    }
}