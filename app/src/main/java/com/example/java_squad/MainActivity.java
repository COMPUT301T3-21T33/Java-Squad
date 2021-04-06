package com.example.java_squad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.java_squad.user.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {
    TextView tvUIDS;
    Button editProfile;
    User user;
    FirebaseDatabase db;
    DatabaseReference df;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvUIDS = (TextView) findViewById(R.id.tv_uids);
        StringBuilder sb = new StringBuilder();

        TelephonyManager telMan = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        sb.append("Account ID:" + Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID) +"\n");

        userid = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        tvUIDS.setText(sb.toString());



        user = new User("","",userid);
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("User");
        Query query = myRef.equalTo(userid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot: snapshot.getChildren()) {
                    String parent = childSnapshot.getKey();
                    if (parent != userid){
                        myRef.child(userid).setValue(user);
                    }

                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //DatabaseReference r =FirebaseDatabase.getInstance().getReference("Question");
        //r.child("experiment2").removeValue();


        editProfile = findViewById(R.id.editProfile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditProfileActivity.class);
                intent.putExtra("id", userid);
                startActivity(intent);
            }
        });


        Button addTrialButton = findViewById(R.id.show_all_followed_exp);
        addTrialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new AddMeasurementTrialFragment().show(getSupportFragmentManager(), "add trial");
                Intent intent = new Intent(MainActivity.this, ShowAllFollowedExperiments.class);
                intent.putExtra("id", userid);
                startActivity(intent);
                Log.d("show all exp activity","show all experiments button clicked");

            }
        });


    }


    public void launchSearch(View view){
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("id", userid);
        startActivity(intent);
    }

    public void createExperiment(View view){
        Intent intent = new Intent(view.getContext(), ExperimentConstructor.class);
        intent.putExtra("id", userid);
        startActivity(intent);
    }

    /** public void launchexperiment(View view){
     * Intent intent = new Intent(this, com.test.experiment.class);
     *         startActivity(intent);
     */
}
