package com.fct.miei.ipm;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.brutal.ninjas.hackaton19.R;

public class Loading extends AppCompatActivity {

    private static int LOGO_TIME_OUT = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent firstPage = new Intent(Loading.this, IntroActivity.class);
                startActivity(firstPage);
                finish();
            }


        }, LOGO_TIME_OUT);
    }
}