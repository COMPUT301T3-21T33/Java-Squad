package com.example.java_squad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.HashMap;


public class
MainActivity extends AppCompatActivity {
    TextView tvUIDS, username,useremail;
    Button editProfile,showAllOwened;
    User user;
    String userid;
    ListView showAllOwnedList, followExp;
    ArrayAdapter<Experimental> allExpAdapter; // Bridge between dataList and cityList.
    ArrayList<Experimental> allExpDataList; // Holds the data that will go into the listview
    ArrayAdapter<Experimental> followExpAdapter; // Bridge between dataList and cityList.
    ArrayList<Experimental> followExpDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvUIDS = (TextView) findViewById(R.id.tv_uids);
        StringBuilder sb = new StringBuilder();

        TelephonyManager telMan = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        sb.append("Account ID:" + Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID) +"\n");
        //generate user id 
        userid = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        tvUIDS.setText(sb.toString());

        username = findViewById(R.id.tv_uname);
        useremail = findViewById(R.id.tv_uemail);
        //if user is first time use this account update database with she/his id
        //else pass
        user = new User("","",userid);
        user = new User("","",userid);
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("User");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(userid) == false){ 
                        myRef.child(userid).setValue(user);
                        username.setText("No name has been update");
                        useremail.setText("No email has been update");
                    } 
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //show all the information the user had on the screen
        showAllOwnedList = findViewById(R.id.trail_list);
        allExpDataList = new ArrayList<>();
        followExp = findViewById(R.id.follow_exp);
        followExpDataList = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User").child(userid);
            databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("username").getValue().toString();
                String email = snapshot.child("contact").getValue().toString();

                if (name.equals(null)){username.setText("No name has been update"); }
                else{username.setText(name);}

                if(email.equals(null)){useremail.setText("No email has been update");}
                else{useremail.setText(email);}


                if (snapshot.hasChild("Experiment")){
                    allExpDataList.clear();
                    for (DataSnapshot datasnapshot: snapshot.child("Experiment").getChildren()){
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

        //press this button, go to the edit profile activity
        editProfile = findViewById(R.id.editProfile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditProfileActivity.class);
                intent.putExtra("id", userid);
                startActivity(intent);
            }
        });

        //when press this button show all the experiment this user followed
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
        //when press this button show all the experiment this user had
        showAllOwened = findViewById(R.id.show_all_owned);
        showAllOwened.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowAllOwnedActivity.class);
                intent.putExtra("id", userid);
                startActivity(intent);
            }
        });
    }

/**
     * Called when the search button is pressed. go to search activity.
     * @param view
     * Button being pressed.
     */
    public void launchSearch(View view){
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("id", userid);
        startActivity(intent);
    }
    /**
     * Called when the creat experiment button is pressed. go to create experiment activity.
     * @param view
     * Button being pressed.
     */
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
