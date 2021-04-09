package com.example.java_squad;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * search Activity, find keywords in the database and display some specific fields of activity
 * @param: all parameters in the experiment in the database
 * @return: active, ownername, descrption in the database
 */

public class SearchActivity extends AppCompatActivity implements ExperimentalAdapter.OnNoteListener  {
    List<Experimental> experimentals = new ArrayList<>();
    RecyclerView recyclerView;
    private ExperimentalAdapter adatper;
    private EditText etContent;
    private ProgressDialog loading;
    Intent intent;
    String userid;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        recyclerView = findViewById(R.id.recycrview);
        etContent = findViewById(R.id.et_content);
        //Create dialog progress box
        loading = new ProgressDialog(this);
        //Set up LinearLayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Create adapter
        adatper = new ExperimentalAdapter(this,experimentals,this);
        //Binding with recyclerview
        recyclerView.setAdapter(adatper);
        intent = getIntent();
        userid = intent.getStringExtra("id");


        //Create FirebaseFirestore object
        //Set click event
        findViewById(R.id.bt_go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = etContent.getText().toString();
                search(content);
            }
        });
    }
    private void search(final String name){
        //Open progress box
        loading.show();
        experimentals.clear();
        //Search experimental table
        //Match query based on name
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Experiment");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                loading.dismiss();
                //success
                if (snapshot.exists()) {
                    for (DataSnapshot document : snapshot.getChildren()) {
                        //document.toObject converts QueryDocumentSnapshot into an object
                        Experimental experimental = document.getValue(Experimental.class);
                        if (TextUtils.isEmpty(name)||experimental.getName().toLowerCase().contains(name.toLowerCase())
                                ||experimental.getName().toLowerCase().equals(name.toLowerCase())
                                ||experimental.getDescription().toLowerCase().contains(name.toLowerCase())
                                ||experimental.getRules().toLowerCase().contains(name.toLowerCase())
                                ||experimental.getTypeString().toLowerCase().contains(name.toLowerCase())
                                ||(experimental.getPublished()+"").contains(name.toLowerCase())
                                ||(experimental.getType()+"").contains(name.toLowerCase())
                                ||(experimental.getOwner().getUsername()+"").contains(name.toLowerCase())
                                ||(experimental.getMinTrials()+"").contains(name.toLowerCase())
                                ||(experimental.getActive()+"").toLowerCase().contains(name.toLowerCase())){
                            experimentals.add(experimental);
                        }

                    }
                    //Refresh adapter
                    adatper.notifyDataSetChanged();
                } else {
                    //fail
                    Toast.makeText(SearchActivity.this,"error",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void onNoteClick(int position){
        Experimental experiment = experimentals.get(position);
        Integer exp_type = experimentals.get(position).getType();
        String thisUserID = experiment.getOwner().getUserID();


            if (exp_type == 0){
                Intent intent = new Intent(this, RecordIntCountTrial.class);

                //Log.d("main activity","on item click to start record trails");

                intent.putExtra("experiment", experiment);
                intent.putExtra("id", userid);
                startActivity(intent);

            }
            else if (exp_type == 1) {
                Intent intent = new Intent(this, RecordBinomialTrial.class);

                //Log.d("main activity","on item click to start record trails");

                intent.putExtra("experiment", experiment);
                intent.putExtra("id", userid);
                startActivity(intent);
            }
            else if (exp_type == 2) {
                Intent intent = new Intent(this, RecordCountTrial.class);

                //Log.d("main activity","on item click to start record trails");

                intent.putExtra("experiment", experiment);
                intent.putExtra("id", userid);
                startActivity(intent);
            }
            else if (exp_type == 3) {
                Intent intent = new Intent(this, RecordMeasurementTrial.class);

                //Log.d("main activity","on item click to start record trails");

                intent.putExtra("experiment", experiment);
                intent.putExtra("id", userid);
                startActivity(intent);
            }




    }
}

