package com.example.java_squad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class RecordMeasurementTrial extends AppCompatActivity implements AddMeasurementTrialFragment.OnFragmentInteractionListener {

    ListView trialList; // Reference to listview inside activity_main.xml
    ArrayAdapter<Measurement> trialAdapter; // Bridge between dataList and cityList.
    ArrayList<Measurement> trialDataList; // Holds the data that will go into the listview
    Experimental experiment;
    FirebaseDatabase db;
    DatabaseReference df;
    String userid;
    String expName;
    FirebaseFirestore fs;
    Button viewQuestion;
    Double longitude;
    Double latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.experiment_for_experimenter);
        Intent intent = getIntent();

        experiment = (Experimental) intent.getSerializableExtra("experiment");
        Places.initialize(getApplicationContext(),"@string/API_key");

        TextView experimentName = findViewById(R.id.experiment_name);
        TextView owner = findViewById(R.id.owner);
        TextView description = findViewById(R.id.experiment_description_content);
        TextView type = findViewById(R.id.type);
        TextView availability = findViewById(R.id.availability);
        TextView status = findViewById(R.id.status);
        TextView geo = findViewById(R.id.geo);

        experimentName.setText(experiment.getName());
        owner.setText(experiment.getOwnerName());
        description.setText(experiment.getDescription());
        expName = experiment.getName();
        if (experiment.getEnableGeo() == 1){
            geo.setText("Enabled");
        } else{
            geo.setText("Disabled");
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

        // Get a top level reference to the collection
        userid  = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        trialDataList = new ArrayList<>();
        trialAdapter = new MeasurementCustomList(this, trialDataList);
        //
        trialList.setAdapter(trialAdapter);
        trialList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView < ? > adapter, View view,int position, long arg){
                if (experiment.getEnableGeo() == 1){
                    Intent intent = new Intent(getBaseContext(),com.example.java_squad.Geo.SelectLocationActivity.class);
                    intent.putExtra("position", position);
                    startActivityForResult(intent,4);
                    startActivity(intent);
                }
            }
        });
        DateConverter dateConverter = new DateConverter();

        df =  FirebaseDatabase.getInstance().getReference("User").child(userid).child("FollowedExperiment").child(expName).child("trials");
        df.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                trialDataList.clear();
                for(DataSnapshot ss: snapshot.getChildren())
                {
                    String enableGeo = ss.child("enableGeo").getValue().toString();
                    String dateString = "2020-02-02";
                    String experimenter = ss.child("experimenter").getValue().toString();
                    String unit = ss.child("unit").getValue().toString();
                    String amount = ss.child("amount").getValue().toString();
                    Double a = Double.parseDouble(amount);
                    Integer geo = Integer.parseInt(enableGeo);
                    String lonS = ss.child("longitude").getValue().toString();
                    String latS = ss.child("latitude").getValue().toString();
                    Double lon = Double.parseDouble(lonS);
                    Double lat = Double.parseDouble(latS);

                    try {
                        Date dateDate = dateConverter.stringToDate(dateString);
                        trialDataList.add(new Measurement(experimenter, dateDate,geo,lon,lat,unit,a)); // Adding the cities and provinces from FireStore
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                trialAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Button addTrialButton = findViewById(R.id.add_trial_button);
        addTrialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("enable geo", String.valueOf(experiment.getEnableGeo()));
                // set Fragmentclass Arguments
                AddMeasurementTrialFragment fragobj = new AddMeasurementTrialFragment();
                fragobj.setArguments(bundle);
                fragobj.show(getSupportFragmentManager(), "add trial");

                //new AddMeasurementTrialFragment().show(getSupportFragmentManager(), "add trial");
                Log.d("record msg activity","add experiment trial button pressed");

            }
        });

    }
    public void MapsActivity(View view){
        Intent intent = new Intent(this, com.example.java_squad.Geo.MapsActivity.class);
//        intent.putExtra("user", user);
        startActivity(intent);
    }

    @Override
    public void onOkPressed(Measurement newTrail) {
        newTrail.setEnableGeo(experiment.getEnableGeo());
        trialAdapter.add(newTrail);
        df =  FirebaseDatabase.getInstance().getReference("User").child(userid).child("FollowedExperiment").child(expName).child("trials");

        viewQuestion = findViewById(R.id.view_question_button);
        viewQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ViewQuestionActivity.class);
                intent.putExtra("experimentName", experiment.getName());
                startActivity(intent);

            }
        });

        df.push().setValue(newTrail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    Log.d("add trial", "successful with id ");
                } else {
                    Log.d("add trial", "not successful");
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 4 && resultCode == RESULT_OK){
            longitude = data.getDoubleExtra("longitude",0);
            latitude = data.getDoubleExtra("latitude",0);
            int position = data.getIntExtra("position",0);

            Measurement trial = trialAdapter.getItem(position);
            trial.setLongitude(longitude);
            trial.setLatitude(latitude);
            trial.setEnableGeo(0);
            Log.d("get measurement",String.valueOf(trial.getLatitude()));
            Toast.makeText(RecordMeasurementTrial.this,"latitude = "+String.valueOf(latitude) + " longitude = "+String.valueOf(longitude), Toast.LENGTH_SHORT).show();

            replaceTrial(position,trial);

        } else {
            Log.d("record measurement","cannot receive coordinate");
        }
    }
    private void replaceTrial(int index, Measurement updatedTrial){
//        int currentExperimentIndex = trialDataList.indexOf(trial);
        trialDataList.set(index,updatedTrial);
        trialAdapter = new MeasurementCustomList(this, trialDataList);
        trialList.setAdapter(trialAdapter);
        trialAdapter.notifyDataSetChanged();
    }
}