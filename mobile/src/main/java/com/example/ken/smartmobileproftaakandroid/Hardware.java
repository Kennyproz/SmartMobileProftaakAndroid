package com.example.ken.smartmobileproftaakandroid;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

/**
 * Created by Ken on 30-11-2017.
 */

public class Hardware {
    Vibrator vibrator;
    MediaPlayer mediaPlayer;
    Context context;


    public Hardware (Context con, MediaPlayer mp){
        this.mediaPlayer = mp;
        this.vibrator = (Vibrator)con.getSystemService(Context.VIBRATOR_SERVICE);
        this.context = con;
    }

    public void vibrate(int seconds){
        if(Build.VERSION.SDK_INT >= 26){
            vibrator.vibrate(VibrationEffect.createOneShot(seconds*100,10));
        } else {
            vibrator.vibrate(seconds*100);
        }
    }

    public void startMedia(){
        mediaPlayer.start();
    }


}
