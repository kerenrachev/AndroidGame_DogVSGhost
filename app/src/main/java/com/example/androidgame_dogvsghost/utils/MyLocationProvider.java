package com.example.androidgame_dogvsghost.utils;

import static androidx.core.app.ActivityCompat.requestPermissions;

import com.example.androidgame_dogvsghost.callbacks.CallBack_GPSLocationReturned;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

public class MyLocationProvider {

    private CallBack_GPSLocationReturned callBack_gpsLocationReturned;
    private LocationRequest locationRequest;
    private Context context;

    private MyLocationProvider(Context context) {
        this.context = context;
    }

    private static MyLocationProvider me;

    public static MyLocationProvider getMe() {
        return me;
    }

    public MyLocationProvider setCallBack_gpsLocationReturned(CallBack_GPSLocationReturned callBack_gpsLocationReturned) {
        this.callBack_gpsLocationReturned = callBack_gpsLocationReturned;
        return this;
    }

    public static MyLocationProvider initHelper(Context context) {
        if (me == null) {
            me = new MyLocationProvider(context);
        }
        return me;
    }

    /**
     * I dont know why, but permissions are defined in Manifest, and still, without this "SuppressLint" Looks like an error.
     * @param myLocation - Location object
     * @param activity - Activity that called this function
     * On Location result - call the GPSLocationReturned Callback.
     */
    @SuppressLint("MissingPermission")
    public void getCurrentLocation(MyLoaction myLocation, Activity activity) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, "Pleas wait, getting Location... ", Toast.LENGTH_LONG).show();
                if (isGPSEnabled()) {

                    LocationServices.getFusedLocationProviderClient(context)
                            .requestLocationUpdates(locationRequest, new LocationCallback() {
                                @Override
                                public void onLocationResult(@NonNull LocationResult locationResult) {
                                    super.onLocationResult(locationResult);

                                    LocationServices.getFusedLocationProviderClient(context)
                                            .removeLocationUpdates(this);

                                    if (locationResult != null && locationResult.getLocations().size() >0){

                                        int index = locationResult.getLocations().size() - 1;
                                        double latitude = locationResult.getLocations().get(index).getLatitude();
                                        double longitude = locationResult.getLocations().get(index).getLongitude();
                                        myLocation.setLat(latitude)
                                                .setLng(longitude);
                                        callBack_gpsLocationReturned.GPSLocationReturned();
                                    }
                                }
                            }, Looper.getMainLooper());

                } else {
                    turnOnGPS(activity);
                }

            } else {
                requestPermissions(activity,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            }
        }
    }

    public void turnOnGPS(Activity activity) {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(context)
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    Toast.makeText(context, "GPS is already tured on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(activity, 2);
                            } catch (IntentSender.SendIntentException ex) {
                                ex.printStackTrace();
                            }
                            break;

                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            //Device does not have location
                            break;
                    }
                }
            }
        });

    }

    public boolean isGPSEnabled() {
        LocationManager locationManager = null;
        boolean isEnabled = false;

        if (locationManager == null) {
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        }

        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isEnabled;

    }

    public void initiateLocationProvider() {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);
    }
}
