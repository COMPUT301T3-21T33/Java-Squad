package com.example.java_squad.Geo;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.java_squad.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class SelectLocationFragment extends Fragment implements OnMapReadyCallback{
    private GoogleMap mMap;
    boolean isPermissionGranter;
    Context ctx;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //super.onCreateView(inflater, container, savedInstanceState)
        View view = inflater.inflate(R.layout.main_map,container,false);
        Log.d("Select location","in fragment select location");
        SupportMapFragment supportMapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapView));
//        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapView);
        Log.d("Select location","find id");

        checkPermission();

        if (isPermissionGranter) {
            if (checkGooglePlayServices()) {
//                Toast.makeText(ShowMap.this,"Google Play Services available", Toast.LENGTH_SHORT).show();
//                mapView.getMapAsync(this);
//                mapView.onCreate(savedInstanceState);
                Log.d("Select location","try on map ready callback");

                supportMapFragment.getMapAsync((OnMapReadyCallback) this);

            }
//            else {
//                Toast.makeText(SelectLocationFragment.this, "Google Play Services not available", Toast.LENGTH_SHORT).show();
//            }
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        }
//
//        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
//            @Override
//            public void onMapReady(GoogleMap googleMap) {
//                // When the map is load
//                Log.d("Select location","map ready");
//
//                googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
//                    @Override
//                    public void onMapLongClick(LatLng latLng) {
//                        // click on the map
//                        MarkerOptions markerOptions = new MarkerOptions();
//                        markerOptions.position(latLng);
//                        markerOptions.title(latLng.latitude + ":" +latLng.longitude);
//                        googleMap.clear();
//                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
//                        googleMap.addMarker(markerOptions);
//                    }
//                });
//            }
//        });
        return view;
    }
    private boolean checkGooglePlayServices(){
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        //int result=GoogleApiAvailability.isGooglePlayServicesAvailable(this);
        int result = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getActivity());

        if (result == ConnectionResult.SUCCESS){
            Log.d("Select location","google play services connection success");

            return true;
        } else if (googleApiAvailability.isUserResolvableError(result)){
            Dialog dialog = googleApiAvailability.getErrorDialog(this,result,201,new DialogInterface.OnCancelListener(){
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    Toast.makeText(getActivity(),"User Canceled Dialog", Toast.LENGTH_SHORT).show();
                }
            });
            dialog.show();

        }
        return false;
    }
    private void checkPermission () {
        Log.d("Select location","checking permission");

        Dexter.withContext(ctx).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                isPermissionGranter = true;
                Toast.makeText(getActivity(), "Permission Granted", Toast.LENGTH_SHORT).show();
                Log.d("Select location","Permission Granted");
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Log.d("Select location","Permission denied");
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("packet", getActivity().getPackageName(), "");
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
        Log.d("Select location","map ready");

        mMap = googleMap;
        if (googleMap != null) {
            // your additional codes goes here
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
//                longitude = latLng.latitude;
//                latitude = latLng.longitude;
                }
            });
        }
        else {
            Log.d("Select Location","google map is null");
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ctx=context;
    }

}
