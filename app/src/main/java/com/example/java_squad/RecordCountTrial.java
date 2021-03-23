package com.example.java_squad;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Date;

public class RecordCountTrial extends AppCompatActivity implements AddCountTrialFragment.OnFragmentInteractionListener{
    ListView trialList; // Reference to listview inside activity_main.xml
    ArrayAdapter<Count> trialAdapter; // Bridge between dataList and cityList.
    ArrayList<Count> trialDataList; // Holds the data that will go into the listview

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.experiment_for_experimenter);

        trialList = findViewById(R.id.trail_list);

        String[] experimenter = {};
        Date[] experiment_date = {};
        String[] object = {};
        Integer[] amount = {};

        trialDataList = new ArrayList<>();
        for (int i = 0; i < experimenter.length; i++) {
            trialDataList.add((new Count(experimenter[i], experiment_date[i],object[i],amount[i])));
        }
        trialAdapter = new CountCustomList(this, trialDataList);

        trialList.setAdapter(trialAdapter);
        Button addTrialButton = findViewById(R.id.add_trial_button);
        addTrialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddCountTrialFragment().show(getSupportFragmentManager(), "add trial");
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
    }


    @Override
    public void onOkPressed(Count newTrail) {
        trialAdapter.add(newTrail);
    }


}
