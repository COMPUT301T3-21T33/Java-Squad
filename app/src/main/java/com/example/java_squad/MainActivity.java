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

import com.example.java_squad.Geo.MapsActivity;
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
        
                user = new User("","",userid);
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("User");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot: snapshot.getChildren()) {
                    if (snapshot.hasChild(userid)){

                    }
                    else{
                        myRef.child(userid).setValue(user);

                    }
                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        TelephonyManager telMan = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        sb.append("Account ID:" + Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID) +"\n");

        userid = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        tvUIDS.setText(sb.toString());


        showAllOwnedList = findViewById(R.id.trail_list);
        allExpDataList = new ArrayList<>();
        followExp = findViewById(R.id.follow_exp);
        followExpDataList = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User").child(userid);
            databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name;
                String email;
                if(snapshot.child("username").getValue()==null){
                    name = "";
                }
                else{
                    name = snapshot.child("username").getValue().toString();
                }
                if(snapshot.child("contact").getValue()==null){
                    email = "";
                }
                else{
                    email = snapshot.child("contact").getValue().toString();
                }


                if (name.equals("")){username.setText("No name has been update"); }
                else{username.setText(name);}

                if(email.equals("")){useremail.setText("No email has been update");}
                else{useremail.setText(email);}


                if (snapshot.hasChild("Experiment")){
                    allExpDataList.clear();
                    for (DataSnapshot datasnapshot: snapshot.child("Experiment").getChildren()){
                        //Map<Experimental, Object> map = (Map<Experimental, Object>) datasnapshot.getValue();
                        Experimental exp = datasnapshot.getValue(Experimental.class);
                        allExpDataList.add(exp);
                    }
                    allExpAdapter.notifyDataSetChanged();
                }

                else{
                    allExpDataList.clear();

                }

                if (snapshot.hasChild("follow")){
                    followExpDataList.clear();
                    for (DataSnapshot datasnapshot: snapshot.child("follow").getChildren()){
                        Experimental experiment = datasnapshot.getValue(Experimental.class);
                        followExpDataList.add(experiment);
                    }
                    followExpAdapter.notifyDataSetChanged();
                }
                else{
                    followExpDataList.clear();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        allExpAdapter = new ExperimentCustomList(this, allExpDataList);
        showAllOwnedList.setAdapter(allExpAdapter);
        followExpAdapter = new ExperimentCustomList(this, followExpDataList);
        followExp.setAdapter(followExpAdapter);


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
    public void MapsActivity(View view){
        Intent intent = new Intent(this, MapsActivity.class);
//        intent.putExtra("user", user);
        startActivity(intent);
    }
    /** public void launchexperiment(View view){
     * Intent intent = new Intent(this, com.test.experiment.class);
     *         startActivity(intent);
     */
}
