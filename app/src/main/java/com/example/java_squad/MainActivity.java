package com.example.java_squad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;
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

        tvUIDS.setText(sb.toString();

    }

    public void onDestroy(){
        super.onDestroy();
        FirebaseAuth.getInstance().getCurrentUser().delete();
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
}
