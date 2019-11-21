package com.fct.miei.ipm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.brutal.ninjas.hackaton19.R;

public class HelpActivity extends AppCompatActivity {

    public HelpActivity() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        setContentView(R.layout.activity_help);
        getSupportActionBar().hide();

        Bundle extras = getIntent().getExtras();
        String image = extras.getString("image");

        ImageView img = findViewById(R.id.helpImage);
        img.getLayoutParams().height = displayMetrics.heightPixels;
        img.getLayoutParams().width = displayMetrics.widthPixels;

        if(image.equalsIgnoreCase("cadeiras")){
            img.setImageResource(R.drawable.cadeiras);
        }
        else if(image.equalsIgnoreCase("adicionar")){
            img.setImageResource(R.drawable.adicionar_help);
        }
        else if(image.equalsIgnoreCase("adicionar_ex_apont")){
            img.setImageResource(R.drawable.adicionar_ex_apont_help);
        }
        else if(image.equalsIgnoreCase("eventos")){
            img.setImageResource(R.drawable.eventos_help);
        }
        else if(image.equalsIgnoreCase("criar_eventos")){
            img.setImageResource(R.drawable.criar_evento_help);
        }
        else if(image.equalsIgnoreCase("ranking")){
            img.setImageResource(R.drawable.ranking_help);
        }
        else if(image.equalsIgnoreCase("perfil")){
            img.setImageResource(R.drawable.perfil_help);
        }
        else if(image.equalsIgnoreCase("notificacoes")){
            img.setImageResource(R.drawable.notificacoes_help);
        }
        else if(image.equalsIgnoreCase("criar_exercicios")){
            img.setImageResource(R.drawable.criar_exercicio_help);
        }
        else if(image.equalsIgnoreCase("criar_apontamentos")){
            img.setImageResource(R.drawable.criar_apontamento_help);
        }
        else if(image.equalsIgnoreCase("criar_duvidas")){
            img.setImageResource(R.drawable.criar_duvida_help);
        }
        else if(image.equalsIgnoreCase("comentarios")){
            img.setImageResource(R.drawable.comentarios_help);
        }
        else if(image.equalsIgnoreCase("documentos")){
            img.setImageResource(R.drawable.documentos_help);
        }
        else if(image.equalsIgnoreCase("documento")){
            img.setImageResource(R.drawable.documento_ajuda);
        }
        else if(image.equalsIgnoreCase("duvidas")){
            img.setImageResource(R.drawable.duvidas_help);
        }
        else{
            img.setImageResource(R.drawable.ranking_help);
        }
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }
}
