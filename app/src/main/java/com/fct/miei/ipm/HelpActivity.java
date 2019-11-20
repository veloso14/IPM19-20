package com.fct.miei.ipm;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

import com.brutal.ninjas.hackaton19.R;

public class HelpActivity extends AppCompatActivity {

    public HelpActivity() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        getSupportActionBar().hide();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();

        return true;
    }
}
