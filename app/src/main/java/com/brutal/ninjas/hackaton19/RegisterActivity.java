package com.brutal.ninjas.hackaton19;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.hide();
        }

        findViewById(R.id.registar_button).setOnClickListener((event) ->{
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RegisterActivity.this, IntroActivity.class);
        startActivity(intent);
        finish();
    }
}
