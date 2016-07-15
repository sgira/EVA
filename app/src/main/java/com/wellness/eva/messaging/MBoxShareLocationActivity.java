package com.wellness.eva.messaging;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.pubnub.api.Pubnub;
import com.wellness.eva.R;

public class MBoxShareLocationActivity extends ActionBarActivity implements
        ConnectionCallbacks, LocationListener {

    // =========================================================================
    // Properties
    // =========================================================================

    private static final String TAG = "Tracker - MBox Share";
    private boolean mRequestingLocationUpdates = false;
    private boolean isFirstMessage = true;
    private MenuItem mShareButton;

    // Google API - Locations
    private GoogleApiClient mGoogleApiClient;

    // MapBox
    private MapView mMapView;
    // PathOverlay mLine;
    private LatLng mLatLng;
    private Marker mMarker;

    // PubNub
    private Pubnub mPubnub;
    private String channelName;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    // =========================================================================
    // Activity Life Cycle
    // =========================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mbox_view);

        // Get MapView
        mMapView = (MapView) findViewById(R.id.mapview);

        // Get Channel Name
        Intent intent = getIntent();
        channelName = intent.getExtras().getString("channel");
        Log.d(TAG, "Passed Channel Name: " + channelName);

        // Start Google Client
        this.buildGoogleApiClient();

        // Start PubNub
        mPubnub = PubNubManager.startPubnub();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this).addApi(LocationServices.API)
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share, menu);
        mShareButton = menu.findItem(R.id.share_locations);
        return true;
    }

    // =========================================================================
    // Google Location API CallBacks
    // =========================================================================

    @Override
    public void onConnected(Bundle connectionHint) {
        Log.d(TAG, "Connected to Google API for Location Management");
        if (mRequestingLocationUpdates) {
            LocationRequest mLocationRequest = createLocationRequest();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);
            initializePolyline();
        }
    }

    @Override
    public void onConnectionSuspended(int cause) {
        Log.d(TAG, "Connection to Google API suspended");
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "Location Detected");
        mLatLng = new LatLng(location.getLatitude(), location.getLongitude());

        // Broadcast information on PubNub Channel
        PubNubManager.broadcastLocation(mPubnub, channelName, location.getLatitude(),
                location.getLongitude(), location.getAltitude());

        // Update Map
        updateCamera();
        updatePolyline();
        updateMarker();
    }

    private LocationRequest createLocationRequest() {
        Log.d(TAG, "Building request");
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return mLocationRequest;
    }

    // =========================================================================
    // Button CallBacks
    // =========================================================================

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.share_locations:
                Log.d(TAG, "'Share Your Location' Button Pressed");
                mRequestingLocationUpdates = !mRequestingLocationUpdates;
                if (mRequestingLocationUpdates) {
                    startSharingLocation();
                    mShareButton.setTitle("Stop Sharing Your Location");
                }
                if (!mRequestingLocationUpdates) {
                    stopSharingLocation();
                    mShareButton.setTitle("Start Sharing Your Location");
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void startSharingLocation() {
        Log.d(TAG, "Starting Location Updates");
        mGoogleApiClient.connect();
    }

    public void stopSharingLocation() {
        Log.d(TAG, "Stop Location Updates & Disconect to Google API");
        isFirstMessage = true;
        stopLocationUpdates();
        mGoogleApiClient.disconnect();
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }

    // =========================================================================
    // Map Editing Methods
    // =========================================================================

    private void initializePolyline() {
//        mMapView.removeOverlay(mLine);
//        mMapView.clear();
//        mLine = new PathOverlay(Color.BLUE, 5);
    }

    private void updatePolyline() {
//        mMapView.removeOverlay(mLine);
//        mLine.addPoint(mLatLng);
//        mMapView.getOverlays().add(mLine);
    }

    private void updateCamera() {
        mMapView.setCameraDistance(10);
//        mMapView.setCenter(mLatLng);
    }

    private void updateMarker() {
//        if (!isFirstMessage) {
//            mMapView.removeMarker(mMarker);
//        }
        isFirstMessage = false;
//        mMarker = new Marker(mMapView, "", "", mLatLng);
//        mMapView.addMarker(mMarker);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "MBoxShareLocation Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.wellness.eva.messaging/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "MBoxShareLocation Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.wellness.eva.messaging/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}