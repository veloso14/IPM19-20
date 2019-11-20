package com.fct.miei.ipm;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import com.brutal.ninjas.hackaton19.R;
import com.fct.miei.ipm.fragments.Adicionar.Adicionar;
import com.fct.miei.ipm.fragments.Adicionar.ShowAdicionar;
import com.fct.miei.ipm.fragments.Documentos.Documentos;
import com.fct.miei.ipm.fragments.Eventos.Eventos;
import com.fct.miei.ipm.fragments.Home.Home;
import com.fct.miei.ipm.fragments.Perfil;
import com.fct.miei.ipm.fragments.Ranking;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {

    private LoginState loginState;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.addicionar:
                    cleanUp();
                    changeFragment(new Adicionar());
                    return true;
                case R.id.home:
                    cleanUp();
                    changeFragment(new Home());
                    return true;
                case R.id.eventos:
                    cleanUp();
                    changeFragment(new Eventos());
                    return true;
                case R.id.ranking:
                    cleanUp();
                    changeFragment(new Ranking());
                    return true;
                case R.id.perfil:
                    cleanUp();
                    changeFragment(new Perfil());
                    return true;
            }
            return false;
        }

    };

    public void cleanUp(){
        SharedPreferences prefs = this.getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("pref");
        editor.apply();     // This line is IMPORTANT !!!
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginState = new LoginState(this);
        setContentView(R.layout.activity_main);

        ActionBar bar = getSupportActionBar();

        bar.setBackgroundDrawable(new ColorDrawable(0xFFFFFFFF));
        bar.setTitle(Html.fromHtml("<font color='#007aff'>Note Uni</font>"));
        bar.setElevation(0);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, instanceIdResult -> {
            String newToken = instanceIdResult.getToken();
            Log.e("newToken", newToken);
            this.getPreferences(Context.MODE_PRIVATE).edit().putString("fb", newToken).apply();
        });

        Log.d("newToken", this.getPreferences(Context.MODE_PRIVATE).getString("fb", "empty :("));

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.home);

//        changeFragment(new ProcurarAulas());
        navigation.getMenu().getItem(2).setChecked(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Fragment fragmentInFrame = getSupportFragmentManager().findFragmentById(R.id.content);

        Intent intent = new Intent(MainActivity.this, HelpActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        if (fragmentInFrame instanceof Home){
            intent.putExtra("image", "cadeiras");
            startActivity(intent);
        }else if (fragmentInFrame instanceof Adicionar){
            intent.putExtra("image", "adicionar");
            startActivity(intent);
        }else if (fragmentInFrame instanceof ShowAdicionar){
            intent.putExtra("image", "adicionar_ex_apont");
            startActivity(intent);
        }else if (fragmentInFrame instanceof Ranking){
            intent.putExtra("image", "ranking");
            startActivity(intent);
        }else if (fragmentInFrame instanceof Eventos){
            intent.putExtra("image", "eventos");
            startActivity(intent);
        }else if (fragmentInFrame instanceof Perfil){
            intent.putExtra("image", "ranking");
            startActivity(intent);
        }else if (fragmentInFrame instanceof Documentos){
            intent.putExtra("image", "ranking");
            startActivity(intent);
        }else if (fragmentInFrame instanceof Documentos){
            intent.putExtra("image", "ranking");
            startActivity(intent);
        }

        return true;
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
