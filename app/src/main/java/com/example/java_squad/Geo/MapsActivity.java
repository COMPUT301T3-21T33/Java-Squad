package com.example.java_squad.Geo;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.java_squad.Binomial;
import com.example.java_squad.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
/**
 * MapsActivity class shows all the trials' Geo locations
 */
public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    boolean isPermissionGranter;
    String experimentName;
    Double latitude;
    Double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Intent intent = getIntent();
        experimentName = intent.getStringExtra("experimentName");
        Log.d("map activity","get experiment");

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
                Toast.makeText(MapsActivity.this, "Google Play Services not available", Toast.LENGTH_SHORT).show();

            }
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        }
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
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
                    Toast.makeText(MapsActivity.this,"User Canceled Dialog", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(MapsActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();

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
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Trail");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("map activity","get data base");
                if (snapshot.hasChild(experimentName)){
                    for (DataSnapshot datasnapshot: snapshot.child(experimentName).getChildren()){
//                        String userKey = datasnapshot.getKey();
//                        Log.d("add marker get key",userKey);
                        String latitudeStr = datasnapshot.child("latitude").getValue().toString();
                        String longitudeStr = datasnapshot.child("longitude").getValue().toString();
                        latitude = Double.parseDouble(latitudeStr);
                        longitude = Double.parseDouble(longitudeStr);
                        LatLng coord = new LatLng(latitude, longitude);
                        mMap.addMarker(new MarkerOptions()
                                .position(coord));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(coord));
                        Log.d("map activity",latitudeStr + " "+longitudeStr);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}