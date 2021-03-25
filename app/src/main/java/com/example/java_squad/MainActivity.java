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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {
    TextView tvUIDS;
    Button editProfile;
    User user;
    FirebaseDatabase db;
    DatabaseReference df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvUIDS = (TextView) findViewById(R.id.tv_uids);
        StringBuilder sb = new StringBuilder();

        TelephonyManager telMan = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        sb.append("Account ID:" + Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID) +"\n");

        String userid = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        tvUIDS.setText(sb.toString());

        db = FirebaseDatabase.getInstance();
        df =db.getReference("User").child(userid);
        Log.d("TAG123", Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));

        user = new User();
        user.setUsername("");
        user.setContact("");

        HashMap data = new HashMap();
        data.put("Name", "");
        data.put("Email", "");
        df.updateChildren(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.d("TAG", "onComplete: successful created an account");
                }
                else{
                    Log.d("Tag", "fail to create an account");
                }
            }
        });

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
                startActivity(new Intent(MainActivity.this, ShowAllFollowedExperiments.class));
                Log.d("show all exp activity","show all experiments button clicked");

            }
        });


    }


    public void launchSearch(View view){
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    public void createExperiment(View view){
        Intent intent = new Intent(view.getContext(), EditProfileActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    /** public void launchexperiment(View view){
     * Intent intent = new Intent(this, com.test.experiment.class);
     *         startActivity(intent);
     */
}
