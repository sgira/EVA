package com.wellness.eva;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.wellness.eva.procedures.MedicalProcedureView;

public class Main3Activity extends Activity {

    public ImageButton closeImageButton;
    public ImageButton heartAttackImageButton;
    public ImageButton chokingImageButton;
    public ImageButton burningImageButton;
    public ImageButton drowningImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        closeImageButton = (ImageButton) findViewById(R.id.imageButton7);

        closeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent closeIntent = new Intent(Main3Activity.this, MainActivity.class);
                startActivity(closeIntent);
            }
        });

        heartAttackImageButton = (ImageButton) findViewById(R.id.imageButton3);
        heartAttackImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent heartAttackIntent = new Intent(Main3Activity.this, Main4Activity.class);
                heartAttackIntent.putExtra("emergency_situation", "heart_attack");
                startActivity(heartAttackIntent);

            }
        });

        chokingImageButton = (ImageButton) findViewById(R.id.imageButton4);
        chokingImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent chokingIntent = new Intent(Main3Activity.this, Main4Activity.class);
                Bundle bundle = new Bundle();
                chokingIntent.putExtra("emergency_situation", "choking");
                startActivity(chokingIntent);


            }
        });

        burningImageButton = (ImageButton) findViewById(R.id.imageButton5);
        burningImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent burningIntent = new Intent(Main3Activity.this, Main4Activity.class);
                burningIntent.putExtra("emergency_situation", "burning");
                startActivity(burningIntent);



            }
        });

        drowningImageButton = (ImageButton) findViewById(R.id.imageButton6);
        drowningImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent drowningIntent = new Intent(Main3Activity.this, Main4Activity.class);
                drowningIntent.putExtra("emergency_situation", "drowning");
                startActivity(drowningIntent);
                startActivity(drowningIntent);

            }
        });
    }
}

