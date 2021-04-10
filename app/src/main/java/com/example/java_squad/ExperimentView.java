package com.example.java_squad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.java_squad.user.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ExperimentView extends AppCompatActivity implements AddCountTrialFragment.OnFragmentInteractionListener, AddBinomialTrialFragment.OnFragmentInteractionListener, AddIntCountTrialFragment.OnFragmentInteractionListener,  AddMeasurementTrialFragment.OnFragmentInteractionListener{
    public Experimental currentExperiment;

    TextView expName;
    TextView expAvail;
    //needs expLocation once implemented
    //needs date
    TextView expOwner;
    TextView expType;
    Button expPublish;
    TextView expActive;
    TextView expDescription;
    TextView expRules;
    Button EndExperiment,viewStatistic, viewQuestion, enableGEO,addTrial,barcodeButton;
    ImageButton follow;

    ListView trialList;
    ArrayAdapter<Trial> trialAdapter;
    User owner;
    //Experimental experiment;
    ArrayAdapter<Count> trialAdapterCount; // Bridge between dataList and cityList.
    ArrayList<Count> trialDataListCount; // Holds the data that will go into the listview
    ArrayAdapter<Binomial> trialAdapterBinomial; // Bridge between dataList and cityList.
    ArrayList<Binomial> trialDataListBinomial; // Holds the data that will go into the listview
    ArrayAdapter<IntCount> trialAdapterIntCount; // Bridge between dataList and cityList.
    ArrayList<IntCount> trialDataListIntCount; // Holds the data that will go into the listview
    ArrayAdapter<Measurement> trialAdapterMeasurement; // Bridge between dataList and cityList.
    ArrayList<Measurement> trialDataListMeasurement; // Holds the data that will go into the listview
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiment_view);
        Intent intent = getIntent();

        currentExperiment = (Experimental) intent.getSerializableExtra("experiment");


        expName = findViewById(R.id.exp_name);
        expOwner = findViewById(R.id.exp_owner);
        expType = findViewById(R.id.exp_type);
        expPublish = findViewById(R.id.unpublish_button);
        expActive = findViewById(R.id.exp_active);
        expDescription = findViewById(R.id.exp_description);
        expRules = findViewById(R.id.exp_rules);
        expAvail = findViewById(R.id.available_text);
        barcodeButton = findViewById(R.id.button_barcode);

        expName.setText(currentExperiment.getName());
        //Whatever the func is to get owners name
        String userid = intent.getStringExtra("id");
        DatabaseReference df = FirebaseDatabase.getInstance().getReference("User").child(userid);
        df.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                owner = snapshot.getValue(User.class);
                expOwner.setText(owner.getUsername());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        barcodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), BarcodeSetupActivity.class);
                intent.putExtra("experiment",currentExperiment);
                startActivity(intent);

            }
        });
        //Log.d("xibing", owner.getUsername());

        int exp_type = currentExperiment.getType();
        String typeInStr = "";
        if (exp_type == 0) {
            typeInStr = "Count";
        } else if (exp_type == 1) {
            typeInStr = "Binomial";

        } else if (exp_type == 2) {
            typeInStr = "Non-neg Count";

        } else if (exp_type == 3) {
            typeInStr = "Measurement";

        }
        expType.setText(typeInStr);

        if (currentExperiment.getActive())
            expActive.setText("Status: In progress");
        else
            expActive.setText("Status: Finished");

        if (currentExperiment.getPublished() == true) {
            expAvail.setText("Public");
        } else {
            expAvail.setText("Private");
        }


        expDescription.setText(currentExperiment.getDescription());
        expRules.setText(currentExperiment.getRules());

        trialList = findViewById(R.id.owner_trial_list);

        //trialAdapter = new TrialCustomList(this, currentExperiment.trials);
        addTrial = findViewById(R.id.add_trial);

        if (exp_type == 0) {

            trialDataListCount = new ArrayList<>();
            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Trial");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.hasChild(currentExperiment.getName())){
                        for(DataSnapshot datasnapshot: snapshot.child(currentExperiment.getName()).getChildren()){
                            Count count = datasnapshot.getValue(Count.class);
                            trialDataListCount.add(count);
                        }
                        trialAdapterCount.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            trialAdapterCount = new CountCustomList(this, trialDataListCount);

            trialList.setAdapter(trialAdapterCount);
            addTrial.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AddCountTrialFragment().show(getSupportFragmentManager(), "add trial");
                    Log.d("record msg activity","add experiment trial button pressed");

                }
            });
            trialList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Log.d("main activity","On item click to record trials");
                    Count trial_count = (Count) adapterView.getAdapter().getItem(i);//experiments[i];
                    new AlertDialog.Builder(ExperimentView.this)
                            .setTitle("Ignore Trial")
                            .setMessage("Would you like to ignore this trial?")
                            .setPositiveButton("Ignore", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    trialDataListCount.remove(trial_count);
                                    trialAdapterCount.notifyDataSetChanged();
                                    String id = trial_count.getTrialID();
                                    DatabaseReference myref = FirebaseDatabase.getInstance().getReference("Trail")
                                            .child(currentExperiment.getName())
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
            //typeInStr = "Count";
        } else if (exp_type == 1) {
            //typeInStr = "Binomial";

            trialDataListBinomial = new ArrayList<>();
            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Trial");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.hasChild(currentExperiment.getName())){
                        for(DataSnapshot datasnapshot: snapshot.child(currentExperiment.getName()).getChildren()){
                            Binomial binomial = datasnapshot.getValue(Binomial.class);
                            trialDataListBinomial.add(binomial);
                        }
                        trialAdapterBinomial.notifyDataSetChanged();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            trialAdapterBinomial = new BinomialCustomList(this, trialDataListBinomial);
            trialList.setAdapter(trialAdapterBinomial);
            addTrial.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AddBinomialTrialFragment().show(getSupportFragmentManager(), "add trial");
                    Log.d("record msg activity","add experiment trial button pressed");

                }
            });
            trialList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Log.d("main activity","On item click to record trials");
                    Binomial trial_binomial = (Binomial) adapterView.getAdapter().getItem(i);//trialList[i];
                    new AlertDialog.Builder(ExperimentView.this)
                            .setTitle("Ignore Trial")
                            .setMessage("Would you like to ignore this trial?")
                            .setPositiveButton("Ignore", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    trialDataListBinomial.remove(trial_binomial);
                                    trialAdapterBinomial.notifyDataSetChanged();
                                    String id = trial_binomial.getTrialID();
                                    DatabaseReference myref = FirebaseDatabase.getInstance().getReference("Trail")
                                            .child(currentExperiment.getName())
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

        } else if (exp_type == 2) {
            //typeInStr = "Non-neg Count";
            trialDataListIntCount = new ArrayList<>();
            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Trial");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.hasChild(currentExperiment.getName())){
                        for(DataSnapshot datasnapshot: snapshot.child(currentExperiment.getName()).getChildren()){
                            IntCount intcount = datasnapshot.getValue(IntCount.class);
                            trialDataListIntCount.add(intcount);
                        }
                        trialAdapterIntCount.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            trialAdapterIntCount = new IntCountCustomList(this, trialDataListIntCount);
            trialList.setAdapter(trialAdapterIntCount);

            addTrial.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AddIntCountTrialFragment().show(getSupportFragmentManager(), "add trial");
                    Log.d("record msg activity","add experiment trial button pressed");

                }
            });
            trialList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Log.d("main activity","On item click to record trials");
                    IntCount trial_intcount = (IntCount) adapterView.getAdapter().getItem(i);//experiments[i];
                    new AlertDialog.Builder(ExperimentView.this)
                            .setTitle("Ignore Trial")
                            .setMessage("Would you like to ignore this trial?")
                            .setPositiveButton("Ignore", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    trialDataListIntCount.remove(trial_intcount);
                                    trialAdapterIntCount.notifyDataSetChanged();
                                    String id = trial_intcount.getTrialID();
                                    DatabaseReference myref = FirebaseDatabase.getInstance().getReference("Trail")
                                            .child(currentExperiment.getName())
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

        } else if (exp_type == 3) {
            //typeInStr = "Measurement";
            trialDataListMeasurement = new ArrayList<>();
            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Trial");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.hasChild(currentExperiment.getName())){
                        for(DataSnapshot datasnapshot: snapshot.child(currentExperiment.getName()).getChildren()){
                            Measurement measurement = datasnapshot.getValue(Measurement.class);
                            trialDataListMeasurement.add(measurement);
                        }
                        trialAdapterMeasurement.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            trialAdapterMeasurement = new MeasurementCustomList(this, trialDataListMeasurement);
            trialList.setAdapter(trialAdapterMeasurement);

            addTrial.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AddMeasurementTrialFragment().show(getSupportFragmentManager(), "add trial");
                    Log.d("record msg activity","add experiment trial button pressed");

                }
            });
            trialList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Log.d("main activity","On item click to record trials");
                    Measurement trial_measurement = (Measurement) adapterView.getAdapter().getItem(i);//experiments[i];
                    new AlertDialog.Builder(ExperimentView.this)
                            .setTitle("Ignore Trial")
                            .setMessage("Would you like to ignore this trial?")
                            .setPositiveButton("Ignore", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    trialDataListMeasurement.remove(trial_measurement);
                                    trialAdapterMeasurement.notifyDataSetChanged();
                                    String id = trial_measurement.getTrialID();
                                    DatabaseReference myref = FirebaseDatabase.getInstance().getReference("Trail")
                                            .child(currentExperiment.getName())
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

        }




        EndExperiment = findViewById(R.id.button_end_experiment);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User").child(userid);

        EndExperiment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentExperiment.setActive(false);
                expActive.setText("Status: Finished");
                EndExperiment.setClickable(false);
                addTrial.setClickable(false);
                HashMap data = new HashMap();
                boolean isfalse = false;
                data.put("active", isfalse);
                databaseReference.child("Experiment").child(currentExperiment.getName()).updateChildren(data);
                DatabaseReference df = FirebaseDatabase.getInstance().getReference("Experiment").child(currentExperiment.getName());
                df.updateChildren(data);
            }

        });

        expPublish = findViewById(R.id.unpublish_button);

        expPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String text = (String) expPublish.getText();
                expAvail.setText("PRIVATE");
                currentExperiment.setPublished(false);
                HashMap data = new HashMap();
                boolean isf = false;
                data.put("published", isf);
                databaseReference.child("Experiment").child(currentExperiment.getName()).updateChildren(data);
                DatabaseReference df1 = FirebaseDatabase.getInstance().getReference("Experiment").child(currentExperiment.getName());
                df1.removeValue();
            }
        });

        viewQuestion = findViewById(R.id.view_question);
        viewQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExperimentView.this, ViewQuestionActivity.class);
                intent.putExtra("experimentName", currentExperiment.getName());
                startActivity(intent);

            }
        });


        DatabaseReference dff = FirebaseDatabase.getInstance().getReference("User").child(userid);
        follow = findViewById(R.id.follow);
        dff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild("follow")){
                    for(DataSnapshot datasnapshot: snapshot.child("follow").getChildren()){
                        if (datasnapshot.child("name").getValue().toString().equals(currentExperiment.getName())){
                            follow.setImageResource(R.drawable.ic_action_liking);
                            follow.setTag(R.drawable.ic_action_liking);
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
                    currentExperiment.setOwner(owner);
                    dff.child("follow").child(currentExperiment.getName()).setValue(currentExperiment);
                }
            }
        });


        Button back = findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onOkPressed(Count newTrail1) {
        trialAdapterCount.add(newTrail1);
        DatabaseReference dataref = FirebaseDatabase.getInstance().getReference("Trail");
        String key = dataref.push().getKey();
        newTrail1.setTrialID(key);
        dataref.child(currentExperiment.getName()).child(key).setValue(newTrail1);
    }

    @Override
    public void onOkPressed(Binomial newTrail1) {
        trialAdapterBinomial.add(newTrail1);
        DatabaseReference dataref = FirebaseDatabase.getInstance().getReference("Trail");
        String key = dataref.push().getKey();
        newTrail1.setTrialID(key);
        dataref.child(currentExperiment.getName()).child(key).setValue(newTrail1);
    }

    @Override
    public void onOkPressed(IntCount newTrail1) {

        trialAdapterIntCount.add(newTrail1);
        DatabaseReference dataref = FirebaseDatabase.getInstance().getReference("Trail");
        String key = dataref.push().getKey();
        newTrail1.setTrialID(key);
        dataref.child(currentExperiment.getName()).child(key).setValue(newTrail1);
    }

    @Override
    public void onOkPressed(Measurement newTrail1) {

        trialAdapterMeasurement.add(newTrail1);
        DatabaseReference dataref = FirebaseDatabase.getInstance().getReference("Trail");
        String key = dataref.push().getKey();
        newTrail1.setTrialID(key);
        dataref.child(currentExperiment.getName()).child(key).setValue(newTrail1);
    }



}
