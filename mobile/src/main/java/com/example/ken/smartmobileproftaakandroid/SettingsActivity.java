package com.example.ken.smartmobileproftaakandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Ken on 11-1-2018.
 */

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void resetColors(View view) {
        Toast.makeText(this, "Kleuren zijn teruggezet!", Toast.LENGTH_SHORT).show();
    }

    public void returnView(View view) {
        super.onBackPressed();
    }
}
