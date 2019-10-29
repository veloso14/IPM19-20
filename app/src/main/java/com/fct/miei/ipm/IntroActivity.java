package com.fct.miei.ipm;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.brutal.ninjas.hackaton19.R;

public class IntroActivity extends AppCompatActivity {
    private static LoginState loginState;

    private void hideActionBar() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.hide();
        }
    }
    private void launchMainActivity() {
        Intent firstPage = new Intent(IntroActivity.this, MainActivity.class);
        startActivity(firstPage);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideActionBar();
        loginState=  new LoginState(this);
        if (loginState.isLoggedIn()) {
            //skip login, already done
            launchMainActivity();
            finish();
            Toast.makeText(this, "Sessão iniciada automaticamente", Toast.LENGTH_SHORT).show();
        }else{

            setContentView(R.layout.activity_intro);
            findViewById(R.id.intro_button_login).setOnClickListener( (event) ->{
                Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
        });
            findViewById(R.id.intro_button_register).setOnClickListener( (event) ->{
                Intent intent = new Intent(IntroActivity.this, RegisterActivity.class);
                    startActivity(intent);
                    finish();

        });
//        getSupportActionBar().hide();
        }
    }
}
