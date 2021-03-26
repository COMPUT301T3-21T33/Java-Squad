package com.example.java_squad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddExperimentActivity extends AppCompatActivity {
    EditText name, date, minTrails, rule, type, description;
    Button addbtn;
    FirebaseDatabase db;
    DatabaseReference df;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_experiment);


        Intent intent = getIntent();
        String userid = intent.getStringExtra("id");
        name = findViewById(R.id.Ename);
        date = findViewById(R.id.Edate);
        minTrails = findViewById(R.id.EminTrail);
        rule = findViewById(R.id.Erules);
        type = findViewById(R.id.Etype);
        description = findViewById(R.id.Edescription);

        String Ename = name.getText().toString();
        String Edate = date.getText().toString();
        int Emin = Integer.parseInt(minTrails.getText().toString());
        int Etype =  Integer.parseInt(type.getText().toString());
        String Erule = rule.getText().toString();
        String Edescription = description.getText().toString();

        db = FirebaseDatabase.getInstance();
        df = db.getReference("User").child(userid);

        /*addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Experimental newE = new Experimental(Ename, Edescription,Erule,Etype, Emin);
                df.child("Experiment").child(Ename).setValue(newE);
            }
        });*/

    }
}