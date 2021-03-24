package com.example.java_squad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tvUIDS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvUIDS = (TextView) findViewById(R.id.tv_uids);
        StringBuilder sb = new StringBuilder();

        TelephonyManager telMan = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        sb.append("Account ID:" + Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID) +"\n");
        
        tvUIDS.setText(sb.toString());


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

    /** public void launchexperiment(View view){
     * Intent intent = new Intent(this, com.test.experiment.class);
     *         startActivity(intent);
     */
}
