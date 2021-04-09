package com.example.java_squad;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.java_squad.Geo.MapsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
/**
 * RecordCountTrial class showing all the information of a Count type experiment for experimenters
 */
public class RecordCountTrial extends AppCompatActivity implements AddCountTrialFragment.OnFragmentInteractionListener{
    ListView trialList; // Reference to listview inside activity_main.xml
    ArrayAdapter<Count> trialAdapter; // Bridge between dataList and cityList.
    ArrayList<Count> trialDataList; // Holds the data that will go into the listview
    Experimental experiment;
    FirebaseDatabase db;
    FirebaseFirestore fs;
    Double longitude;
    Double latitude;
    String userid;
    Boolean isfollow = false;

    Button viewQuestion,back_btn,viewMap,addTrialButton,stat_btn;
    ImageButton follow;

    String ExperimentName;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.experiment_for_experimenter);
        intent = getIntent();
        experiment = (Experimental) intent.getSerializableExtra("experiment");
        Places.initialize(getApplicationContext(),"@string/API_key");

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
        viewMap = findViewById(R.id.view_map);

        if (experiment.getEnableGeo() == 1){
            viewMap.setEnabled(true);
        }
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

        trialAdapter = new CountCustomList(this, trialDataList);
        //
        trialList.setAdapter(trialAdapter);

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Trail");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            int counter = 0;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(ExperimentName)){
                    for (DataSnapshot datasnapshot: snapshot.child(ExperimentName).getChildren()){
                        Count count = datasnapshot.getValue(Count.class);
                        trialDataList.add(count);
                        counter++;
                    }
                    trialAdapter.notifyDataSetChanged();
                    if (counter < experiment.getMinTrials()){
                        String min = String.valueOf(experiment.getMinTrials());
                        Toast.makeText(RecordCountTrial.this,"This experiment needs at least "+min+" trials", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Add Statistic view button for count trials here
        stat_btn =findViewById(R.id.view_stat_button);
        stat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pass this datalist to statistic_RecordCountTrial
                Intent intent_s_C = new Intent(RecordCountTrial.this, Statistic_RecordCountTrial.class);
                intent_s_C.putExtra("DataList_of_C_trials", trialDataList);
                startActivity(intent_s_C);
                //startActivity(new Intent(getApplicationContext(), Statistic_RecordIntCountTrial.class));
            }
        });


        trialList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView < ? > adapter, View view,int position, long arg){
                if (experiment.getEnableGeo() == 1){
                    Intent intent = new Intent(getBaseContext(),com.example.java_squad.Geo.SelectLocationActivity.class);
                    intent.putExtra("position", position);
                    startActivityForResult(intent,2);
                }
            }
        });

        addTrialButton = findViewById(R.id.add_trial_button);
        addTrialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("enable geo", String.valueOf(experiment.getEnableGeo()));
                // set Fragmentclass Arguments
                AddCountTrialFragment fragobj = new AddCountTrialFragment();
                fragobj.setArguments(bundle);
                fragobj.show(getSupportFragmentManager(), "add trial");
                Log.d("record msg activity","add experiment trial button pressed");

            }
        });
        viewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MapsActivity.class);
                intent.putExtra("experimentName", experiment.getName());
                Log.d("view map button clicked",experiment.getName());
                startActivity(intent);
            }
        });
        
        //view question
        viewQuestion = findViewById(R.id.view_question_button);
        viewQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ViewQuestionActivity.class);
                intent.putExtra("experimentName", experiment.getName());
                startActivity(intent);

            }
        });

        userid =intent.getStringExtra("id");
        //check in database if the user follow this experiment or not
        follow = findViewById(R.id.follow_button);
        DatabaseReference df = FirebaseDatabase.getInstance().getReference("User").child(userid);
        df.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild("follow")){
                    for(DataSnapshot datasnapshot: snapshot.child("follow").getChildren()){
                        if (datasnapshot.child("name").getValue().toString().equals(ExperimentName)){
                            //if user followed this experiment, set the hearbutton to be full
                            follow.setImageResource(R.drawable.ic_action_liking);
                            follow.setTag(R.drawable.ic_action_liking);//set tag
                            isfollow = true; //set is follow to true
                            viewQuestion.setClickable(true);
                            addTrialButton.setClickable(true);
                            viewMap.setClickable(true);
                            stat_btn.setClickable(true);
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //if isfollow is true set the viewquestion, addtril, viewmap, viewstatistic button to be clicked
        //else non clicked
        if (!isfollow){
            viewQuestion.setClickable(false);
            addTrialButton.setClickable(false);
            viewMap.setClickable(false);
        }
        //when user click on follow
        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if user did not follow this experiment
                if(follow.getTag()==null) {
                    //set the viewquestion, addtril, viewmap, viewstatistic button to be clicked
                    viewQuestion.setClickable(true);
                    addTrialButton.setClickable(true);
                    viewMap.setClickable(true);
                    stat_btn.setClickable(true);
                    //set image button to be full heart
                    follow.setImageResource(R.drawable.ic_action_liking);
                    //update database
                    df.child("follow").child(ExperimentName).setValue(experiment);
                }
                //if user follow this experiment
                else{
                    //set image button to be empty heart
                    follow.setImageResource(R.drawable.ic_action_like);
                    //update database
                    df.child("follow").child(ExperimentName).removeValue();
                    //set the viewquestion, addtril, viewmap, viewstatistic button to be unclicked
                    viewQuestion.setClickable(false);
                    viewMap.setClickable(false);
                    addTrialButton.setClickable(false);
                    stat_btn.setClickable(false);
                }

            }
        });
        //back button
    
        back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void MapsActivity(View view){
        Intent intent = new Intent(this, com.example.java_squad.Geo.MapsActivity.class);
//        intent.putExtra("user", user);
        startActivity(intent);
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK){
            longitude = data.getDoubleExtra("longitude",0);
            latitude = data.getDoubleExtra("latitude",0);
            int position = data.getIntExtra("position",0);

            Count trial = trialAdapter.getItem(position);
            trial.setLongitude(longitude);
            trial.setLatitude(latitude);
            trial.setEnableGeo(0);
            Log.d("get count",String.valueOf(trial.getLatitude()));
            Toast.makeText(RecordCountTrial.this,"latitude = "+String.valueOf(latitude) + " longitude = "+String.valueOf(longitude), Toast.LENGTH_SHORT).show();

            replaceTrial(position,trial);
            DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference("Trail").child(ExperimentName);

            HashMap updateData = new HashMap();
            updateData.put("longitude", longitude);
            updateData.put("latitude", latitude);
            dataRef.child(trial.getTrialID()).updateChildren(updateData);

        } else {
            Log.d("record count","cannot receive coordinate");
        }
    }
    /**
     * replace the old trial with the updated trial
     * @param index
     * the index of the trial that needs to be update
     * @param updatedTrial
     * the new trial to update
     */
    private void replaceTrial(int index, Count updatedTrial){
//        int currentExperimentIndex = trialDataList.indexOf(trial);
        trialDataList.set(index,updatedTrial);
        trialAdapter = new CountCustomList(this, trialDataList);
        trialList.setAdapter(trialAdapter);
        trialAdapter.notifyDataSetChanged();
    }


    @Override
    public void onOkPressed(Count newTrail) {
        newTrail.setEnableGeo(experiment.getEnableGeo());
        trialAdapter.add(newTrail);
        DatabaseReference dataref = FirebaseDatabase.getInstance().getReference("Trail");
        String key = dataref.push().getKey();
        newTrail.setTrialID(key);
        dataref.child(ExperimentName).child(key).setValue(newTrail);
    }
}
