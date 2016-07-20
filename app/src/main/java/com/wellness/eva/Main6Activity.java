package com.wellness.eva;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TextView;

import com.wellness.eva.procedures.MedicalProcedure;

public class Main6Activity extends Activity {

    private String emergencyType = "burning";
    private TabHost tabhost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        TabHost host = (TabHost)findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("First degree burns");
        spec.setContent(R.id.tab1);
        spec.setIndicator("First degree burns");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Second degree burns");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Second degree burns");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("Third degree burns");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Third degree burns");
        host.addTab(spec);


        FileRetrieval file = new FileRetrieval();
        MedicalProcedure burningProcedure = new MedicalProcedure();

        burningProcedure = file.retrieveMedicalEmergency(emergencyType);


        TextView myTextView = new TextView(this);

        myTextView = (TextView) findViewById(R.id.textView10);

        for(int i = 0; i < burningProcedure.getInstructions().size() - 15; i++) {

            myTextView.append(burningProcedure.getInstructions().get(i));
        }


        TextView myTextView2 = new TextView(this);

        myTextView2 = (TextView) findViewById(R.id.textView11);

        for(int i = 4; i < burningProcedure.getInstructions().size() - 5; i++) {

            myTextView2.append(burningProcedure.getInstructions().get(i));
        }

        TextView myTextView3 = new TextView(this);

        myTextView3 = (TextView) findViewById(R.id.textView12);

        for(int i = 14; i < burningProcedure.getInstructions().size(); i++) {

            myTextView3.append(burningProcedure.getInstructions().get(i));
        }
    }
}