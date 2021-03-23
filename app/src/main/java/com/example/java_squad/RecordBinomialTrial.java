package com.example.java_squad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

public class RecordBinomialTrial extends AppCompatActivity implements AddBinomialTrialFragment.OnFragmentInteractionListener {

    ListView trialList; // Reference to listview inside activity_main.xml
    ArrayAdapter<Binomial> trialAdapter; // Bridge between dataList and cityList.
    ArrayList<Binomial> trialDataList; // Holds the data that will go into the listview

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.experiment_for_experimenter);

        trialList = findViewById(R.id.trail_list);

        String[] experimenter = {};
        Date[] experiment_date = {};
        String[] binomial = {};

        trialDataList = new ArrayList<>();
        for (int i = 0; i < experimenter.length; i++) {
            trialDataList.add((new Binomial(experimenter[i], experiment_date[i],binomial[i])));
        }
        trialAdapter = new BinomialCustomList(this, trialDataList);

        trialList.setAdapter(trialAdapter);
        Button addTrialButton = findViewById(R.id.add_trial_button);
        addTrialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddBinomialTrialFragment().show(getSupportFragmentManager(), "add trial");
                Log.d("record msg activity","add experiment trial button pressed");

            }
        });
        //https://stackoverflow.com/questions/6210895/listview-inside-scrollview-is-not-scrolling-on-android#:~:text=You%20shouldn't%20put%20a,handled%20by%20the%20parent%20ScrollView%20.&text=For%20example%20you%20can%20add,ListView%20as%20headers%20or%20footers.
        trialList.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });

//        trialList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
////                Intent pass = new Intent(view.getContext(), AddCityFragment.class);
//                Measurement trial = trialDataList.get(i);
//
////                AddMeasurementTrailFragment frag = new AddMeasurementTrailFragment().newInstance(city);
////                frag.show(getSupportFragmentManager(), "add trial");
//
//            }
//        });
    }


    @Override
    public void onOkPressed(Binomial newTrail) {
        trialAdapter.add(newTrail);
    }
}