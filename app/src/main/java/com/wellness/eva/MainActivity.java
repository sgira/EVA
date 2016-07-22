package com.wellness.eva;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.wellness.eva.feedback.CPRFeedback;
import com.wellness.eva.feedback.ProcedureFeedback;
import com.wellness.eva.messaging.GMapsFollowLocationActivity;
import com.wellness.eva.messaging.GMapsShareLocationActivity;
import com.wellness.eva.procedures.MedicalEmergency;
import com.wellness.eva.procedures.MedicalProcedure;
import com.wellness.eva.validation.UserPreferences;


public class MainActivity extends AppCompatActivity
{
    private final String channelName =  "EVA_Broadcast";
    private MedicalEmergency medicalEmergency;
    private ImageButton redCrossImageButton;
    private ImageButton sosImageButton;
    private Toolbar toolbar; // Declaring the Toolbar Object
    private boolean call911Flag;
    private boolean broadcastFlag;
    private boolean receiveBroadcastFlag;
    private boolean EnglishFlag;
    private boolean SpanishFlag;
    private Button btnLocation;
    private UserPreferences mypreferences = new UserPreferences();

    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Attaching the layout to the toolbar object
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        // Setting toolbar as the ActionBar with setSupportActionBar() call
        setSupportActionBar(toolbar);

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

        sosImageButton = (ImageButton) findViewById(R.id.imageButton2);
        sosImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialContactPhone("911");
            }
        });


        //Setting location button OnClick listener
        btnLocation = (Button)findViewById(R.id.btnLocation);
        btnLocation.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Follow location
                callActivity(GMapsFollowLocationActivity.class);
            }
        });

        //Checking broadcasting setting
        if(true)//change to broadcasting setting
        {
            //Share current location
            GMapsShareLocationActivity shareLocationActivity = new GMapsShareLocationActivity(this);
            shareLocationActivity.startSharingLocation();
        }

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        broadcastFlag = settings.getBoolean("broadcastingMode", true);
        mypreferences.setSendBroadcast(broadcastFlag);

        for(int i = 0; i < 2; i++)
            if(!broadcastFlag){
                Toast.makeText(getApplicationContext(), "Attention: Broadcast Location is Disable    Change Settings to Broadcast Location", Toast.LENGTH_LONG).show();
            }

        receiveBroadcastFlag = settings.getBoolean("receiveBroadcastingMode", true);
        mypreferences.setReceiveBroadcast(receiveBroadcastFlag);

        EnglishFlag = settings.getBoolean("EnglishMode", true);
        mypreferences.setEnglish(EnglishFlag);

        SpanishFlag = settings.getBoolean("SpanishMode", false);
        mypreferences.setSpanish(SpanishFlag);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        LanguageHelper myLanguage = new LanguageHelper();
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

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

            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("broadcastingMode",broadcastFlag).commit();
        }

        if (id == R.id.menu_disableBroadcast) {
            Toast.makeText(getApplicationContext(), "Disable Broadcasting", Toast.LENGTH_SHORT).show();
            broadcastFlag = false;
            mypreferences.setSendBroadcast(broadcastFlag);

            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("broadcastingMode",broadcastFlag).commit();
        }

        if (id == R.id.menu_receiveBroadcast) {
            Toast.makeText(getApplicationContext(),"Enable Receiving Broadcast",Toast.LENGTH_SHORT).show();
            receiveBroadcastFlag = true;
            mypreferences.setReceiveBroadcast(receiveBroadcastFlag);

            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("receiveBroadcastingMode",receiveBroadcastFlag).commit();
        }

        if (id == R.id.menu_disable_receiveBroadcast) {
            Toast.makeText(getApplicationContext(),"Disable Receiving Broadcast",Toast.LENGTH_SHORT).show();
            receiveBroadcastFlag = false;
            mypreferences.setReceiveBroadcast(receiveBroadcastFlag);

            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("receiveBroadcastingMode",receiveBroadcastFlag).commit();
        }

        if (id == R.id.menu_setEnglish) {
            Toast.makeText(getApplicationContext(),"Set English",Toast.LENGTH_SHORT).show();
            EnglishFlag = true;
            SpanishFlag = false;
            myLanguage.changeLocale(this.getResources(), "en");
            mypreferences.setEnglish(EnglishFlag);
            mypreferences.setSpanish(SpanishFlag);

            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("receiveBroadcastingMode",EnglishFlag).commit();
            editor.putBoolean("receiveBroadcastingMode",SpanishFlag).commit();
        }

        if (id == R.id.menu_setSpanish) {
            Toast.makeText(getApplicationContext(),"Set Spanish",Toast.LENGTH_SHORT).show();
            SpanishFlag = true;
            EnglishFlag = false;
            myLanguage.changeLocale(this.getResources(), "es");
            mypreferences.setSpanish(SpanishFlag);
            mypreferences.setEnglish(EnglishFlag);

            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("receiveBroadcastingMode",SpanishFlag).commit();
            editor.putBoolean("receiveBroadcastingMode",EnglishFlag).commit();
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

    public void dialContactPhone(final String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" +  phoneNumber));
        startActivity(intent);
    }

    private void callActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}





