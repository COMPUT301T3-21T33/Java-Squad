package com.example.java_squad;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
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
import com.example.java_squad.user.User;
import com.google.android.libraries.places.api.Places;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class ExperimentViewIntCount extends AppCompatActivity implements AddIntCountTrialFragment.OnFragmentInteractionListener {

    ListView trialList; // Reference to listview inside activity_main.xml
    ArrayAdapter<IntCount> trialAdapter; // Bridge between dataList and cityList.
    ArrayList<IntCount> trialDataList; // Holds the data that will go into the listview
    Experimental experiment;

    DatabaseReference df;
    String userid;
    Double longitude;
    Double latitude;
    Boolean isfollow = false;
    Button viewQuestion,back_btn,viewMap,addTrialButton,stat_btn, EndExperiment,
            qrButton, barcodeButton,expPublish;
    ImageButton follow;
    Intent intent;
    String ExperimentName;
    User owner1;
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
        setContentView(R.layout.activity_experiment_view);
        intent = getIntent();
        experiment = (Experimental) intent.getSerializableExtra("experiment");
        ExperimentName = experiment.getName();
        Places.initialize(getApplicationContext(),"@string/API_key");

        TextView experimentName = findViewById(R.id.exp_name);
        TextView owner = findViewById(R.id.exp_owner);
        TextView description = findViewById(R.id.exp_description);
        TextView type = findViewById(R.id.exp_type);
        TextView availability = findViewById(R.id.available_text);
        TextView status = findViewById(R.id.exp_active);
        viewMap = findViewById(R.id.view_map_owner);
        qrButton = findViewById(R.id.button_qrcode);
        barcodeButton = findViewById(R.id.button_barcode);

        if (experiment.getEnableGeo() == 1){
            viewMap.setEnabled(true);
        }
        experimentName.setText(experiment.getName());

        userid  = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        //owner.setText(experiment.getOwnerName());
        DatabaseReference df1 = FirebaseDatabase.getInstance().getReference("User").child(userid);
        df1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                owner1 = snapshot.getValue(User.class);
                owner.setText(owner1.getUsername());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        description.setText(experiment.getDescription());
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
        trialList = findViewById(R.id.owner_trial_list);

        // Get a top level reference to the collection
        //userid  = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        trialDataList = new ArrayList<>();
        trialAdapter = new IntCountCustomList(this, trialDataList);
        trialList.setAdapter(trialAdapter);

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Trail");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            int counter = 0;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(ExperimentName)){
                    for (DataSnapshot datasnapshot: snapshot.child(ExperimentName).getChildren()){
                        IntCount intcount = datasnapshot.getValue(IntCount.class);
                        trialDataList.add(intcount);
                        counter ++;
                    }
                    trialAdapter.notifyDataSetChanged();
                    if (counter < experiment.getMinTrials()){
                        String min = String.valueOf(experiment.getMinTrials());
                        Toast.makeText(ExperimentViewIntCount.this,"This experiment needs at least "+min+" trials", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //trialAdapter = new BinomialCustomList(this, trialDataList);
        //trialList.setAdapter(trialAdapter);
        Button enableGEO = findViewById(R.id.geolocation_enable);
        enableGEO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                experiment.setEnableGeo(1);
                HashMap geo = new HashMap();
                geo.put("enableGeo", 1);
                viewMap.setEnabled(true);
                DatabaseReference updateGeo = FirebaseDatabase.getInstance().getReference("Experiment");
                updateGeo.child(ExperimentName).updateChildren(geo);
                DatabaseReference updateGeoTOuser = FirebaseDatabase.getInstance().getReference("User").child(userid);
                updateGeoTOuser.child("Experiment").child(ExperimentName).updateChildren(geo);

            }
        });
        //Add Statistic view button for binomial trials here
        stat_btn =findViewById(R.id.statistic_owner);
        //Add Statistic view button for integer count trials here
        stat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pass this datalist to statistic_RecordIntCountTrial
                Intent intent_s_IntC = new Intent(ExperimentViewIntCount.this, Statistic_RecordIntCountTrial.class);
                intent_s_IntC.putExtra("DataList_of_IntC_trials", trialDataList);
                startActivity(intent_s_IntC);
                //startActivity(new Intent(getApplicationContext(), Statistic_RecordIntCountTrial.class));
            }
        });



        trialList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Log.d("main activity","On item click to record trials");
                IntCount trial_intcount = (IntCount) adapterView.getAdapter().getItem(i);//experiments[i];
                new AlertDialog.Builder(ExperimentViewIntCount.this)
                        .setTitle("Ignore Trial")
                        .setMessage("Would you like to ignore this trial or add a location?")
                        .setNeutralButton("Add Location", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (experiment.getEnableGeo() == 1){
                                    Intent intent = new Intent(getBaseContext(),com.example.java_squad.Geo.SelectLocationActivity.class);
                                    intent.putExtra("position", i);
                                    startActivityForResult(intent,1);
                                }

                            }
                        })
                        .setPositiveButton("Ignore", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                trialDataList.remove(trial_intcount);
                                trialAdapter.notifyDataSetChanged();
                                String id = trial_intcount.getTrialID();
                                DatabaseReference myref = FirebaseDatabase.getInstance().getReference("Trail")
                                        .child(experiment.getName())
                                        .child(id);
                                myref.removeValue();

                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();


            }
        });

        addTrialButton = findViewById(R.id.add_trial);
        addTrialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("enable geo", String.valueOf(experiment.getEnableGeo()));
                // set Fragmentclass Arguments
                AddIntCountTrialFragment fragobj = new AddIntCountTrialFragment();
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

        EndExperiment = findViewById(R.id.button_end_experiment);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User").child(userid);

        EndExperiment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                experiment.setActive(false);
                status.setText("Status: Finished");
                EndExperiment.setClickable(false);
                addTrialButton.setClickable(false);
                HashMap data = new HashMap();
                boolean isfalse = false;
                data.put("active", isfalse);
                databaseReference.child("Experiment").child(experiment.getName()).updateChildren(data);
                DatabaseReference df = FirebaseDatabase.getInstance().getReference("Experiment").child(experiment.getName());
                df.updateChildren(data);
            }

        });

        expPublish = findViewById(R.id.unpublish_button);

        expPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String text = (String) expPublish.getText();
                availability.setText("PRIVATE");
                experiment.setPublished(false);
                HashMap data = new HashMap();
                
                boolean isf = false;
                data.put("published", isf);
                databaseReference.child("Experiment").child(experiment.getName()).updateChildren(data);
                DatabaseReference df1 = FirebaseDatabase.getInstance().getReference("Experiment").child(experiment.getName());
                df1.removeValue();
            }
        });

//view question
        viewQuestion = findViewById(R.id.view_question);
        viewQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ViewQuestionActivity.class);
                intent.putExtra("experimentName", experiment.getName());
                startActivity(intent);

            }
        });

        barcodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), BarcodeActivity.class);
                intent.putExtra("Experiment",experiment);
                intent.putExtra("scanning", true);
                startActivity(intent);

            }
        });

        qrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), QrSetupActivity.class);
                intent.putExtra("experiment",experiment);
                startActivity(intent);
            }
        });

        //check in database if the user follow this experiment or not
        follow = findViewById(R.id.follow);
        DatabaseReference df123  = FirebaseDatabase.getInstance().getReference("User").child(userid);
        df123.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild("follow")){
                    for(DataSnapshot datasnapshot: snapshot.child("follow").getChildren()){
                        if (datasnapshot.child("name").getValue().toString().equals(ExperimentName)){
                            //if user followed this experiment, set the hearbutton to be full
                            follow.setImageResource(R.drawable.ic_action_liking);
                            follow.setTag(R.drawable.ic_action_liking);//set tag
                            isfollow = true; //set is follow to true

                        }
                        Log.d("if is followed exp name",datasnapshot.child("name").getValue().toString());

                    }
                    Log.d("if is followed","has child follow");

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //when user click on follow
        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if user did not follow this experiment
                if(follow.getTag()==null) {
                    //set the viewquestion, addtril, viewmap, viewstatistic button to be clicked

                    //set image button to be full heart
                    follow.setImageResource(R.drawable.ic_action_liking);
                    //update database
                    experiment.setOwner(owner1);
                    df123.child("follow").child(ExperimentName).setValue(experiment);
                }
                //if user follow this experiment
                else{
                    //set image button to be empty heart
                    follow.setImageResource(R.drawable.ic_action_like);
                    //update database
                    df123.child("follow").child(ExperimentName).removeValue();
                    //set the viewquestion, addtril, viewmap, viewstatistic button to be unclicked

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
    public void onOkPressed(IntCount newTrail) {
        newTrail.setEnableGeo(experiment.getEnableGeo());
        trialAdapter.add(newTrail);
        DatabaseReference dataref = FirebaseDatabase.getInstance().getReference("Trail");
        String key = dataref.push().getKey();
        newTrail.setTrialID(key);
        dataref.child(ExperimentName).child(key).setValue(newTrail);
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK){
            longitude = data.getDoubleExtra("longitude",0);
            latitude = data.getDoubleExtra("latitude",0);
            int position = data.getIntExtra("position",0);

            IntCount trial = trialAdapter.getItem(position);
            trial.setLongitude(longitude);
            trial.setLatitude(latitude);
            trial.setEnableGeo(0);
            Log.d("get latitude",String.valueOf(trial.getLatitude()));

            replaceTrial(position,trial);

            DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference("Trail").child(ExperimentName);

            HashMap updateData = new HashMap();
            updateData.put("longitude", longitude);
            updateData.put("latitude", latitude);
            dataRef.child(trial.getTrialID()).updateChildren(updateData);

        } else {
            Log.d("record binomial","cannot receive coordinate");
        }
    }
    /**
     * replace the old trial with the updated trial
     * @param index
     * the index of the trial that needs to be update
     * @param updatedTrial
     * the new trial to update
     */
    private void replaceTrial(int index, IntCount updatedTrial){
//        int currentExperimentIndex = trialDataList.indexOf(trial);
        trialDataList.set(index,updatedTrial);
        trialAdapter = new IntCountCustomList(this, trialDataList);
        trialList.setAdapter(trialAdapter);
        trialAdapter.notifyDataSetChanged();
    }

    public void trialDataList(DocumentReference document, Trial trial) {
    }
}
