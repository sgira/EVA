package com.wellness.eva.messaging;

/**
 * Created by sindyg on 7/15/2016.
 */

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.wellness.eva.R;

import org.json.JSONException;
import org.json.JSONObject;

public class BroadcastReceiver extends Activity implements
        OnMapReadyCallback {

    // =========================================================================
    // Properties
    // =========================================================================

    private static final String TAG = "Tracker - GMaps Follow";
    private static final String PUBNUB_TAG = "PUBNUB";
    private boolean isFirstMessage = true;
    private boolean mRequestingLocationUpdates = false;
    private Button closeButton;

    // Google Maps
    private GoogleMap mGoogleMap;
    //	private Polyline mPolyline;
    private PolylineOptions mPolylineOptions;
    private Marker mMarker;
    private MarkerOptions mMarkerOptions;
    private LatLng mLatLng;
    private boolean inBoundary = false;

    // PubNub
    private Pubnub mPubnub;
    private final String channelName =  "EVA_Broadcast";

    // =========================================================================
    // Activity Life Cycle
    // =========================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gmaps_view);

        //Get instance of close button
        closeButton = (Button)findViewById(R.id.btnCloseMap);
        closeButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Set up View: Map & Action Bar
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    // =========================================================================
    // Map CallBacks
    // =========================================================================

    @Override
    public void onMapReady(GoogleMap map)
    {
        mGoogleMap = map;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
            //Force user to set settings
            //Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            //startActivity(intent);

            return;
        }
        mGoogleMap.setMyLocationEnabled(true);
        Log.d(TAG, "Map Ready");
        startFollowingLocation();
    }

    // =========================================================================
    // Button CallBacks
    // =========================================================================


    private void startFollowingLocation() {
        initializePolyline();
        mPubnub = PubNubManager.startPubnub();
        PubNubManager.subscribe(mPubnub, channelName, subscribeCallback);
    }

    private void stopFollowingLocation() {
        mPubnub.unsubscribe(channelName);
        isFirstMessage = true;
    }

    // =========================================================================
    // Map Editing Methods
    // =========================================================================

    private void initializePolyline() {
        mGoogleMap.clear();
        mPolylineOptions = new PolylineOptions();
        mPolylineOptions.color(Color.BLUE).width(10);
        mGoogleMap.addPolyline(mPolylineOptions);

        mMarkerOptions = new MarkerOptions();
    }

    private void updatePolyline() {
        mPolylineOptions.add(mLatLng);
        mGoogleMap.clear();
        mGoogleMap.addPolyline(mPolylineOptions);
    }

    private void updateCamera() {
        mGoogleMap
                .animateCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 16));
    }

    private void updateMarker() {
//		if (!isFirstMessage) {
//			isFirstMessage = false;
//			mMarker.remove();
//		}
        mMarker = mGoogleMap.addMarker(mMarkerOptions.position(mLatLng));
    }

    // =========================================================================
    // PubNub Callback
    // =========================================================================
    Callback subscribeCallback = new Callback()
    {
        @Override
        public void successCallback(String channel, Object message)
        {
            Log.d(PUBNUB_TAG, "Message Received: " + message.toString());
            JSONObject jsonMessage = (JSONObject) message;
            try {
                double mLat = jsonMessage.getDouble("lat");
                double mLng = jsonMessage.getDouble("lng");
                mLatLng = new LatLng(mLat, mLng);
                //inBoundary = CompareLocation(mLat, mLng);
            } catch (JSONException e) {
                Log.e(TAG, e.toString());
            }

            runOnUiThread(new Runnable()
            {
                @Override
                public void run() {

                    //Receive broadcasting location of people within the same vicinity
                    //if(inBoundary) {
                        updatePolyline();
                        updateCamera();
                        updateMarker();
                    //}
                }
            });
        }
    };


    private boolean CompareLocation(double lat, double lng)
    {
        float[] results = new float[1];
        Location.distanceBetween((double)mGoogleMap.getMyLocation().getLatitude(),
                (double)mGoogleMap.getMyLocation().getLongitude(), lat, lng, results);
        float distanceInMeters = results[0];
        boolean isWithin2km = distanceInMeters < 2000;//~1.24274 miles

        return  isWithin2km;
    }
}