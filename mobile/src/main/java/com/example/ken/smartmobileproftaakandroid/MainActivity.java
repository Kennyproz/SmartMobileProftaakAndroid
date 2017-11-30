package com.example.ken.smartmobileproftaakandroid;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final MediaPlayer mp = MediaPlayer.create(this,R.raw.alarm);
        // Hardware hardware = new Hardware(this,mp);
        // hardware.startMedia();
    }

}
