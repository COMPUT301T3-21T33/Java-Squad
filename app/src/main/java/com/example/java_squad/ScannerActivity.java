package com.example.java_squad;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.UUID;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    ZXingScannerView scannerView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DateConverter stringDate = new DateConverter();


    Intent intent = getIntent();
    Experimental experiment = (Experimental) intent.getSerializableExtra("experiment");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);

        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        scannerView.startCamera();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(getBaseContext(), "" + permissionDeniedResponse.isPermanentlyDenied(), Toast.LENGTH_SHORT).show();
                        if (permissionDeniedResponse.isPermanentlyDenied()) {
                            //permission is permanently denied navigate to user setting
                            new AlertDialog.Builder(ScannerActivity.this)
                                    .setTitle("Camera permission was denied permanently.")
                                    .setMessage("Allow Camera access through your settings.")
                                    .setPositiveButton("Go To Settings", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                                            intent.setData(uri);
                                            startActivityForResult(intent, 101);
                                            dialog.cancel();
                                            finish();
                                        }
                                    })
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.cancel();
                                                    finish();
                                                }
                                            }
                                    ).show();

                        } else {
                            finish();
                        }

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();

                    }
                }).check();
    }

    @Override
    public void handleResult(Result rawResult) {
        Log.e("format", rawResult.getBarcodeFormat().toString());
        Intent intent = getIntent();
        Experimental experiment = (Experimental) intent.getSerializableExtra("experiment");
        String type = intent.getStringExtra("Flag");
        if (type.equals("Scan")) {
            if (rawResult.getBarcodeFormat().toString().contains("QR_CODE")) {
                QRCodeScanned(rawResult.toString());
            }
            onBackPressed();
        }

    }


    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        scannerView.stopCamera();
    }

    private void QRCodeScanned(String rawResult) {
        String[] values = rawResult.split(",");
        db      .collection("Experiment")
                .document(experiment.getExpID())
                .collection("Trails")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            int count = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                count+=1;
                            }
                            if (count >= experiment.getMinTrials()){
                                onBackPressed();
//
                            } else{
                                if (values[3].equals("Binomial")) {
                                    //Need to add in given number of binomial trials
                                    if (count+Integer.parseInt(String.valueOf((values[2])))> experiment.getMinTrials()){
                                        onBackPressed();
                                    }
                                    else{
                                        for (int i = 1; i <= Integer.parseInt(values[2]); i++) {
                                            Trial trial = new Trial(Boolean.parseBoolean(values[4]),
                                                    values[3],
                                                    Boolean.parseBoolean(values[5]),
                                                    values[1],
                                            db.collection("Experiments")
                                                    .document(values[0])
                                                    .collection("Trials")
                                                    .document(trial.getTrialID()), trial);
                                        }
                                    }
                                } else {
                                    Trial trial = new Trial(Boolean.parseBoolean(values[4]),
                                            values[3],
                                            Float.parseFloat(values[2]),
                                            values[1],
                                            UUID.randomUUID().toString(),
                                    db.collection("Experiments")
                                            .document(values[0])
                                            .collection("Trials")
                                            .document(Trial.getTrialID()), trial);
                                }
                            }
                        }
                    }
                });
    }