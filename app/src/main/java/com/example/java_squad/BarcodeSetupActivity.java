package com.example.java_squad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Activity for experiment owners to link barcodes to trials.
 */
public class BarcodeSetupActivity extends AppCompatActivity {
    Experimental currentExperiment;

    TextView experimentName;

    ListView barcodeList;
    ArrayAdapter<BarcodeTrial> barcodeAdapter;
    ArrayList<BarcodeTrial> barcodeTrials;

    String barcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_setup);

        Intent intent = getIntent();
        currentExperiment = (Experimental) intent.getSerializableExtra("Experiment");

        experimentName = findViewById(R.id.textView_currentExp);
        String expTitle = "Current Experiment: " + currentExperiment.getName();
        experimentName.setText(expTitle);

    }

    private void launchScanner(View view){
        Intent intent = new Intent(this, BarcodeActivity.class);
        intent.putExtra("Experiment",currentExperiment);
        intent.putExtra("scanning", false);
        startActivity(intent);
        barcode = intent.getStringExtra("barcode");
        handleBarcode(barcode);
    }

    private void handleBarcode(String barcode){
        if (currentExperiment.getType() == 0){
            
        }

    }
}