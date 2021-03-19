package com.example.java_squad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

/**
 * Activity for entering info for a new experiment or updating an old one.
 */
public class ExperimentConstructor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiment_constructor);
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
        //still being worked on.
    }
}