package com.test.serachapp;


import android.app.ProgressDialog;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    List<Experimental> experimentals = new ArrayList<>();
    RecyclerView recyclerView;
    private ExperimentalAdapter adatper;
    private FirebaseFirestore db;
    private EditText etContent;
    private ProgressDialog loading;


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
        adatper = new ExperimentalAdapter(this,experimentals);
        //Binding with recyclerview
        recyclerView.setAdapter(adatper);
        //Create FirebaseFirestore object
         db = FirebaseFirestore.getInstance();
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
        //Create CollectionReference object
        CollectionReference collection = db.collection("experimental");
        //Match query based on name

        collection.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        //close
                        loading.dismiss();
                        //success
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //document.toObject converts QueryDocumentSnapshot into an object
                                Experimental experimental = document.toObject(Experimental.class);
                                if (TextUtils.isEmpty(name)||experimental.getName().toLowerCase().contains(name.toLowerCase())
                                        ||experimental.getName().toLowerCase().equals(name.toLowerCase())
                                ||experimental.getStatus().toLowerCase().equals(name.toLowerCase())
                                        ||experimental.getDesc().toLowerCase().contains(name.toLowerCase())){
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
                });
    }
}
