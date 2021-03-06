package com.wellness.eva.validation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wellness.eva.MainActivity;
import com.wellness.eva.R;

/**
 * Created by SinJa on 7/24/16.
 */

public class SettingsSecurity extends Activity
{
    private Button closeButton;
    private Button ignoreButton;
    private TextView txtSecurityMsg;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings_security_layout);

        //Get information passed from main
        Intent extras = getIntent();

        String msg = "A setting was changed in EVA!";

        if (extras != null && extras.getStringExtra("date") != null)
        {
            msg = "Last time a setting was changed: " + extras.getStringExtra("date");
        }

        txtSecurityMsg = (TextView)findViewById(R.id.txtSecurityMsg);
        txtSecurityMsg.setText(msg);

        //Get instance of close button
        closeButton = (Button)findViewById(R.id.btnCloseSecurity);
        closeButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendNotification(false);
            }
        });

        ignoreButton = (Button)findViewById(R.id.btnIgnore);
        ignoreButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendNotification(true);
            }
        });

    }

    private void SendNotification(boolean showAlert)
    {
        Intent intent=new Intent();
        intent.putExtra("MESSAGE", showAlert);
        setResult(MainActivity.SECURITY_CODE, intent);
        finish();
    }
}
