package com.example.java_squad;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.auth.User;

public class QRCodeActivity extends AppCompatActivity {
    QRCode QRcode = new QRCode();
    private static final int passID = 1;
    private static final int failID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generate_qr);


        Intent intent = getIntent();
        Experimental experiment = (Experimental) intent.getSerializableExtra("experiment");

        EditText value = findViewById(R.id.;

        RadioGroup binoChoice = findViewById(R.id.binoRadioGroup);
        RadioButton pass = findViewById(R.id.pass_radio_button);
        RadioButton fail = findViewById(R.id.fail_radio_button);

        pass.setId(passID);
        fail.setId(failID);

        int inputType = InputType.TYPE_CLASS_NUMBER;

        if (experiment.getTypeString().equals("Count")){
            inputType += InputType.TYPE_NUMBER_FLAG_SIGNED;
        } else if (experiment.getTypeString().equals("Measurement")){
            inputType += InputType.TYPE_NUMBER_FLAG_DECIMAL;
        }
        else if (experiment.getTypeString().equals("Binomial")){
            pass.setVisibility(View.VISIBLE);
            fail.setVisibility(View.VISIBLE);
        }
        value.setInputType(inputType);

        //merge with location when moved to test branch

        CheckBox location = findViewById(R.id.TrialLocationCheckBox);

        if(experiment.()){
            location.setChecked(true);
        }

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Location was turned "+experiment.()+" for this experiment.", Toast.LENGTH_SHORT).show();
            }
        });

        MaterialButton generate = findViewById(R.id.generateCodeBTN);

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String QRCodeMessage;
                Log.d("Value", "" + value.getText().toString());

                if (experiment.getTypeString().equals("Binomial")){

                    QRCodeMessage = value.getText().toString() +","+experiment.getTypeString()+","+experiment.getRegionOn() + "," + findBinoType(binoChoice.getCheckedRadioButtonId());

                } else {

                    QRCodeMessage =  value.getText().toString() + "," + experiment.getTypeString() + "," + experiment.getRegionOn();

                }

                ImageView QRCode = findViewById(R.id.ReplaceImageQrCode);
                QRCode.setImageBitmap(QRcode.generate(QRCodeMessage));
                value.setText("");
            }
        });

    }//onCreate

    public Boolean findBinoType(int binoTypeID) {
        switch (binoTypeID) {
            case passID:
                return true;
            case failID:
                return false;
            default:
                return null;
        }
    }

}

}
