package com.example.ken.smartmobileproftaakandroid;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.CapabilityApi;
import com.google.android.gms.wearable.Wearable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Ken on 30-11-2017.
 */

public class Hardware implements Serializable {
    private static final String CAPABILITY_NAME = "voice_transcription";
    Vibrator vibrator;
    BluetoothAdapter bluetoothAdapter;
    MediaPlayer mediaPlayer;
    Context context;
    BluetoothDevice bluetoothDevice;

    @SuppressLint("ServiceCast")
    public Hardware (Context con, MediaPlayer mp){
        this.mediaPlayer = mp;
        this.vibrator = (Vibrator)con.getSystemService(Context.VIBRATOR_SERVICE);
        this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();// (BluetoothAdapter)con.getSystemService(Context.BLUETOOTH_SERVICE); //
        this.context = con;
    }

    public BluetoothDevice getBluetoothDevice() {
        return bluetoothDevice;
    }

    public void setBluetoothDevice(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
    }

    //source https://stackoverflow.com/questions/13950338/how-to-make-an-android-device-vibrate
    public void startVibrate(){
        long[] pattern = {0, 100, 1000, 300, 200, 100, 500, 200, 100};
        if(Build.VERSION.SDK_INT >= 26){
            vibrator.vibrate(VibrationEffect.createOneShot(60000,10));
        } else {
            vibrator.vibrate(pattern,0);
        }
    }

    public void startVibrate(int seconds){
        if(Build.VERSION.SDK_INT >= 26){
            vibrator.vibrate(VibrationEffect.createOneShot(seconds*100,10));
        } else {
            vibrator.vibrate(seconds*100);
        }
    }

    public void stopVibrate() {
        vibrator.cancel();
    }

    public void startMedia(){
        mediaPlayer.start();
    }

    public void stopMedia() {mediaPlayer.stop();}

    public void pauseMedia() {mediaPlayer.pause();}

    public BluetoothState bluetoothState(){
        if (bluetoothAdapter == null){
           return BluetoothState.NOT_SUPPORTED;
            //return "Apparaat ondersteunt geen bluetooth.";
        } else {
            if (bluetoothAdapter.isEnabled()){
                return BluetoothState.CONNECTED;
            }
            else {
                return BluetoothState.NOT_CONNECTED;
                //return "Bluetooth is niet verbonden.";
            }
        }
    }

    public Set<BluetoothDevice> getAllBluetoothDevices(){
        return bluetoothAdapter.getBondedDevices();
    }

    public void fillConnectedDeviceList(ListView listView, ArrayAdapter adapter){
        List list = new ArrayList<BluetoothDev>();
        for (BluetoothDevice b : getAllBluetoothDevices()){
            BluetoothDev bd = new BluetoothDev(b);
            list.add(bd);
        }
        adapter = new ArrayAdapter<>(listView.getContext(), android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);
    }





}
