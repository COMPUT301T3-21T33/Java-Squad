package com.example.java_squad;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
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
    FirebaseDatabase database;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

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
                        Toast.makeText(getBaseContext(), ""+permissionDeniedResponse.isPermanentlyDenied(),  Toast.LENGTH_SHORT).show();
                        if (permissionDeniedResponse.isPermanentlyDenied()){
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

        String[] values = rawResult.getText().split(",");

        if (rawResult.getBarcodeFormat().toString().contains("QR_CODE")) {

            if (values[3].equals("Binomial")){
                //Still need to finish
                for (int i = 1; i <= Integer.parseInt(values[2]); i++ ){
                    Trial trial = new Trial(Boolean.parseBoolean(values[4], values[3],
                            Boolean.parseBoolean(values[5]), values[1],
                            UUID.randomUUID().toString());
                    database.(db.collection("Experiments")
                            .document(values[0])
                            .collection("Trials")
                            .document(trial.()), trial);
                }

            } else{
                Trial trial = new Trial(Boolean.parseBoolean(values[4]),
                        values[3],
                        Float.parseFloat(values[2]),
                        values[1],
                        UUID.randomUUID().toString());
                database.(db.collection("Experiments")
                        .document(values[0])
                        .collection("Trials")
                        .document(trial.()), trial);
            }
            onBackPressed();

        } else {
            View trialView = LayoutInflater.from(ScannerActivity.this).inflate(R.layout.activity_barcode, null);


            AlertDialog.Builder builder = new AlertDialog.Builder(ScannerActivity.this);
            builder.setTitle("Enter value for trial")
                    .setView(trialView)
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            onBackPressed();
                        }
                    })
                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            onBackPressed();
                        }
                    })
                    .create()
                    .show();
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

}
}
