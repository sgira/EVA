package com.wellness.eva;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.wellness.eva.messaging.GMapsFollowLocationActivity;
import com.wellness.eva.procedures.MedicalEmergency;
import com.wellness.eva.validation.UserPreferences;

import java.util.Date;


public class MainActivity extends AppCompatActivity
{
    private final String channelName =  "EVA_Broadcast";
    private MedicalEmergency medicalEmergency;
    private ImageButton redCrossImageButton;
    private ImageButton sosImageButton;
    private Toolbar toolbar;
    private boolean call911Flag;
    private boolean broadcastFlag;
    private boolean receiveBroadcastFlag;
    private Button btnLocation;
    private boolean EnglishFlag;
    private boolean SpanishFlag;
    private Button btnEmergencyLocation;
    private Button btnBroadcasting;
    private ImageView imgAlert;
    private Animation pulse;
    private String alertDate = "";
    private UserPreferences mypreferences = new UserPreferences();
    private SharedPreferences sharedPref;
    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Attaching the layout to the toolbar object
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        //Initiate alert icon
        imgAlert = (ImageView)findViewById(R.id.imgAlert);

        // Setting toolbar as the ActionBar with setSupportActionBar() call
        setSupportActionBar(toolbar);

        // Setting redCrossImageButton OnClick listener
        redCrossImageButton = (ImageButton) findViewById(R.id.imageButton);
        redCrossImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentVar = new Intent(MainActivity.this, Main3Activity.class);

                startActivity(intentVar);
            }
        });

        sosImageButton = (ImageButton) findViewById(R.id.imageButton2);
        sosImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialContactPhone("3055425728");
            }
        });


        //Setting emergency location button OnClick listener
        btnEmergencyLocation = (Button)findViewById(R.id.btnEmergencyLocation);
        btnEmergencyLocation.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                    //Follow location
                    callActivity(GMapsFollowLocationActivity.class);
            }
        });

        //Setting emergency location button OnClick listener
        btnBroadcasting = (Button)findViewById(R.id.btnEmergencyLocation);
        btnBroadcasting.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Follow location
                callActivity(GMapsFollowLocationActivity.class);
            }
        });

        //Acquire last changed setting date
        GetLastSettingDate();

        pulse = AnimationUtils.loadAnimation(this, R.anim.pulse);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        broadcastFlag = settings.getBoolean("broadcastingMode", true);
        mypreferences.setSendBroadcast(broadcastFlag);
        sharedPref = getSharedPreferences(getString(R.string.preference_internal_file_key), Context.MODE_PRIVATE);

        if(!broadcastFlag)
        {
            Toast.makeText(getApplicationContext(), "Attention: Broadcast Location is Disabled. Change Settings to Broadcast Location", Toast.LENGTH_LONG).show();
        }

        receiveBroadcastFlag = settings.getBoolean("receiveBroadcastingMode", true);
        mypreferences.setReceiveBroadcast(receiveBroadcastFlag);
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
            Toast.makeText(getApplicationContext(),"Enabled Call 911",Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getApplicationContext(),"Disabled Receiving Broadcast",Toast.LENGTH_SHORT).show();
            receiveBroadcastFlag = false;
            mypreferences.setReceiveBroadcast(receiveBroadcastFlag);

            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("receiveBroadcastingMode",receiveBroadcastFlag).commit();
        }

        //Inform user of change setting
        AlertSettingChanged();
        return super.onOptionsItemSelected(item);
    }

    private void AlertSettingChanged()
    {
        Date d = new Date();
        alertDate = DateFormat.format("EEEE, MMMM d, yyyy ", d.getTime()).toString();
        SaveLastSettingDate();

        imgAlert.setVisibility(View.VISIBLE);
        imgAlert.startAnimation(pulse);
    }

    private void SaveLastSettingDate()
    {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("settings_date", alertDate);
        editor.commit();
    }

    private void GetLastSettingDate()
    {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        alertDate = sharedPref.getString("settings_date", "");
    }

//    private void ShowEmergencyProcedure(String emergencyName)
//    {
//        MedicalProcedure medicalProcedure = FileRetrieval.retrieveMedicalEmergency(emergencyName);
//        medicalEmergency = new MedicalEmergency(emergencyName,medicalProcedure);
//
//        // Retrieve procedures for medical emergency
//
//        // Evaluate procedure feedback if applicable
//        ProcedureFeedback procedureFeedback = new CPRFeedback();
//        procedureFeedback.getFeedback();
//    }

    public void dialContactPhone(final String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" +  phoneNumber));
        startActivity(intent);
    }

    private void callActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}





