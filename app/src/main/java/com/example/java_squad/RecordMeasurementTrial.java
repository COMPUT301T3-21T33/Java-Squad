package com.example.java_squad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class RecordMeasurementTrial extends AppCompatActivity implements AddMeasurementTrialFragment.OnFragmentInteractionListener {

    ListView trialList; // Reference to listview inside activity_main.xml
    ArrayAdapter<Measurement> trialAdapter; // Bridge between dataList and cityList.
    ArrayList<Measurement> trialDataList; // Holds the data that will go into the listview
    Experimental experiment;
    Button viewQuestion,back_btn,addTrialButton;
    ImageButton follow;
    String ExperimentName;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.experiment_for_experimenter);
        intent = getIntent();

        experiment = (Experimental) intent.getSerializableExtra("experiment");
        ExperimentName = experiment.getName();

        TextView experimentName = findViewById(R.id.experiment_name);
        TextView owner = findViewById(R.id.owner);
        TextView description = findViewById(R.id.experiment_description_content);
        TextView type = findViewById(R.id.type);
        TextView availability = findViewById(R.id.availability);
        TextView status = findViewById(R.id.status);

        experimentName.setText(experiment.getName());
        owner.setText(experiment.getOwnerName());
        description.setText(experiment.getDescription());


        if (experiment.getPublished() == true){
            availability.setText("Public");
        }
        else{
            availability.setText("Private");
        }

        if (experiment.getActive() == true){
            status.setText("In progress");
        }
        else{
            status.setText("End");
        }

        int exp_type = experiment.getType();
        String typeInStr = "";
        if (exp_type == 0){
            typeInStr = "Count";
        }
        else if (exp_type == 1) {
            typeInStr = "Binomial";

        }
        else if (exp_type == 2) {
            typeInStr = "Non-neg Count";

        }
        else if (exp_type == 3) {
            typeInStr = "Measurement";

        }
        type.setText(typeInStr);

        trialList = findViewById(R.id.trail_list);
        trialDataList = new ArrayList<>();

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Trail");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(ExperimentName)){
                    for (DataSnapshot datasnapshot: snapshot.child(ExperimentName).getChildren()){
                        Measurement measurement = datasnapshot.getValue(Measurement.class);
                        trialDataList.add(measurement);
                    }
                    trialAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        trialAdapter = new MeasurementCustomList(this, trialDataList);
        trialList.setAdapter(trialAdapter);

        addTrialButton = findViewById(R.id.add_trial_button);
        addTrialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddMeasurementTrialFragment().show(getSupportFragmentManager(), "add trial");
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

        viewQuestion = findViewById(R.id.view_question_button);
        viewQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ViewQuestionActivity.class);
                intent.putExtra("experimentName", experiment.getName());
                startActivity(intent);

            }
        });

        String userid = intent.getStringExtra("id");
        viewQuestion.setClickable(false);
        addTrialButton.setClickable(false);
        follow = findViewById(R.id.follow_button);
        DatabaseReference df = FirebaseDatabase.getInstance().getReference("User").child(userid);
        df.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild("follow")){
                    for(DataSnapshot datasnapshot: snapshot.child("follow").getChildren()){
                        if (datasnapshot.child("name").getValue().toString().equals(ExperimentName)){
                            follow.setImageResource(R.drawable.ic_action_liking);
                            follow.setTag(R.drawable.ic_action_liking);
                            viewQuestion.setClickable(true);
                            addTrialButton.setClickable(true);
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(follow.getTag()==null) {
                    viewQuestion.setClickable(true);
                    df.child("follow").child(ExperimentName).setValue(experiment);
                }
            }
        });
        back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public void onOkPressed(Measurement newTrail) {
        trialAdapter.add(newTrail);
        DatabaseReference dataref = FirebaseDatabase.getInstance().getReference("Trail");
        dataref.child(ExperimentName).push().setValue(newTrail);
    }
}