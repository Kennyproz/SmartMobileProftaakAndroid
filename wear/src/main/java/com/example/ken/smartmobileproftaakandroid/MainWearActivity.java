package com.example.ken.smartmobileproftaakandroid;

import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.Switch;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class MainWearActivity extends WearableActivity {
    private TimerTask mT1;
    private Timer mTimer1;
    private Handler mTimerHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_wear);

        // Enables Always-on
        setAmbientEnabled();

        Initialize();

    }

    public void Initialize(){

    }

    public void DoVibrate(){
        final Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        mTimer1 = new Timer();
        mT1 = new TimerTask(){
            public void run(){
                mTimerHandler.post(new Runnable(){
                    public void run(){
                        vibrator.vibrate(100);
                    }
                });
            }
        };
        mTimer1.schedule(mT1, 1, 500);
    }

    public void DoNotVibrate(){
        if(mTimer1 != null){
            mTimer1.cancel();
            mTimer1.purge();
        }
    }

    public void vibrateSwitch(View view){

        Switch vibrateSwitch = findViewById(R.id.switch1);

        if(vibrateSwitch.isChecked()){
            DoVibrate();
        }
        else{
            DoNotVibrate();
        }

    }

    //https://developer.android.com/training/wearables/wearable-sounds.html
    //Find if there's an audio device is available
}
