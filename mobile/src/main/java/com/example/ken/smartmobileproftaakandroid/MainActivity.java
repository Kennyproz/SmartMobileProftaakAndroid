package com.example.ken.smartmobileproftaakandroid;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.RemoteInput;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
//import android.support.wearable.input.RemoteInputConstants;

public class MainActivity extends AppCompatActivity {
    Hardware hardware;
    Switch mediaSwitch;
    TextView tvBluetooth, tvMessage, tvBackgroundColor;
    ListView lvConnectedDevices;
    ImageView img;
    public static final String EXTRA_VOICE_REPLY = "extra_voice_reply";

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
        img = (ImageView)findViewById(R.id.ivCheck);
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
        if (hardware.bluetoothState().equals(BluetoothState.CONNECTED)){

        int notificationId = 001;
        String id = "my_channel_01";
        Intent viewIntent = new Intent(this, MainActivity.class);
        PendingIntent viewPendingIntent =
                PendingIntent.getActivity(this, 0, viewIntent, 0);

        //Reply options voor notification
        String replyLabel = "Reply";
        String[] replyChoices = getResources().getStringArray(R.array.reply_choices);

        //Bundle extras = new Bundle();
        //extras.putBoolean(RemoteInputConstants.EXTRA_DISALLOW_EMOJI, true);

        RemoteInput remoteInput = new RemoteInput.Builder(EXTRA_VOICE_REPLY)
                .setLabel(replyLabel)
                .setChoices(replyChoices)
                .build();

        NotificationCompat.Action action = new NotificationCompat.Action.Builder(R.drawable.sligrologo, replyLabel, viewPendingIntent)
                .addRemoteInput(remoteInput)
                .setAllowGeneratedReplies(true)
                .build();

        //Extra background image voor Smartwatch
        NotificationCompat.WearableExtender wearableExtender = new NotificationCompat.WearableExtender()
                .setHintHideIcon(false)
                .setBackground(BitmapFactory.decodeResource(
                        getResources(), R.drawable.sligrobit));

        //General notification stuff
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, id)
                        .setSmallIcon(R.drawable.sligrologo)
                        .setColor(Color.parseColor("#045340"))
                        .setContentTitle("Sligro Security")
                        .setContentText("Mobile out of reach. Respond!")
                        .extend(wearableExtender.addAction(action))
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
            tvBackgroundColor.setBackgroundColor(Color.parseColor("#8B0000"));
            hardware.startMedia();
            hardware.startVibrate();
            img.setImageResource(R.drawable.uncheck);
            return true;
        } else {
            screenChanges("U bent beveiligd.", "Alarm gepauzeerd");
            tvBackgroundColor.setBackgroundColor(Color.parseColor("#045340"));
            hardware.pauseMedia();
            hardware.stopVibrate();
            img.setImageResource(R.drawable.check);
            return false;
        }
    }

    public void fillBluetoothList() {
        if (hardware.bluetoothState().equals(BluetoothState.CONNECTED)) {
            ArrayAdapter adapter = new ArrayAdapter(lvConnectedDevices.getContext(), android.R.layout.simple_list_item_1);
            hardware.fillConnectedDeviceList(lvConnectedDevices, adapter);
        } else {
            Toast.makeText(this, "Bluetooth: " + hardware.bluetoothState().toString(), Toast.LENGTH_SHORT).show();
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

    public void settings(View view){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (mediaSwitch.isEnabled()) {
            alarm(false);
        }
        super.onBackPressed();
    }
}
