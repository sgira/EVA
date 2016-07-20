package com.wellness.eva;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.wellness.eva.procedures.MedicalProcedure;

public class Main4Activity extends Activity {

    private String emergencyType = "heartAttack";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        // Get Channel Name
        Intent intent = getIntent();
        emergencyType = intent.getExtras().getString("emergency_situation");

        FileRetrieval file = new FileRetrieval();
        MedicalProcedure heartAttackProcedure = new MedicalProcedure();

        heartAttackProcedure = file.retrieveMedicalEmergency(emergencyType);


        TextView myTextView = new TextView(this);

        myTextView = (TextView) findViewById(R.id.textView8);

        for(int i = 0; i < heartAttackProcedure.getInstructions().size(); i++) {

            myTextView.append(heartAttackProcedure.getInstructions().get(i));
        }
    }
}
