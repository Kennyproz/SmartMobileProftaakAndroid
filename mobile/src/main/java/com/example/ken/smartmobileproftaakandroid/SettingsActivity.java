package com.example.ken.smartmobileproftaakandroid;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Ken on 11-1-2018.
 */

public class SettingsActivity extends AppCompatActivity {
    Hardware hardware;
    ListView lvBluetoothDevices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initialize();
    }

    private void initialize () {
        MediaPlayer mp = MediaPlayer.create(this, R.raw.alarm);
        lvBluetoothDevices = (ListView)findViewById(R.id.lvBluetoothDevices);
       if(getIntent().getSerializableExtra("Hardware") != null){
            hardware =(Hardware)getIntent().getSerializableExtra("Hardware");
        } else{
            hardware = new Hardware(this,mp);
        }
        fillBluetoothList();
    }
    public void resetColors(View view) {
        Toast.makeText(this, "Kleuren zijn teruggezet!", Toast.LENGTH_SHORT).show();
    }

    public void returnView(View view) {
        super.onBackPressed();
    }

    public void fillBluetoothList() {
        if (hardware.bluetoothState().equals(BluetoothState.CONNECTED)) {
            ArrayAdapter adapter = new ArrayAdapter(lvBluetoothDevices.getContext(), android.R.layout.simple_list_item_1);
            hardware.fillConnectedDeviceList(lvBluetoothDevices, adapter);
        } else {
            Toast.makeText(this, "Bluetooth: " + hardware.bluetoothState().toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
