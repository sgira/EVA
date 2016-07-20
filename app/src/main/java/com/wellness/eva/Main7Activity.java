package com.wellness.eva;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.wellness.eva.procedures.MedicalProcedure;

public class Main7Activity extends Activity {

    private String emergencyType = "drowning";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);

        FileRetrieval file = new FileRetrieval();
        MedicalProcedure chokingProcedure = new MedicalProcedure();

        chokingProcedure = file.retrieveMedicalEmergency(emergencyType);


        TextView myTextView = new TextView(this);

        myTextView = (TextView) findViewById(R.id.textView13);

        for(int i = 0; i < chokingProcedure.getInstructions().size(); i++) {

            myTextView.append(chokingProcedure.getInstructions().get(i));
        }
    }
}
