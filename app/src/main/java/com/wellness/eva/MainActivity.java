package com.wellness.eva;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.wellness.eva.feedback.CPRFeedback;
import com.wellness.eva.feedback.ProcedureFeedback;
import com.wellness.eva.messaging.GMapsFollowLocationActivity;
import com.wellness.eva.messaging.GMapsShareLocationActivity;
import com.wellness.eva.procedures.MedicalEmergency;
import com.wellness.eva.procedures.MedicalProcedure;


public class MainActivity extends Activity
{
    private final String channelName =  "EVA_Broadcast";
    private MedicalEmergency medicalEmergency;
    private ImageButton redCrossImageButton;
    private ImageButton sosImageButton;
    private ImageButton settingsImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setting redCrossImageButton OnClick listener
        redCrossImageButton = (ImageButton) findViewById(R.id.imageButton);

        redCrossImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentVar = new Intent(MainActivity.this, Main3Activity.class);

                startActivity(intentVar);
                //Toast.makeText(MainActivity.this, "it works", Toast.LENGTH_LONG).show();
            }
        });

        // Setting settingsImageButton OnClick listener
        settingsImageButton = (ImageButton) findViewById(R.id.imageButton8);

        //Check broadcasting setting
        if(true)//change to broadcasting setting
        {
            //Share current location
            GMapsShareLocationActivity shareLocationActivity = new GMapsShareLocationActivity(this);
            shareLocationActivity.startSharingLocation();

            //Follow location
            callActivity(GMapsFollowLocationActivity.class);
        }
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

    private void callActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}





