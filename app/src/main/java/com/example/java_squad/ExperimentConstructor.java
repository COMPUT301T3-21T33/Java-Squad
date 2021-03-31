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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Activity for entering info for a new experiment or updating an old one.
 */
public class ExperimentConstructor extends AppCompatActivity {

    EditText expName;
    EditText expDesc;
    EditText expRules;
    RadioGroup trialType;
    EditText minTrials;
    Button submit;
    FirebaseDatabase db;
    DatabaseReference df;

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
        submit = findViewById(R.id.button_submit);


        df =  FirebaseDatabase.getInstance().getReference("User").child(userid).child("Experiment");



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


                Experimental newE = new Experimental(new User(),Ename, Edescription,Erule,Etype, Emin);
                df.child(Ename).setValue(newE).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){

                            Log.d("frieda","successful" );
                        }
                        else{
                            Log.d("frieda1", "not successful");
                        }
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

    /*public void submitButton(View view){
        String newName = expName.getText().toString();
        String newDesc = expDesc.getText().toString();
        String newRules = expRules.getText().toString();
        int newMinTrials = Integer.parseInt(minTrials.getText().toString());

        int radioButtonID = trialType.getCheckedRadioButtonId();
        View radioButton = trialType.findViewById(radioButtonID);
        int idx = trialType.indexOfChild(radioButton);

        Experimental newExperiment = new Experimental(owner, newName, newDesc, newRules, idx , newMinTrials);

        //put exp on firebase

        finish();
    }*/
}
