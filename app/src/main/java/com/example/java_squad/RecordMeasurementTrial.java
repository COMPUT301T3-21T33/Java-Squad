package com.example.java_squad;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Date;

public class RecordMeasurementTrial extends AppCompatActivity {

    ListView trailList; // Reference to listview inside activity_main.xml
    ArrayAdapter<Measurement> trialAdapter; // Bridge between dataList and cityList.
    ArrayList<Measurement> trialDataList; // Holds the data that will go into the listview

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        trailList = findViewById(R.id.trail_list);

        String[] experimenter = {};
        Date[] experiment_date = {};
        String[] unit = {};
        double[] amount = {};

        trialDataList = new ArrayList<>();
        for (int i = 0; i < experimenter.length; i++) {
            trialDataList.add((new Measurement(experimenter[i], experiment_date[i],unit[i],amount[i])));
        }
        trialAdapter = new MeasurementCustomList(this, trialDataList);

        trailList.setAdapter(trialAdapter);
        Button addTrialButton = findViewById(R.id.add_trial_button);
        addTrialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new AddCityFragment().show(getSupportFragmentManager(), "add trial");
                Log.d("record msg activity","add experiment trial button pressed");

            }
        });

        trailList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

//                Intent pass = new Intent(view.getContext(), AddCityFragment.class);
                Measurement trial = trialDataList.get(i);

//                AddMeasurementTrailFragment frag = new AddMeasurementTrailFragment().newInstance(city);
//                frag.show(getSupportFragmentManager(), "add trial");

            }
        });
    }


}
