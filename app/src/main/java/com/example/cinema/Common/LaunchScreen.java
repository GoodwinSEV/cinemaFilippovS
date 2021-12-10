package com.example.cinema.Common;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cinema.R;

public class LaunchScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_screen);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean fs = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("firstRun",false);
                if(fs)
                {
                    getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("firstRun",true).apply();
                Intent i = new Intent(LaunchScreen.this, SignUp.class);
                startActivity(i);
                }else{
                    Intent i = new Intent(LaunchScreen.this, SignIn.class);
                    startActivity(i);
                }
                finish();
            }
        },5*600);
    }
}
