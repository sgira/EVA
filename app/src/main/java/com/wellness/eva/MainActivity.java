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

import com.wellness.eva.messaging.BroadcastEmergency;
import com.wellness.eva.messaging.BroadcastReceiver;
import com.wellness.eva.procedures.MedicalEmergency;
import com.wellness.eva.procedures.MedicalProcedures;
import com.wellness.eva.validation.SettingsSecurity;
import com.wellness.eva.validation.UserPreferences;

import java.util.Date;


public class MainActivity extends AppCompatActivity
{
    public static final int SECURITY_CODE = 1;
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
    private boolean securityShow;
    private UserPreferences mypreferences = new UserPreferences();
    private SharedPreferences internalPref;
    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        InitiateControls();
        InitiateLogic();

    }

    private void InitiateControls()
    {
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

                Intent intentVar = new Intent(MainActivity.this, MedicalProcedures.class);

                startActivity(intentVar);
            }
        });

        sosImageButton = (ImageButton) findViewById(R.id.imageButton2);
        sosImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialContactPhone("123");
            }
        });

        //Setting emergency location button OnClick listener
        btnEmergencyLocation = (Button)findViewById(R.id.btnEmergencyLocation);
        btnEmergencyLocation.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Follow location
                callActivity(BroadcastReceiver.class);
            }
        });

        //Setting emergency broadcasting button
        btnBroadcasting = (Button)findViewById(R.id.btnBroadcasting);

        pulse = AnimationUtils.loadAnimation(this, R.anim.pulse);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        broadcastFlag = settings.getBoolean("broadcastingMode", true);
        mypreferences.setSendBroadcast(broadcastFlag);
        internalPref = getSharedPreferences(getString(R.string.preference_internal_file_key), Context.MODE_PRIVATE);

        setBroadcastingState(broadcastFlag);

        receiveBroadcastFlag = settings.getBoolean("receiveBroadcastingMode", true);
        mypreferences.setReceiveBroadcast(receiveBroadcastFlag);

        imgAlert.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, SettingsSecurity.class);
                intent.putExtra("key",alertDate);
                startActivityForResult(intent, SECURITY_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SECURITY_CODE)
        {
            boolean ignoreSecurity = data.getBooleanExtra("MESSAGE", false);
            AlertSettingChanged(!ignoreSecurity);
        }
    }

    private void InitiateLogic()
    {
        //Acquire last changed setting date
        GetLastSettingDate();
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

        if (id == R.id.menu_allowBroadcast) {
            Toast.makeText(getApplicationContext(),"Enable Broadcasting",Toast.LENGTH_SHORT).show();
            broadcastFlag =true;
            mypreferences.setSendBroadcast(broadcastFlag);

            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("broadcastingMode",broadcastFlag).commit();

            setBroadcastingState(true);
        }

        if (id == R.id.menu_disableBroadcast) {
            Toast.makeText(getApplicationContext(), "Disable Broadcasting", Toast.LENGTH_SHORT).show();
            broadcastFlag = false;
            mypreferences.setSendBroadcast(broadcastFlag);

            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("broadcastingMode",broadcastFlag).commit();

            setBroadcastingState(false);
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
        AlertSettingChanged(true);
        return super.onOptionsItemSelected(item);
    }

    private void setBroadcastingState(boolean show)
    {
        if(show)
        {
            //Share current location
            BroadcastEmergency shareLocationActivity = new BroadcastEmergency(this, mypreferences);
            shareLocationActivity.startSharingLocation();

            Toast.makeText(getApplicationContext(), "Attention: Broadcast Location is Enable. Your location is been shared!", Toast.LENGTH_LONG).show();

            btnBroadcasting.setBackground(getDrawable(R.drawable.ic_signal_wifi_4_bar_white_18dp));
            btnBroadcasting.setVisibility(View.VISIBLE);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Attention: Broadcast Location is Disabled. Change Settings to Broadcast Location", Toast.LENGTH_LONG).show();

            btnBroadcasting.setBackground(getDrawable(R.drawable.ic_signal_wifi_off_white_18dp));
            btnBroadcasting.setVisibility(View.INVISIBLE);
        }
    }

    private void AlertSettingChanged(boolean show)
    {
        Date d = new Date();
        alertDate = DateFormat.format("EEEE, MMMM d, yyyy ", d.getTime()).toString();
        SaveLastSettingDate(show);

        ShowAlert(show);
    }

    private void SaveLastSettingDate(boolean show)
    {
        SharedPreferences.Editor editor = internalPref.edit();
        editor.putBoolean("settings_show", show);
        editor.putString("settings_date", alertDate);
        editor.commit();
    }

    private void GetLastSettingDate()
    {
        alertDate = internalPref.getString("settings_date", "");
        securityShow = internalPref.getBoolean("settings_show", false);

        ShowAlert(securityShow);
    }

    private void ShowAlert(boolean securityShow)
    {
        if(securityShow)
        {
            imgAlert.startAnimation(pulse);
            imgAlert.setVisibility(View.VISIBLE);
            imgAlert.setAnimation(pulse);
        }
        else
        {
            imgAlert.clearAnimation();
            imgAlert.setVisibility(View.INVISIBLE);
            imgAlert.invalidate();
            imgAlert.requestLayout();
        }
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





