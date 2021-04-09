package com.example.java_squad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.java_squad.user.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Activity for entering info for a new experiment or updating an old one.
 */
public class ExperimentConstructor extends AppCompatActivity {

    EditText expName;

    EditText expDesc;
    EditText expRules;
    RadioGroup trialType;
    RadioGroup enableGeo;
    EditText minTrials;
    Button submit;
    FirebaseDatabase db;
    String userName;
    String userEmail;
    String ID;
    User owner;
    DatabaseReference df, saveToExperiment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiment_constructor);
        Log.d("fried", "you are here");
        Intent intent = getIntent();
        String userid = intent.getStringExtra("id");

        expName = findViewById(R.id.editText_name);
        expDesc = findViewById(R.id.editText_description);
        expRules = findViewById(R.id.editText_rules);
        minTrials = findViewById(R.id.editText_minTrials);
        trialType = findViewById(R.id.RadioGroup);
        enableGeo = findViewById(R.id.geoRadioGroup);
        submit = findViewById(R.id.button_submit);

        df =  FirebaseDatabase.getInstance().getReference("User").child(userid).child("Experiment");
        saveToExperiment = FirebaseDatabase.getInstance().getReference("Experiment");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("frieda0", "this is submit");

                int radioButtonID = trialType.getCheckedRadioButtonId();
                View radioButton = trialType.findViewById(radioButtonID);

                String Ename = expName.getText().toString();

                int Emin = Integer.parseInt(minTrials.getText().toString());
                int Etype = trialType.indexOfChild(radioButton);
                String Erule = expRules.getText().toString();
                String Edescription = expDesc.getText().toString();

                int geoRadioButtonID = enableGeo.getCheckedRadioButtonId();
                View geoRadioButton = enableGeo.findViewById(geoRadioButtonID);
                int geoidx = enableGeo.indexOfChild(geoRadioButton);
                Log.d("Experiment Constructor",String.valueOf(geoidx));
                Experimental newE = new Experimental(new User(),Ename, Edescription,Erule,Etype, Emin,geoidx);

                FirebaseDatabase.getInstance().getReference("User").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        userName = snapshot.child(userid).child("username").getValue().toString();
                        userEmail = snapshot.child(userid).child("contact").getValue().toString();
                        ID = snapshot.child(userid).child("userID").getValue().toString();
                        owner = new User();
                        owner.setUserID(ID);
                        owner.setContact(userEmail);
                        owner.setUsername(userName);

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

                Query query = saveToExperiment.orderByChild("name").equalTo(Ename);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Boolean isEname = false;

                        for (DataSnapshot dataSnapshot: snapshot.getChildren()){

                            if (dataSnapshot.child("name").getValue().toString().equals(Ename))
                            {
                                isEname = true;
                            }
                        }

                        if(Ename.equals("") ) {
                            expName.setError("Name cannot be empty");
                        }
                        else if (isEname){
                            expName.setError("This name have been used");
                        }
                        else{
                            df.child(Ename).setValue(newE);
                            Experimental addToExp = new Experimental(owner,Ename, Edescription,Erule,Etype, Emin,geoidx);
                            saveToExperiment.child(Ename).setValue(addToExp);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    /**
     * Called when the cancel button is pressed. Returns to the previous activity.
     * @param view
     * Button being pressed.
     */
    public void cancelButton(View view){
        finish();
    }

}