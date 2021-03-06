package com.wellness.eva.messaging;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.pubnub.api.Pubnub;
import com.wellness.eva.R;
import com.wellness.eva.validation.PreferencesObserver;
import com.wellness.eva.validation.UserPreferences;

/**
 * Created by sindyg on 7/15/2016.
 */
public class BroadcastEmergency extends PreferencesObserver implements
        GoogleApiClient.ConnectionCallbacks, LocationListener {

    // =============================================================================================
    // Properties
    // =============================================================================================
    private final String channelName =  "EVA_Broadcast";
    private static final String TAG = "Tracker - GMaps Share";
    private boolean mRequestingLocationUpdates = true;
    private Context context;

    // Google API - Locations
    private GoogleApiClient mGoogleApiClient;

    // Google Maps
    private GoogleMap mGoogleMap;
    private PolylineOptions mPolylineOptions;
    private LatLng mLatLng;

    //User preferences
    private UserPreferences userPreferences;

    // PubNub
    private Pubnub mPubnub;

    // =============================================================================================
    // Class constructor
    // =============================================================================================

    public BroadcastEmergency(Context context, UserPreferences userPreferences)
    {
        this.context = context;
        this.userPreferences = userPreferences;
        this.userPreferences.attach(this);

        // Start Google Client
        this.buildGoogleApiClient();

        // Start PubNub
        mPubnub = PubNubManager.startPubnub();
    }

    private synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this).addApi(LocationServices.API)
                .build();
    }

    // =============================================================================================
    // Google Location API CallBacks
    // =============================================================================================

    @Override
    public void onConnected(Bundle connectionHint) {
        Log.d(TAG, "Connected to Google API for Location Management");
        if (mRequestingLocationUpdates) {
            LocationRequest mLocationRequest = createLocationRequest();
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                //Force user to set settings
                //Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                //context.startActivity(intent);

                return;
            }
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);
            //initializePolyline();
        }
    }

    @Override
    public void onConnectionSuspended(int cause)
    {
        Log.d(TAG, "Connection to Google API suspended");
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "Location Detected");
        mLatLng = new LatLng(location.getLatitude(), location.getLongitude());

        // Broadcast information on PubNub Channel
        PubNubManager.broadcastLocation(mPubnub, channelName, location.getLatitude(),
                location.getLongitude(), location.getAltitude());
    }

    private LocationRequest createLocationRequest() {
        Log.d(TAG, "Building request");
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return mLocationRequest;
    }

    public void startSharingLocation() {
        Log.d(TAG, "Starting Location Updates");
        mGoogleApiClient.connect();
    }

    public void stopSharingLocation() {
        Log.d(TAG, "Stop Location Updates & Disconect to Google API");
        stopLocationUpdates();
        mGoogleApiClient.disconnect();
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }

    @Override
    public void update()
    {
        if(!userPreferences.isSendBroadcast())
        {
            stopSharingLocation();
        }
    }
}
