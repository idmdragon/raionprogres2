package com.android.monagealpha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

public class splash extends AppCompatActivity {
    private int time=2500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //setelah loading maka akan langsung berpindah ke home activity
                Intent IntentMain=new Intent(splash.this, login.class);
                startActivity(IntentMain);
                finish();

            }
        },time);
    }
}


