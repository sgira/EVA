package com.wellness.eva;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.wellness.eva.feedback.CPRFeedback;
import com.wellness.eva.feedback.ProcedureFeedback;
import com.wellness.eva.procedures.MedicalEmergency;
import com.wellness.eva.procedures.MedicalProcedure;


public class MainActivity extends Activity
{
    private MedicalEmergency medicalEmergency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
