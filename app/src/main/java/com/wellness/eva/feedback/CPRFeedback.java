package com.wellness.eva.feedback;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.wellness.eva.MainActivity;
import com.wellness.eva.R;


/**
 * Compute and evaluate CPR
 */
public class CPRFeedback extends Activity implements SensorEventListener
{

    private float mLastX, mLastY, mLastZ;
    private boolean mInitialized;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private final float NOISE = (float) 2.0;
    private int compressionCount = 0;
    private boolean atRest = true;
    private static int filterFeedbackAttempts = 0;
    MediaPlayer metronomeBeep;
    GifView gifView;
    ViewFlipper mViewFlipper;
    private float initialX;
    Context mContext;
    ImageView handsOnChestView;
    ImageView chestCompressionView;
    TableRow compressTimer;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpr_feedback);

        mContext = this;

        //initialize compression timer row
        compressTimer = (TableRow) findViewById(R.id.compression_timer_row);


        //initialize view flipper
        mViewFlipper = (ViewFlipper) this.findViewById(R.id.cprinstruction_flipper);

        //initialize all view flipper images
        handsOnChestView = (ImageView) findViewById(R.id.cprImg1);
        handsOnChestView.setImageResource(R.drawable.hand_position);

        chestCompressionView = (ImageView) findViewById(R.id.cprImg2);
        chestCompressionView.setImageResource(R.drawable.knelling_posistion);

        //initialize button
        final Button button = (Button) findViewById(R.id.begin_cpr);

        //make necessary sensor initializations
        mInitialized = false;
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);

    }


    @Override
    public boolean onTouchEvent(MotionEvent touchevent) {
        switch (touchevent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initialX = touchevent.getX();
                break;
            case MotionEvent.ACTION_UP:
                float finalX = touchevent.getX();
                if (initialX > finalX) {
                    if (mViewFlipper.getDisplayedChild() == 2)
                        break;

                    //not using following animation in this project
                    //mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.in_from_left));
                    //mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.out_from_left));

                    mViewFlipper.showNext();
                } else {
                    if (mViewFlipper.getDisplayedChild() == 0)
                        break;

                    //not using following animation in this project
                    //mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.in_from_right));
                    //mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.out_from_right));

                    mViewFlipper.showPrevious();
                }
                break;
        }
        return false;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // This method will not be implemented for this iteration
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //TextView compressionDepth = (TextView)findViewById(R.id.compressionDepth);
        //TextView compCount = (TextView)findViewById(R.id.compressionCount);

        float z = event.values[2];

        if (!mInitialized) {
            mLastZ = z;
            //compressionDepth.setText("0.0");
            mInitialized = true;
        } else {
            float deltaZ = Math.abs(mLastZ - z);
            if (deltaZ < NOISE) deltaZ = (float)0.0;


            if (deltaZ > 0.0) atRest = false;

            mLastZ = z;
            //compressionDepth.setText(Float.toString(deltaZ));
            if(deltaZ == 0 && !atRest ) {
                filterFeedbackAttempts = 0;
                atRest = true;
                compressionCount += 1;
                //compCount.setText(Integer.toString(compressionCount));
            }
        }
    }

    public void startCpr(View view){
        mViewFlipper.setVisibility(View.INVISIBLE);
        compressTimer.setVisibility(View.VISIBLE);

        //define text view to display the countDownTimer
        final TextView countDownTimer = (TextView) findViewById(R.id.compressionTimer);

        //initialize gif image
        gifView = (GifView) findViewById(R.id.gif_view);
        gifView.setVisibility(View.VISIBLE);
        //initialize media player
        metronomeBeep = MediaPlayer.create(this, R.raw.cprmetronome);

        //initialize and start Count down timer
        new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                //define what to do on tick (a tick occurs once every second)
                if(!metronomeBeep.isPlaying())
                    metronomeBeep.start();
                countDownTimer.setText("0:" + Integer.toString(((int) (millisUntilFinished/1000))-1));

            }

            //define what actions to be taken on when count down timer is finished
            public void onFinish() {
                metronomeBeep.stop();
                onPause();
            }
        }.start();



    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);


        //need to change to the calling activity
        finish();
        //Intent intent = new Intent(this, MainActivity.class);
        //startActivity(intent);
    }
}
