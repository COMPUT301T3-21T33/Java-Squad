package com.example.java_squad;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.java_squad.user.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.HashMap;

public class ViewFollowActivity extends AppCompatActivity {
    Button follow,addTrail, viewStatistics, viewQuestion;
    Experimental experiment;
    FirebaseAuth auth;
    FirebaseUser current;
    User user;
    TextView experimentName;
    TextView experimentOwenr;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_follow2);
        Intent intent = getIntent();
        String userid = intent.getStringExtra("id");
        auth = FirebaseAuth.getInstance();
        current = auth.getCurrentUser();
        experimentName =findViewById(R.id.experiment_name);
        addTrail = findViewById(R.id.add_trial_button);
        experimentOwenr = findViewById(R.id.owner);
        String owner = experimentOwenr.getText().toString();
        database = FirebaseDatabase.getInstance();
        viewQuestion =findViewById(R.id.view_question_button);
        viewStatistics =findViewById(R.id.view_stat_button);
        follow = findViewById(R.id.follow_button);
        String eName = experimentName.getText().toString();
        myRef = FirebaseDatabase.getInstance().getReference("User");
        Query query = myRef.orderByChild("name").equalTo(owner);
        Experimental exp = new Experimental();

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Experimental exp = snapshot.child(owner).getValue(Experimental.class);
                String description = exp.getDescription();
                User user = exp.getOwner();
                String name = exp.getName();
                String rules = exp.getRules();
                int type = exp.getType();
                int minTrials = exp.getMinTrials();
                exp.setDescription(description);
                exp.setMinTrials(minTrials);
                exp.setName(name);
                exp.setOwner(user);
                exp.setRules(rules);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("find3", "clicked follow");
                Toast.makeText(ViewFollowActivity.this, "fail to update account", Toast.LENGTH_LONG).show();
                if(follow.getText().toString().equals("follow")){
                    follow.setText("following");
                    viewStatistics.setClickable(true);
                    viewQuestion.setClickable(true);

                    myRef.child("followed").setValue(exp);

                }
                else{
                    follow.setText("follow");
                    viewStatistics.setClickable(false);
                    viewQuestion.setClickable(false);
                    addTrail.setClickable(false);
                    myRef.child(eName).removeValue();
                }
            }
        });

    }
    }
