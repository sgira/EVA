package com.wellness.eva;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.wellness.eva.feedback.CPRFeedback;
import com.wellness.eva.procedures.MedicalProcedure;

import java.util.ArrayList;

public class Main4Activity extends Activity implements OnClickListener {

    private String emergencyType;
    private ViewFlipper heartAttack_viewFlipper;
    private ViewFlipper choking_viewFlipper;
    private ViewFlipper drowning_viewFlipper;
    private ViewFlipper burning_tab1_viewFlipper;
    private ViewFlipper burning_tab2_viewFlipper;
    private ViewFlipper burning_tab3_viewFlipper;
    private TabHost host;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        heartAttack_viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        heartAttack_viewFlipper.setOnClickListener(this);

        choking_viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper2);
        choking_viewFlipper.setOnClickListener(this);

        drowning_viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper3);
        drowning_viewFlipper.setOnClickListener(this);

        burning_tab1_viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper4);
        burning_tab1_viewFlipper.setOnClickListener(this);

        burning_tab2_viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper5);
        burning_tab2_viewFlipper.setOnClickListener(this);

        burning_tab3_viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper6);
        burning_tab3_viewFlipper.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        emergencyType = bundle.getString("emergency_situation");

        FileRetrieval file = new FileRetrieval();
        MedicalProcedure procedure = new MedicalProcedure();
        procedure = file.retrieveMedicalEmergency(emergencyType);


        if (emergencyType.equals("burning")) {

            View b = findViewById(R.id.button);
            b.setVisibility(View.GONE);


            host = (TabHost) findViewById(R.id.tabHost2);
            host.setup();


            //Tab 1
            TabHost.TabSpec spec = host.newTabSpec("First  degree burns");
            spec.setContent(R.id.linearLayout);
            spec.setIndicator("First  degree burns");
            host.addTab(spec);

            //Tab 2
            spec = host.newTabSpec("Second degree burns");
            spec.setContent(R.id.linearLayout2);
            spec.setIndicator("Second degree burns");
            host.addTab(spec);

            //Tab 3
            spec = host.newTabSpec("Third degree burns");
            spec.setContent(R.id.linearLayout3);
            spec.setIndicator("Third degree burns");
            host.addTab(spec);


            TextView myTextView37 = new TextView(this);
            myTextView37 = (TextView) findViewById(R.id.textView37);
            TextView myTextView38 = new TextView(this);
            myTextView38 = (TextView) findViewById(R.id.textView38);
            TextView myTextView39 = new TextView(this);
            myTextView39 = (TextView) findViewById(R.id.textView39);
            TextView myTextView40 = new TextView(this);
            myTextView40 = (TextView) findViewById(R.id.textView40);

            TextView myTextView41 = new TextView(this);
            myTextView41 = (TextView) findViewById(R.id.textView41);
            TextView myTextView42 = new TextView(this);
            myTextView42 = (TextView) findViewById(R.id.textView42);
            TextView myTextView43 = new TextView(this);
            myTextView43 = (TextView) findViewById(R.id.textView43);
            TextView myTextView44 = new TextView(this);
            myTextView44 = (TextView) findViewById(R.id.textView44);
            TextView myTextView45 = new TextView(this);
            myTextView45 = (TextView) findViewById(R.id.textView45);
            TextView myTextView46 = new TextView(this);
            myTextView46 = (TextView) findViewById(R.id.textView46);
            TextView myTextView47 = new TextView(this);
            myTextView47 = (TextView) findViewById(R.id.textView47);
            TextView myTextView48 = new TextView(this);
            myTextView48 = (TextView) findViewById(R.id.textView48);
            TextView myTextView49 = new TextView(this);
            myTextView49 = (TextView) findViewById(R.id.textView49);
            TextView myTextView50 = new TextView(this);
            myTextView50 = (TextView) findViewById(R.id.textView50);

            TextView myTextView51 = new TextView(this);
            myTextView51 = (TextView) findViewById(R.id.textView51);
            TextView myTextView52 = new TextView(this);
            myTextView52 = (TextView) findViewById(R.id.textView52);
            TextView myTextView53 = new TextView(this);
            myTextView53 = (TextView) findViewById(R.id.textView53);
            TextView myTextView54 = new TextView(this);
            myTextView54 = (TextView) findViewById(R.id.textView54);
            TextView myTextView55 = new TextView(this);
            myTextView55 = (TextView) findViewById(R.id.textView55);


            ArrayList <TextView> myTextViews = new ArrayList<>();
            myTextViews.add(myTextView37);
            myTextViews.add(myTextView38);
            myTextViews.add(myTextView39);
            myTextViews.add(myTextView40);

            myTextViews.add(myTextView41);
            myTextViews.add(myTextView42);
            myTextViews.add(myTextView43);
            myTextViews.add(myTextView44);
            myTextViews.add(myTextView45);
            myTextViews.add(myTextView46);
            myTextViews.add(myTextView47);
            myTextViews.add(myTextView48);
            myTextViews.add(myTextView49);
            myTextViews.add(myTextView50);

            myTextViews.add(myTextView51);
            myTextViews.add(myTextView52);
            myTextViews.add(myTextView53);
            myTextViews.add(myTextView54);
            myTextViews.add(myTextView55);


            for (int i = 0; i < procedure.getInstructions().size() - 15; i++) {

                myTextViews.get(i).append(procedure.getInstructions().get(i));
            }


            for (int i = 4; i < procedure.getInstructions().size() - 5; i++) {

                myTextViews.get(i).append(procedure.getInstructions().get(i));
            }


            for (int i = 14; i < procedure.getInstructions().size(); i++) {

                myTextViews.get(i).append(procedure.getInstructions().get(i));
            }

        } else if(emergencyType.equals("heart_attack")) {


            TextView myTextView8 = new TextView(this);
            myTextView8 = (TextView) findViewById(R.id.textView8);
            TextView myTextView9 = new TextView(this);
            myTextView9 = (TextView) findViewById(R.id.textView9);
            TextView myTextView10 = new TextView(this);
            myTextView10 = (TextView) findViewById(R.id.textView10);
            TextView myTextView11 = new TextView(this);
            myTextView11 = (TextView) findViewById(R.id.textView11);
            TextView myTextView12 = new TextView(this);
            myTextView12 = (TextView) findViewById(R.id.textView12);
            TextView myTextView13 = new TextView(this);
            myTextView13 = (TextView) findViewById(R.id.textView13);

            ArrayList <TextView> myTextViews = new ArrayList<>();
            myTextViews.add(myTextView8);
            myTextViews.add(myTextView9);
            myTextViews.add(myTextView10);
            myTextViews.add(myTextView11);
            myTextViews.add(myTextView12);
            myTextViews.add(myTextView13);

            for(int i = 0; i < procedure.getInstructions().size(); i++) {

                myTextViews.get(i).append(procedure.getInstructions().get(i));
            }

        } else if(emergencyType.equals("choking")) {

            TextView myTextView14 = new TextView(this);
            myTextView14 = (TextView) findViewById(R.id.textView14);
            TextView myTextView15 = new TextView(this);
            myTextView15 = (TextView) findViewById(R.id.textView15);
            TextView myTextView16 = new TextView(this);
            myTextView16 = (TextView) findViewById(R.id.textView16);
            TextView myTextView17 = new TextView(this);
            myTextView17 = (TextView) findViewById(R.id.textView17);
            TextView myTextView18 = new TextView(this);
            myTextView18 = (TextView) findViewById(R.id.textView18);
            TextView myTextView19 = new TextView(this);
            myTextView19 = (TextView) findViewById(R.id.textView19);
            TextView myTextView20 = new TextView(this);
            myTextView20 = (TextView) findViewById(R.id.textView20);
            TextView myTextView21 = new TextView(this);
            myTextView21 = (TextView) findViewById(R.id.textView21);
            TextView myTextView22 = new TextView(this);
            myTextView22 = (TextView) findViewById(R.id.textView22);
            TextView myTextView23 = new TextView(this);
            myTextView23 = (TextView) findViewById(R.id.textView23);
            TextView myTextView24 = new TextView(this);
            myTextView24 = (TextView) findViewById(R.id.textView24);
            TextView myTextView25 = new TextView(this);
            myTextView25 = (TextView) findViewById(R.id.textView25);
            TextView myTextView26 = new TextView(this);
            myTextView26 = (TextView) findViewById(R.id.textView26);
            TextView myTextView27 = new TextView(this);
            myTextView27 = (TextView) findViewById(R.id.textView27);
            TextView myTextView28 = new TextView(this);
            myTextView28 = (TextView) findViewById(R.id.textView28);

            ArrayList <TextView> myTextViews = new ArrayList<>();
            myTextViews.add(myTextView14);
            myTextViews.add(myTextView15);
            myTextViews.add(myTextView16);
            myTextViews.add(myTextView17);
            myTextViews.add(myTextView18);
            myTextViews.add(myTextView19);
            myTextViews.add(myTextView20);
            myTextViews.add(myTextView21);
            myTextViews.add(myTextView22);
            myTextViews.add(myTextView23);
            myTextViews.add(myTextView24);
            myTextViews.add(myTextView25);
            myTextViews.add(myTextView26);
            myTextViews.add(myTextView27);
            myTextViews.add(myTextView28);

            for(int i = 0; i < procedure.getInstructions().size(); i++) {

                myTextViews.get(i).append(procedure.getInstructions().get(i));

            }

        } else if(emergencyType.equals("drowning")) {

            TextView myTextView29 = new TextView(this);
            myTextView29 = (TextView) findViewById(R.id.textView29);
            TextView myTextView30 = new TextView(this);
            myTextView30 = (TextView) findViewById(R.id.textView30);
            TextView myTextView31 = new TextView(this);
            myTextView31 = (TextView) findViewById(R.id.textView31);
            TextView myTextView32 = new TextView(this);
            myTextView32 = (TextView) findViewById(R.id.textView32);
            TextView myTextView33 = new TextView(this);
            myTextView33 = (TextView) findViewById(R.id.textView33);
            TextView myTextView34 = new TextView(this);
            myTextView34 = (TextView) findViewById(R.id.textView34);
            TextView myTextView35 = new TextView(this);
            myTextView35 = (TextView) findViewById(R.id.textView35);
            TextView myTextView36 = new TextView(this);
            myTextView36 = (TextView) findViewById(R.id.textView36);

            ArrayList <TextView> myTextViews = new ArrayList<>();
            myTextViews.add(myTextView29);
            myTextViews.add(myTextView30);
            myTextViews.add(myTextView31);
            myTextViews.add(myTextView32);
            myTextViews.add(myTextView33);
            myTextViews.add(myTextView34);
            myTextViews.add(myTextView35);
            myTextViews.add(myTextView36);

            for(int i = 0; i < procedure.getInstructions().size(); i++) {

                myTextViews.get(i).append(procedure.getInstructions().get(i));
            }

        }

    }

    //to initiate cpr
    public void beginCPR_procedures(View view){
        Intent intent = new Intent(this, CPRFeedback.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {

        heartAttack_viewFlipper.showNext();
        choking_viewFlipper.showNext();
        drowning_viewFlipper.showNext();

        if (emergencyType.equals("burning")) {

            ArrayList <ViewFlipper> v_array = new ArrayList<>();
            v_array.add(burning_tab1_viewFlipper);
            v_array.add(burning_tab2_viewFlipper);
            v_array.add(burning_tab3_viewFlipper);

            for (int i = 0; i < host.getTabWidget().getChildCount(); i++) {

                if (host.getTabWidget().getChildAt(i).isSelected()) {

                    v_array.get(i).showNext();
                }
            }

        }

    }

}