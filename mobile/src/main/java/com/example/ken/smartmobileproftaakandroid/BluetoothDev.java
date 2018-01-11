package com.example.ken.smartmobileproftaakandroid;

import android.bluetooth.BluetoothDevice;

/**
 * Created by Ken on 15-12-2017.
 */

public class BluetoothDev {
    BluetoothDevice bluetoothDevice;

    public BluetoothDev(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
    }

    @Override
    public String toString() {
        if (bluetoothDevice != null)
        {
            return bluetoothDevice.getName();
        }
        else {
         return "";
        }
    }
}
