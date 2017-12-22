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
    TextView tvBluetooth, tvMessage, tvBackgroundColor;
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

    public void initialize() {
        MediaPlayer mp = MediaPlayer.create(this, R.raw.alarm);
        hardware = new Hardware(this, mp);
        mediaSwitch = (Switch) findViewById(R.id.swMediaPlayer);
        tvMessage = (TextView) findViewById(R.id.tvSafetyMessage);
        tvBackgroundColor = (TextView) findViewById(R.id.tvBackgroundcolor);
        lvConnectedDevices = (ListView) findViewById(R.id.lvConnectedDevices);
        fillBluetoothList();
        tvBluetooth = (TextView) findViewById(R.id.tvBluetooth);
        setTvBluetoothText();
    }

    public void playMediaPlayer(View view) {
        if (mediaSwitch.isChecked()) {
            alarm(true);
            sendNotification();
        } else {
            alarm(false);
        }
    }

    @SuppressLint("ResourceAsColor")
    private void sendNotification() {
        if (hardware.bluetoothState().equals("Bluetooth is verbonden.")) {
            int notificationId = 001;
            // The channel ID of the notification.
            String id = "my_channel_01";
            // Build intent for notification content
            Intent viewIntent = new Intent(this, MainActivity.class);
            //viewIntent.putExtra(EXTRA_EVENT_ID, eventId);
            PendingIntent viewPendingIntent =
                    PendingIntent.getActivity(this, 0, viewIntent, 0);

            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(this, id)
                            .setSmallIcon(R.drawable.sligrologo)
                            .setContentTitle("Sligro Security")
                            .setContentText("Help me, i'm being stolen!")
                            .setContentIntent(viewPendingIntent);

            NotificationManagerCompat notificationManager =
                    NotificationManagerCompat.from(this);

            notificationManager.notify(notificationId, notificationBuilder.build());
        }
    }

    public boolean alarm(Boolean bool) {
        setTvBluetoothText();
        if (bool) {
            screenChanges("U bent niet beveiligd.", "Alarm started");
            tvBackgroundColor.setBackgroundColor(Color.parseColor("#FF0000"));
            hardware.startMedia();
            hardware.startVibrate();
            //  tvMessage.setText("U bent niet beveiligd");
            //Toast.makeText(this, "Alarm started", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            screenChanges("U bent beveiligd.", "Alarm gepauzeerd");
            tvBackgroundColor.setBackgroundColor(Color.parseColor("#045340"));
            hardware.pauseMedia();
            hardware.stopVibrate();
            return false;
        }
    }

    public void fillBluetoothList() {
        if (hardware.bluetoothState().equals(BluetoothState.CONNECTED)) {
            ArrayAdapter adapter = new ArrayAdapter(lvConnectedDevices.getContext(), android.R.layout.simple_list_item_1);
            hardware.fillConnectedDeviceList(lvConnectedDevices, adapter);
        } else {
            Toast.makeText(this, hardware.bluetoothState().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void screenChanges(String messageText, String notificationText) {
        tvMessage.setText(messageText);
        tvMessage.setTextColor(Color.WHITE);
        Toast.makeText(this, notificationText, Toast.LENGTH_SHORT).show();
    }

    public void setTvBluetoothText() {
        switch (hardware.bluetoothState()) {
            case CONNECTED:
                this.tvBluetooth.setText("Bluetooth is verbonden.");
                break;
            case NOT_CONNECTED:
                this.tvBluetooth.setText("Bluetooth is niet verbonden.");
                break;
            case NOT_SUPPORTED:
                this.tvBluetooth.setText("Apparaat ondersteunt geen bluetooth.");
                break;
            default:
                this.tvBluetooth.setText("Onbekende bluetooth status");
        }
    }
}
