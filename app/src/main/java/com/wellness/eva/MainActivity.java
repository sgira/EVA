package com.wellness.eva;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
import com.wellness.eva.validation.UserPreferences;


public class MainActivity extends Activity
{
    private final String channelName =  "EVA_Broadcast";
    private MedicalEmergency medicalEmergency;
    private ImageButton redCrossImageButton;
    private ImageButton sosImageButton;
    private ImageButton settingsImageButton;
    boolean call911Flag;
    boolean broadcastFlag;
    boolean receiveBroadcastFlag;
    boolean EnglishFlag;
    boolean SpanishFlag;

    public static final String PREFS_NAME = "MyPrefsFile";



    UserPreferences mypreferences = new UserPreferences();



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

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        boolean broadcasting = settings.getBoolean("broadcastingMode", true);
        mypreferences.setSendBroadcast(broadcasting);

        boolean English = settings.getBoolean("EnglishMode", true);
        mypreferences.setEnglish(English);

        boolean Spanish = settings.getBoolean("SpanishMode", false);
        mypreferences.setSpanish(Spanish);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //UserPreferences mypreferences = new UserPreferences();
        LanguageHelper myLanguage = new LanguageHelper();




        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(getApplicationContext(),"Displaying Settings",Toast.LENGTH_SHORT).show();
        }

        if (id == R.id.menu_autoCall911) {
            Toast.makeText(getApplicationContext(),"Enable Call 911",Toast.LENGTH_SHORT).show();
            call911Flag = true;
            mypreferences.setAutoCall911(call911Flag);
            dialContactPhone("911");
        }

        if (id == R.id.menu_allowBroadcast) {
            Toast.makeText(getApplicationContext(),"Enable Broadcasting",Toast.LENGTH_SHORT).show();
            broadcastFlag =true;
            mypreferences.setSendBroadcast(broadcastFlag);
        }

        if (id == R.id.menu_disableBroadcast) {
            Toast.makeText(getApplicationContext(), "Disable Broadcasting", Toast.LENGTH_SHORT).show();
            broadcastFlag = false;
            mypreferences.setSendBroadcast(broadcastFlag);
        }

        if (id == R.id.menu_receiveBroadcast) {
            Toast.makeText(getApplicationContext(),"Enable Receiving Broadcast",Toast.LENGTH_SHORT).show();
            receiveBroadcastFlag = true;
            mypreferences.setReceiveBroadcast(receiveBroadcastFlag);
        }

        if (id == R.id.menu_disable_receiveBroadcast) {
            Toast.makeText(getApplicationContext(),"Disable Receiving Broadcast",Toast.LENGTH_SHORT).show();
            receiveBroadcastFlag = false;
            mypreferences.setReceiveBroadcast(receiveBroadcastFlag);
        }

        if (id == R.id.menu_setEnglish) {
            Toast.makeText(getApplicationContext(),"Set English",Toast.LENGTH_SHORT).show();
            EnglishFlag = true;
            SpanishFlag = false;
            myLanguage.changeLocale(this.getResources(), "en");
            mypreferences.setEnglish(EnglishFlag);
            mypreferences.setSpanish(SpanishFlag);
        }

        if (id == R.id.menu_setSpanish) {
            Toast.makeText(getApplicationContext(),"Set Spanish",Toast.LENGTH_SHORT).show();
            SpanishFlag = true;
            EnglishFlag = false;
            myLanguage.changeLocale(this.getResources(), "es");
            mypreferences.setSpanish(SpanishFlag);
            mypreferences.setEnglish(EnglishFlag);
        }

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("broadcastingMode", mypreferences.isReceiveBroadcast());
        editor.putBoolean("EnglishMode", mypreferences.isEnglish());
        editor.putBoolean("SpanishMode",mypreferences.isSpanish());

        // Commit the edits!
        editor.commit();


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

    public void dialContactPhone(final String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" +  phoneNumber));
        startActivity(intent);
    }

    private void callActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}





