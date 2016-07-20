package com.wellness.eva;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.wellness.eva.procedures.MedicalProcedure;

public class Main5Activity extends Activity {

    private String emergencyType = "choking";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        FileRetrieval file = new FileRetrieval();
        MedicalProcedure chokingProcedure = new MedicalProcedure();

        chokingProcedure = file.retrieveMedicalEmergency(emergencyType);


        TextView myTextView = new TextView(this);

        myTextView = (TextView) findViewById(R.id.textView9);

        for(int i = 0; i < chokingProcedure.getInstructions().size(); i++) {

            myTextView.append(chokingProcedure.getInstructions().get(i));
        }
    }
}