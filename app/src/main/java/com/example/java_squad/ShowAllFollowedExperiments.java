package com.example.java_squad;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.java_squad.user.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

public class ShowAllFollowedExperiments extends AppCompatActivity{
    ListView followedExpList; // Reference to listview inside activity_main.xml
    ArrayAdapter<Experimental> followedExpAdapter; // Bridge between dataList and cityList.
    ArrayList<Experimental> followedExpDataList; // Holds the data that will go into the listview
    DatabaseReference df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_all_experiments);
        Intent intent = getIntent();
        String userid = intent.getStringExtra("id");

        followedExpList = findViewById(R.id.experiment_list);
        followedExpDataList = new ArrayList<>();
        df = FirebaseDatabase.getInstance().getReference("User").child(userid);
        df.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild("follow")){
                    for (DataSnapshot datasnapshot: snapshot.child("follow").getChildren()){
                        followedExpDataList.clear();
                        Experimental exp = datasnapshot.getValue(Experimental.class);
                        followedExpDataList.add(exp);
                    }
                    followedExpAdapter.notifyDataSetChanged();
                }
                else{
                    followedExpDataList.clear();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        followedExpAdapter = new ExperimentCustomList(this, followedExpDataList);
        followedExpList.setAdapter(followedExpAdapter);


        followedExpList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Log.d("main activity","On item click to record trials");
                Experimental experiment = (Experimental) adapterView.getAdapter().getItem(i);//experiments[i];

                //get type
                //* type of trials to record in integer format.
                //* 0 = Count (how many did you see
                //* 1 = Binomial Trial (Pass / Fail)
                //* 2 = non-negative integer counts (each trial has 0 or more)
                //* 3 = measurement trials (like the temperature)
                Integer exp_type = experiment.getType();
                if (exp_type == 0){
                    Intent intent = new Intent(ShowAllFollowedExperiments.this, RecordIntCountTrial.class);

                    //Log.d("main activity","on item click to start record trails");

                    intent.putExtra("experiment", experiment);
                    intent.putExtra("id", userid);
                    startActivity(intent);

                }
                else if (exp_type == 1) {
                    Intent intent = new Intent(ShowAllFollowedExperiments.this, RecordBinomialTrial.class);

                    //Log.d("main activity","on item click to start record trails");

                    intent.putExtra("experiment", experiment);
                    intent.putExtra("id", userid);
                    startActivity(intent);
                }
                else if (exp_type == 2) {
                    Intent intent = new Intent(ShowAllFollowedExperiments.this, RecordCountTrial.class);

                    //Log.d("main activity","on item click to start record trails");

                    intent.putExtra("experiment", experiment);
                    intent.putExtra("id", userid);
                    startActivity(intent);
                }
                else if (exp_type == 3) {
                    Intent intent = new Intent(ShowAllFollowedExperiments.this, RecordMeasurementTrial.class);

                    //Log.d("main activity","on item click to start record trails");

                    intent.putExtra("experiment", experiment);
                    intent.putExtra("id", userid);
                    startActivity(intent);
                }

            }
        });
    }
}
