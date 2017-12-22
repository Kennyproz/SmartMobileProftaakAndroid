package com.example.ken.smartmobileproftaakandroid;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Hardware hardware;
    Switch mediaSwitch;
    TextView tvBluetooth, tvMessage,tvBackgroundColor;
    ListView lvConnectedDevices;

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
        tvMessage = (TextView)findViewById(R.id.tvSafetyMessage);
        tvBackgroundColor = (TextView)findViewById(R.id.tvBackgroundcolor);
        lvConnectedDevices = (ListView)findViewById(R.id.lvConnectedDevices);
        fillBluetoothList();
        tvBluetooth = (TextView)findViewById(R.id.tvBluetooth);
        tvBluetooth.setText(hardware.bluetoothState());
    }

    public void playMediaPlayer(View view){
       if (mediaSwitch.isChecked())
       {
           alarm(true);
           sendNotification();
       }
       else {
           alarm(false);
       }
    }

    @SuppressLint("ResourceAsColor")
    private void sendNotification() {

        int notificationId = 001;
        String id = "my_channel_01";
        Intent viewIntent = new Intent(this, MainActivity.class);
        PendingIntent viewPendingIntent =
                PendingIntent.getActivity(this, 0, viewIntent, 0);


        NotificationCompat.WearableExtender wearableExtender = new NotificationCompat.WearableExtender()
                .setHintHideIcon(false);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, id)
                        .setSmallIcon(R.drawable.sligrologo)
                        .setContentTitle("Sligro Security")
                        .setContentText("Mobile out of reach. Respond!")
                        .extend(wearableExtender)
                        .setContentIntent(viewPendingIntent)
                .addAction(R.drawable.sligrologo, "No", viewPendingIntent);

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);

        notificationManager.notify(notificationId, notificationBuilder.build());
    }

    public boolean alarm (Boolean bool){
        tvBluetooth.setText(hardware.bluetoothState());

        if (bool){
            tvBackgroundColor.setBackgroundColor(Color.parseColor("#FF0000"));
            hardware.startMedia();
            hardware.startVibrate();
            tvMessage.setText("U bent niet beveiligd");
            tvMessage.setTextColor(Color.WHITE);
            Toast.makeText(this, "Alarm started", Toast.LENGTH_SHORT).show();
         return true;
        }
        else {
            tvBackgroundColor.setBackgroundColor(Color.parseColor("#045340"));
            hardware.pauseMedia();
            hardware.stopVibrate();
            tvMessage.setText("U bent beveiligd");
            tvMessage.setTextColor(Color.WHITE);
            Toast.makeText(this, "Alarm paused", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void fillBluetoothList(){
        if(hardware.bluetoothState().equals("Bluetooth is verbonden.")){
        ArrayAdapter adapter = new ArrayAdapter(lvConnectedDevices.getContext(),android.R.layout.simple_list_item_1);
        hardware.fillConnectedDeviceList(lvConnectedDevices,adapter);
        } else {
            Toast.makeText(this, hardware.bluetoothState(), Toast.LENGTH_SHORT).show();
        }
    }

 /*   public void screenChanges(String backgroundcolor, String messageText,String notificationText){
        tvBackgroundColor.setBackgroundColor(Color.parseColor(backgroundcolor));
        tvMessage.setText(messageText);
        tvMessage.setTextColor(Color.WHITE);
        Toast.makeText(this, notificationText.toString(), Toast.LENGTH_SHORT).show();
    }*/



}
