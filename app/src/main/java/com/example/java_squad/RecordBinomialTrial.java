package com.example.java_squad;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
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

import com.example.java_squad.Geo.MapsActivity;
import com.example.java_squad.Geo.SelectLocationActivity;
import com.example.java_squad.Geo.SelectLocationFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.android.libraries.places.api.Places;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class RecordBinomialTrial extends AppCompatActivity implements AddBinomialTrialFragment.OnFragmentInteractionListener {

    ListView trialList; // Reference to listview inside activity_main.xml
    ArrayAdapter<Binomial> trialAdapter; // Bridge between dataList and cityList.
    ArrayList<Binomial> trialDataList; // Holds the data that will go into the listview
    Experimental experiment;

    DatabaseReference df;
    String userid;
    String expName;
    Double longitude;
    Double latitude;
    Button viewQuestion,back_btn;
    ImageButton follow;
    Intent intent;
    String ExperimentName;
    private FirebaseFirestore db;
    private static RecordBinomialTrial data;
    FirebaseFirestore fs;
    public static RecordBinomialTrial getDataBase() {
        if (data == null) {
            data = new RecordBinomialTrial();
        }
        return data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.experiment_for_experimenter);
        intent = getIntent();
        experiment = (Experimental) intent.getSerializableExtra("experiment");
        ExperimentName = experiment.getName();
        Places.initialize(getApplicationContext(),"@string/API_key");

        TextView experimentName = findViewById(R.id.experiment_name);
        TextView owner = findViewById(R.id.owner);
        TextView description = findViewById(R.id.experiment_description_content);
        TextView type = findViewById(R.id.type);
        TextView availability = findViewById(R.id.availability);
        TextView status = findViewById(R.id.status);

        experimentName.setText(experiment.getName());
        owner.setText(experiment.getOwnerName());
        description.setText(experiment.getDescription());
        expName = experiment.getName();

//        if (experiment.getEnableGeo() == 1){
//            geo.setText("Enabled");
//        } else{
//            geo.setText("Disabled");
//        }

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

        trialAdapter = new BinomialCustomList(this, trialDataList);
        //
        trialList.setAdapter(trialAdapter);
        trialList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView < ? > adapter, View view,int position, long arg){
                if (experiment.getEnableGeo() == 1){
                    Intent intent = new Intent(getBaseContext(),com.example.java_squad.Geo.SelectLocationActivity.class);
                    intent.putExtra("position", position);
                    startActivityForResult(intent,1);
                }
            }
        });
//        trialList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Binomial trial = trialDataList.get(i);
//                Toast.makeText(RecordBinomialTrial.this,"latitude = "+String.valueOf(trial.getLongitude()) + " longitude = "+String.valueOf(trial.getLatitude()), Toast.LENGTH_SHORT).show();
//                return true;
//            }
//        });


        trialAdapter = new BinomialCustomList(this, trialDataList);
        //
        trialList.setAdapter(trialAdapter);

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
                    String result = ss.child("result").getValue().toString();
                    String lonS = ss.child("longitude").getValue().toString();
                    String latS = ss.child("latitude").getValue().toString();
                    Double lon = Double.parseDouble(lonS);
                    Double lat = Double.parseDouble(latS);
                    Integer geo = Integer.parseInt(enableGeo);
                    trialDataList.add(new Binomial(experimenter,"",geo,lon,lat,result)); // Adding the cities and provinces from FireStore


//        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Trail");
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.hasChild(ExperimentName)){
//                    for (DataSnapshot datasnapshot: snapshot.child(ExperimentName).getChildren()){
//                        Binomial binomial = datasnapshot.getValue(Binomial.class);
//                        trialDataList.add(binomial);


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
                AddBinomialTrialFragment fragobj = new AddBinomialTrialFragment();
                fragobj.setArguments(bundle);
                fragobj.show(getSupportFragmentManager(), "add trial");

//                AddBinomialTrialFragment fragobj = new AddBinomialTrialFragment();
//                fragobj.setArguments(bundle);
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.replace(R.id.mainMap, fragobj ); // give your fragment container id in first parameter
//                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
//                transaction.commit();
//                new AddBinomialTrialFragment().show(getSupportFragmentManager(), "add trial");
                Log.d("record msg activity","add experiment trial button pressed");

            }
        });

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
    public void MapsActivity(View view){
        Intent intent = new Intent(this, com.example.java_squad.Geo.MapsActivity.class);
//        intent.putExtra("user", user);
        startActivity(intent);
    }

    @Override
    public void onOkPressed(Binomial newTrail) {
        newTrail.setEnableGeo(experiment.getEnableGeo());
        trialAdapter.add(newTrail);
        df = FirebaseDatabase.getInstance().getReference("User").child(userid).child("FollowedExperiment").child(expName).child("trials");

        String key = df.push().getKey();
        newTrail.setTrialID(key);
        df.child(key).setValue(newTrail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    Log.d("add trial", "successful with id ");
                } else {
                    Log.d("add trial", "not successful");
                }
            }
        });

        DatabaseReference dataref = FirebaseDatabase.getInstance().getReference("Trail");
        dataref.child(ExperimentName).child(key).setValue(newTrail);

//    public void trialDataList(DocumentReference document, Trial trial) {
//
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK){
            longitude = data.getDoubleExtra("longitude",0);
            latitude = data.getDoubleExtra("latitude",0);
            int position = data.getIntExtra("position",0);

            Binomial trial = trialAdapter.getItem(position);
            trial.setLongitude(longitude);
            trial.setLatitude(latitude);
            trial.setEnableGeo(0);
            Log.d("get latitude",String.valueOf(trial.getLatitude()));

            replaceTrial(position,trial);

            Toast.makeText(RecordBinomialTrial.this,"latitude = "+String.valueOf(latitude) + " longitude = "+String.valueOf(longitude), Toast.LENGTH_SHORT).show();
            Log.d("record binomial","receives coordinate");

        } else {
            Log.d("record binomial","cannot receive coordinate");
        }
    }
    private void replaceTrial(int index, Binomial updatedTrial){
//        int currentExperimentIndex = trialDataList.indexOf(trial);
        trialDataList.set(index,updatedTrial);
        trialAdapter = new BinomialCustomList(this, trialDataList);
        trialList.setAdapter(trialAdapter);
        trialAdapter.notifyDataSetChanged();
    }

    public void trialDataList(DocumentReference document, Trial trial) {
    }
}