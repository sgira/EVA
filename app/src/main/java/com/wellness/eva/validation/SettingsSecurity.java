package com.wellness.eva.validation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wellness.eva.R;

/**
 * Created by SinJa on 7/24/16.
 */

public class SettingsSecurity extends Activity
{
    private Button closeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings_security_layout);

        //Get instance of close button
        closeButton = (Button)findViewById(R.id.btnCloseSecurity);
        closeButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
