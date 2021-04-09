package com.example.java_squad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

/**
 * Activity for experiment owners to link barcodes to trials.
 */
public class BarcodeSetupActivity extends AppCompatActivity {
    Experimental currentExperiment;

    TextView experimentName;
    TextView currentBarcode;

    ListView barcodeList;
    ArrayAdapter<BarcodeTrial> barcodeAdapter;
    ArrayList<BarcodeTrial> barcodeTrials;

    String barcode;

    EditText value;
    EditText auxValue; // used for adding unit of measurement or object for those trials

    RadioGroup binomialValue;

    DatabaseReference df;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_setup);

        Intent intent = getIntent();
        currentExperiment = (Experimental) intent.getSerializableExtra("Experiment");

        experimentName = findViewById(R.id.textView_currentExp);
        currentBarcode = findViewById(R.id.textView_current_barcode);
        String expTitle = "Current Experiment: " + currentExperiment.getName();
        experimentName.setText(expTitle);

        value = findViewById(R.id.editText_value);
        auxValue = findViewById(R.id.editText_aux);
        binomialValue = findViewById(R.id.RadioGroup_bino);

        if (currentExperiment.getType() == 0 || currentExperiment.getType() == 3){
            binomialValue.setVisibility(View.GONE);
            value.setVisibility(View.VISIBLE);
            auxValue.setVisibility(View.VISIBLE);
            if (currentExperiment.getType() == 0)
                auxValue.setHint("Object");
            else
                auxValue.setHint("Units");
        }
        else if (currentExperiment.getType() == 1){
            binomialValue.setVisibility(View.VISIBLE);
            value.setVisibility(View.GONE);
            auxValue.setVisibility(View.GONE);
        }
        else{
            binomialValue.setVisibility(View.GONE);
            value.setVisibility(View.VISIBLE);
            auxValue.setVisibility(View.GONE);
        }

        //DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference();

    }

    private void launchScanner(View view){
        Intent intent = new Intent(this, BarcodeActivity.class);
        intent.putExtra("Experiment",currentExperiment);
        intent.putExtra("scanning", false);
        startActivity(intent);
        barcode = intent.getStringExtra("barcode");
        String dispBarcode = "Barcode: " + barcode;
        currentBarcode.setText(dispBarcode);
    }

    private void addBarcodeTrial(View view) {

        if (currentExperiment.getType() == 0) {
            int result = Integer.parseInt(value.getText().toString());
            String object = auxValue.getText().toString();

            Count trial = new Count("","",currentExperiment.getEnableGeo(), 0.0, 0.0,object, result);
            BarcodeTrial newTrial = new BarcodeTrial(barcode, trial, currentExperiment);
            //NEEDS DATABASE LINK HERE
            currentExperiment.barcodeTrials.add(newTrial);

        }
        else if (currentExperiment.getType() == 1){
            //binomial
            int radioButtonID = binomialValue.getCheckedRadioButtonId();
            View radioButton = binomialValue.findViewById(radioButtonID);

            int pass = binomialValue.indexOfChild(radioButton);
            String result;
            if (pass != 0)
                result = "pass";
            else
                result = "fail";

            Binomial trial = new Binomial("","",currentExperiment.getEnableGeo(), 0.0, 0.0, result);
            BarcodeTrial newTrial = new BarcodeTrial(barcode, trial, currentExperiment);
            //NEEDS DATABASE LINK HERE
            currentExperiment.barcodeTrials.add(newTrial);
        }
        else if (currentExperiment.getType() == 2){
            int result = Integer.parseInt(value.getText().toString());

            IntCount trial = new IntCount("","",currentExperiment.getEnableGeo(), 0.0, 0.0, result);
            BarcodeTrial newTrial = new BarcodeTrial(barcode, trial, currentExperiment);
            //NEEDS DATABASE LINK HERE
            currentExperiment.barcodeTrials.add(newTrial);

        }
        else if (currentExperiment.getType() == 3){
            int result = Integer.parseInt(value.getText().toString());
            String unit = auxValue.getText().toString();

            Measurement trial = new Measurement("","",currentExperiment.getEnableGeo(), 0.0, 0.0, unit,result);
            BarcodeTrial newTrial = new BarcodeTrial(barcode, trial, currentExperiment);
            //NEEDS DATABASE LINK HERE
            currentExperiment.barcodeTrials.add(newTrial);
        }
    }
}