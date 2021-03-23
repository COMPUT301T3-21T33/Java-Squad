package com.example.java_squad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Activity for displaying all of the experiments that are public.
 * Uses ExperimentCustomList.
 * Written by Michael Harbidge
 */
public class ExperimentList extends AppCompatActivity {

    ListView experimentList;
    ArrayAdapter<Experimental> experimentAdapter;
    ArrayList<Experimental> experiments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiment_list);

        experimentList = findViewById(R.id.experiment_list_view);
        experimentAdapter = new ExperimentCustomList(this, experiments);
    }
}