package com.example.java_squad.Geo;

import android.Manifest;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.java_squad.AddBinomialTrialFragment;
import com.example.java_squad.Binomial;
import com.example.java_squad.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class SelectLocationActivity extends AppCompatActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    boolean isPermissionGranter;
    double longitude;
    double latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);

        checkPermission();

        if (isPermissionGranter) {
            if (checkGooglePlayServices()) {
//                Toast.makeText(ShowMap.this,"Google Play Services available", Toast.LENGTH_SHORT).show();
//                mapView.getMapAsync(this);
//                mapView.onCreate(savedInstanceState);
                mapFragment.getMapAsync(this);

            } else {
                Toast.makeText(SelectLocationActivity.this, "Google Play Services not available", Toast.LENGTH_SHORT).show();

            }
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                onBackPressed();

                Log.d("ok pressed","ok clicked should be back to previous fragment");

                Bundle bundle = new Bundle();
                bundle.putString("Longitude", String.valueOf(longitude));
                bundle.putString("Latitude", String.valueOf(latitude));
                Log.d("ok pressed","test 0000000000");
                Toast.makeText(SelectLocationActivity.this,"latitude = "+String.valueOf(latitude) + " longitude = "+String.valueOf(longitude), Toast.LENGTH_SHORT).show();

                finish();    //finish current activity and go back to previous Activity
            }
        });

    }
    private boolean checkGooglePlayServices(){
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        //int result=GoogleApiAvailability.isGooglePlayServicesAvailable(this);
        int result = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);

        if (result == ConnectionResult.SUCCESS){
            return true;
        } else if (googleApiAvailability.isUserResolvableError(result)){
            Dialog dialog = googleApiAvailability.getErrorDialog(this,result,201,new DialogInterface.OnCancelListener(){
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    Toast.makeText(SelectLocationActivity.this,"User Canceled Dialog", Toast.LENGTH_SHORT).show();
                }
            });
            dialog.show();

        }
        return false;
    }
    private void checkPermission () {
        Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                isPermissionGranter = true;
                Toast.makeText(SelectLocationActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("packet", getPackageName(), "");
                intent.setData(uri);
                startActivity(intent);
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(latLng.latitude + ":" +latLng.longitude);
                mMap.clear();
                //LatLng currentPosition = new LatLng(location.latitude, location.longitude);
//                Location loc = mMap.getMyLocation();
//                LatLng currentLoc = new LatLng(loc.getLatitude(), loc.getLongitude());
                //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLoc,17));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                mMap.addMarker(markerOptions);
                Log.d("long and lat : ",String.valueOf(latLng.latitude)+" "+ String.valueOf(latLng.longitude));
                longitude = latLng.latitude;
                latitude = latLng.longitude;
            }
        });
//
//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions()
//                .position(sydney)
//                .title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}