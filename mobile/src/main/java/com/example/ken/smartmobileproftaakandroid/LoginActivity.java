package com.example.ken.smartmobileproftaakandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Ken on 11-1-2018.
 */

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void Login(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void forgetPass(View view){
        Toast.makeText(this, "Deze functie is momenteel niet geïmplementeerd", Toast.LENGTH_SHORT).show();
    }
}
