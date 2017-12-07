package com.example.ken.smartmobileproftaakandroid;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Hardware hardware;
    Switch mediaSwitch;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }
    @Override
    public void onPause() {
        super.onPause();
     //   hardware.stopMedia();

    }

    public void initialize(){
        MediaPlayer mp = MediaPlayer.create(this,R.raw.alarm);
        hardware = new Hardware(this,mp);
        mediaSwitch = (Switch)findViewById(R.id.swMediaPlayer);
        tv = (TextView)findViewById(R.id.tvBluetooth);
        tv.setText(hardware.bluetoothState());
    }

    public void playMediaPlayer(View view){
       if (mediaSwitch.isChecked())
       {
           alarm(true);
       }
       else {
           alarm(false);
       }
    }

    public boolean alarm (Boolean bool){
        tv.setText(hardware.bluetoothState());
        if (bool){
            hardware.startMedia();
            hardware.startVibrate();
            Toast.makeText(this, "Alarm started", Toast.LENGTH_SHORT).show();
         return true;
        }
        else {
            hardware.pauseMedia();
            hardware.stopVibrate();
            Toast.makeText(this, "Alarm paused", Toast.LENGTH_SHORT).show();
            return false;
        }
    }



}
