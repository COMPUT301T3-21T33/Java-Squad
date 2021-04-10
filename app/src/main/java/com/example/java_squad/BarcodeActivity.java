package com.example.java_squad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

/**
 * Activity for using the camera to scan a barcode.
 * Some code for starting camera and scanning barcode adapted from https://learntodroid.com/how-to-create-a-qr-code-scanner-app-in-android/
 */
public class BarcodeActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CAMERA = 0;

    private PreviewView previewView;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;

    private Button barcodeFoundButton;
    private String barcode;

    private boolean scanning = true; //if true, scanning for existing barcode. if false, registering a new barcode.

    private Experimental currentExperiment;
    private BarcodeTrial currentTrial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        previewView = findViewById(R.id.cameraPreview);

        Intent intent = getIntent();
        currentExperiment = (Experimental) intent.getSerializableExtra("Experiment");
        scanning = intent.getBooleanExtra("scanning", true);

        barcodeFoundButton = findViewById(R.id.button_codeFound);
        barcodeFoundButton.setVisibility(View.INVISIBLE);
        if (!scanning)
            barcodeFoundButton.setText("Register Barcode");
        barcodeFoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), barcode, Toast.LENGTH_SHORT).show();
                Log.i(MainActivity.class.getSimpleName(), "Barcode Found: " + barcode);

                if (scanning){
                    //NEEDS DATABASE LINK HERE
                    currentTrial.addTrialToExp();
                }
                else{
                    intent.putExtra("barcode",barcode);
                    finish();
                }
            }
        });

        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        requestCamera();
    }

    /**
     * Adapted from learntodroid.com
     * Requests camera permissions from the user before launching the camera if successful.
     */
    private void requestCamera() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startCamera();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(BarcodeActivity.this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
            }
        }
    }

    /**
     * Adapted from learntodroid.com
     * Starts camera with on-screen preview.
     */
    private void startCamera() {
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindCameraPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                Toast.makeText(this, "Error starting camera " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera();
            } else {
                Toast.makeText(this, "Camera Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void bindCameraPreview(@NonNull ProcessCameraProvider cameraProvider) {
        previewView.setImplementationMode(PreviewView.ImplementationMode.PERFORMANCE);

        Preview preview = new Preview.Builder()
                .build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        ImageAnalysis imageAnalysis =
                new ImageAnalysis.Builder()
                        .setTargetResolution(new Size(1280, 720))
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build();

        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this), new BarcodeImageAnalyzer(new BarcodeFoundListener() {
            @Override
            public void onBarcodeFound(String _barcode) {
                barcode = _barcode;
                if (scanning){
                    boolean show = false;
                    for (BarcodeTrial trial : currentExperiment.barcodeTrials){
                        if (barcode == trial.getBarcode()){
                            //if barcode matches an existing trial allow user to execute it
                            show = true;
                            currentTrial = trial;
                        }
                    }
                    if (show)
                        barcodeFoundButton.setVisibility(View.VISIBLE);
                }
                else
                    barcodeFoundButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void barcodeNotFound() {
                barcodeFoundButton.setVisibility(View.INVISIBLE);
            }
        }));

        Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner)this, cameraSelector, preview);
    }


}