package com.fct.miei.ipm;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.brutal.ninjas.hackaton19.R;

import com.fct.miei.ipm.fragments.CriarAula;
import com.fct.miei.ipm.fragments.Perfil;
import com.fct.miei.ipm.fragments.Ranking;
import com.fct.miei.ipm.fragments.VerAulas;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {

    private LoginState loginState;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.profile:
                    changeFragment(new Perfil());
                    return true;
                case R.id.findcourse:
                    changeFragment(new VerAulas());
                    return true;
                case R.id.newcourse:
                    changeFragment(new CriarAula());
                    return true;
                case R.id.ranking:
                    changeFragment(new Ranking());
                    return true;
                case R.id.logoutButton:
                    loginState.logout();
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
//                    finish();
                    return true;
            }
            return false;
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginState = new LoginState(this);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, instanceIdResult -> {
            String newToken = instanceIdResult.getToken();
            Log.e("newToken", newToken);
            this.getPreferences(Context.MODE_PRIVATE).edit().putString("fb", newToken).apply();
        });

        Log.d("newToken", this.getPreferences(Context.MODE_PRIVATE).getString("fb", "empty :("));

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.findcourse);

//        changeFragment(new ProcurarAulas());
        navigation.getMenu().getItem(2).setChecked(true);
    }


    @Override
    public void onBackPressed() {


        Log.d("CDA", "onBackPressed Called");
        new AlertDialog.Builder(MainActivity.this)
                .setMessage("Terminar sessão?")
                .setNegativeButton("Sim", (dialog, which) -> {
                            loginState.logout();
                            Intent intent = new Intent(MainActivity.this, IntroActivity.class);
                            startActivity(intent);
                            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
                            finish();
                }
                )
                .setPositiveButton("Não", ((dialog, which) -> {
                }))
                .create().show();
    }

    private void changeFragment(Fragment fm) {
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content, fm);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();
    }

}
