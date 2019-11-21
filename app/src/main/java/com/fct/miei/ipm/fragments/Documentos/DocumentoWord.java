package com.fct.miei.ipm.fragments.Documentos;


import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.brutal.ninjas.hackaton19.R;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

import static android.content.Context.MODE_PRIVATE;


public class DocumentoWord extends Fragment {


    private Dialog myDialog;
    float avalicao = 0;
    private int docSelected;

    public DocumentoWord() {
        // Required empty public constructor
    }

    public void docSelected(int doc){
        this.docSelected = doc;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_documento, container, false);
        myDialog = new Dialog(getContext());
        WebView webView = view.findViewById(R.id.webview);

        SharedPreferences.Editor edit = getContext().getSharedPreferences("docRating", MODE_PRIVATE).edit();
        ;
        edit.putStringSet("docRating", null);
        edit.apply();

        //Duvidas go to
        Button avaliar = view.findViewById(R.id.publicar);

        avaliar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPopup(v);
            }
        });

        //Duvidas go to
        ImageView fechar = view.findViewById(R.id.txtclose);

        fechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();

//                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
//                ft.replace(R.id.content, new Documentos());
//                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//                ft.addToBackStack(null);
//                ft.commit();
            }
        });

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);

        webView.loadUrl("https://drive.google.com/file/d/1l1NsKXdk6bM6h2RIkzIHgRSytbKVUpU1/view");


        return view;
    }


    public void ShowPopup(View v) {
        myDialog.setContentView(R.layout.popup_avaliar_documento);
        //Estrelas
        RatingBar rating = myDialog.findViewById(R.id.ratingBar);
        Set<String> lastDocRating = new TreeSet<>();


        SharedPreferences rate = getContext().getSharedPreferences("RatingWord"+this.docSelected, MODE_PRIVATE);
        rating.setRating(rate.getFloat("RatingWord"+this.docSelected, 0));

        //this.docSelected.setClassificacao(rate.getFloat("RatingWord", 0));
        //rate.edit().putFloat("RatingWord", this.docSelected.getClassificacao());
        avalicao = rating.getRating();


        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                avalicao =  rating;
            }
        });

        TextView txtclose = myDialog.findViewById(R.id.txtclose);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        //buscar
        ImageView pesquisar = myDialog.findViewById(R.id.submit);
        pesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //save settings
                if( avalicao > 0) {
                    SharedPreferences.Editor editorr = getContext().getSharedPreferences("RatingWord" + docSelected, MODE_PRIVATE).edit();
                    editorr.putFloat("RatingWord" + docSelected, avalicao);
                    editorr.apply();

                    Toast.makeText(getContext(),"Rating guardado",Toast.LENGTH_LONG).show();
                }

                System.out.println("rounded "+ Math.round(avalicao));

                SharedPreferences.Editor edition = getContext().getSharedPreferences("classification", MODE_PRIVATE).edit();
                edition.putInt("classification", Math.round(avalicao));
                edition.apply();

                SharedPreferences.Editor edit = getContext().getSharedPreferences("docRating", MODE_PRIVATE).edit();

                lastDocRating.add("doc " + Integer.toString(docSelected));
                lastDocRating.add("class " + Integer.toString(Math.round(avalicao)));

                edit.putStringSet("docRating", lastDocRating);
                edit.apply();

                SharedPreferences.Editor editor = getContext().getSharedPreferences("RatingWord", MODE_PRIVATE).edit();
                editor.putFloat("RatingWord", rating.getRating());
                editor.apply();

                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

}
