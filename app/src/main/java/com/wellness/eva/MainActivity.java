package com.wellness.eva;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.wellness.eva.feedback.CPRFeedback;
import com.wellness.eva.feedback.ProcedureFeedback;
import com.wellness.eva.messaging.GMapsFollowLocationActivity;
import com.wellness.eva.messaging.GMapsShareLocationActivity;
import com.wellness.eva.messaging.MBoxFollowLocationActivity;
import com.wellness.eva.messaging.MBoxShareLocationActivity;
import com.wellness.eva.procedures.MedicalEmergency;
import com.wellness.eva.procedures.MedicalProcedure;


public class MainActivity extends Activity
{
    private MedicalEmergency medicalEmergency;
    private boolean useMapBox;
    private EditText channelEditText;
    private Switch mSwitch;
    private String channelName;
    private static final String TAG = "Tracker - MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSwitch = (Switch) findViewById(R.id.switchMaps);

        // Attach a listener to check for changes in state
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    useMapBox = true;
                } else {
                    useMapBox = false;
                }

            }
        });

        channelEditText = (EditText) findViewById(R.id.channelEditText);
        channelEditText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN)) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_ENTER:
                            channelName = channelEditText.getText().toString();
                            String message = "Chosen channel: " + channelName;
                            Toast.makeText(MainActivity.this, message,
                                    Toast.LENGTH_SHORT).show();
                            Log.d(TAG, message);
                            return true;
                        default:
                            break;
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void ShowEmergencyProcedure(String emergencyName)
    {
        MedicalProcedure medicalProcedure = FileRetrieval.retrieveMedicalEmergency(emergencyName);
        medicalEmergency = new MedicalEmergency(emergencyName,medicalProcedure);

        // Retrieve procedures for medical emergency

        // Evaluate procedure feedback if applicable
        ProcedureFeedback procedureFeedback = new CPRFeedback();
        procedureFeedback.getFeedback();
    }

    public void shareLocation(View view) {
        if (useMapBox) {
            Log.d(TAG, "Share Location With MapBox Chosen on channel: "
                    + channelName);
            callActivity(MBoxShareLocationActivity.class);
        } else {
            Log.d(TAG, "Share Location With Google Maps Chosen on channel: "
                    + channelName);
            callActivity(GMapsShareLocationActivity.class);
        }
        return;
    }

    public void followLocation(View view) {
        if (useMapBox) {
            Log.d(TAG, "Follow Location With MapBox Chosen on channel: "
                    + channelName);
            callActivity(MBoxFollowLocationActivity.class);
        } else {
            Log.d(TAG, "Follow Location With Google Maps Chosen on channel: "
                    + channelName);
            callActivity(GMapsFollowLocationActivity.class);
        }
        return;
    }

    private void callActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        intent.putExtra("channel", channelName);
        startActivity(intent);
    }
}
