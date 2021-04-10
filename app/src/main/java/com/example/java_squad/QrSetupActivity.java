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
public class QrSetupActivity extends AppCompatActivity{
    Experimental currentExperiment;

    TextView experimentName;
    TextView currentQrcode;

    ListView qrCodeList;
    ArrayAdapter<QrCodeTrial> qrCodeTrialArrayAdapter;
    ArrayList<QrCodeTrial> qrCodeTrials;

    String qrcode;

    EditText value;
    EditText auxValue;

    RadioGroup binomialValue;

    DatabaseReference df;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_setup);

        Intent intent = getIntent();
        currentExperiment = (Experimental) intent.getSerializableExtra("Experiment");

        experimentName = findViewById(R.id.textView_currentExp);
        currentQrcode = findViewById(R.id.textView_current_Qrcode);
        currentQrcode = findViewById(R.id.textView_current_Qrcode);
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

    }

    private void launchScanner(View view){
        Intent intent = new Intent(this, QrCodeActivity.class);
        intent.putExtra("Experiment",currentExperiment);
        intent.putExtra("scanning", false);
        startActivity(intent);
        qrcode = intent.getStringExtra("qrcode");
        String dispQrcode = "QrCode " + qrcode;
        currentQrcode.setText(dispQrcode);
    }

    private void addqrcodeTrial(View view) {

        if (currentExperiment.getType() == 0) {
            int result = Integer.parseInt(value.getText().toString());
            String object = auxValue.getText().toString();

            Count trial = new Count("","",currentExperiment.getEnableGeo(), 0.0, 0.0,object, result);
            QrCodeTrial newTrial = new QrCodeTrial(qrcode, trial, currentExperiment);
            currentExperiment.QrCodeTrial.add(newTrial);

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
            QrCodeTrial newTrial = new QrCodeTrial(qrcode, trial, currentExperiment);
            currentExperiment.QrCodeTrial.add(newTrial);
        }
        else if (currentExperiment.getType() == 2){
            int result = Integer.parseInt(value.getText().toString());

            IntCount trial = new IntCount("","",currentExperiment.getEnableGeo(), 0.0, 0.0, result);
            QrCodeTrial newTrial = new QrCodeTrial(qrcode, trial, currentExperiment);
            currentExperiment.QrCodeTrial.add(newTrial);

        }
        else if (currentExperiment.getType() == 3){
            int result = Integer.parseInt(value.getText().toString());
            String unit = auxValue.getText().toString();

            Measurement trial = new Measurement("","",currentExperiment.getEnableGeo(), 0.0, 0.0, unit,result);
            QrCodeTrial newTrial = new QrCodeTrial(qrcode, trial, currentExperiment);
            currentExperiment.QrCodeTrial.add(newTrial);
        }
    }

}
