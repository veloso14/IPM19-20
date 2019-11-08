package com.fct.miei.ipm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.brutal.ninjas.hackaton19.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.hide();
        }

        findViewById(R.id.registar_button).setOnClickListener((event) -> {

             //Bind editext
            String  nome = ((EditText)findViewById(R.id.register_nome)).getText().toString();
            String email = ((EditText) findViewById(R.id.register_email)).getText().toString();
            String password = ((EditText)findViewById(R.id.register_password)).getText().toString();
            String telemovel = ((EditText)findViewById(R.id.register_telemovel)).getText().toString();

            if(!email.contains("@")){
                new AlertDialog.Builder(RegisterActivity.this)
                        .setMessage("Email inválida")
                        .setPositiveButton("Ok", ((dialog, which) -> {}) )
                        .create().show();
            }

            else if( nome.isEmpty() || email.isEmpty() || password .isEmpty() || telemovel.isEmpty()){
                new AlertDialog.Builder(RegisterActivity.this)
                        .setMessage("Os campos são todos obrigatórios")
                        .setPositiveButton("Ok", ((dialog, which) -> {}) )
                        .create().show();
            }
            else if( password.length() < 6 ){
                new AlertDialog.Builder(RegisterActivity.this)
                        .setMessage("A password tem de ter no mínimo 6 carácteres")
                        .setPositiveButton("Ok", ((dialog, which) -> {}) )
                        .create().show();
            }

            else{

                //save conta
                SharedPreferences.Editor editor = getSharedPreferences(email+ "p:" + password, MODE_PRIVATE).edit();
                editor.putBoolean(email+ "p:" + password, true);
                editor.apply();
                //Go to Login
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

        });




    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RegisterActivity.this, IntroActivity.class);
        startActivity(intent);
        finish();
    }
}
