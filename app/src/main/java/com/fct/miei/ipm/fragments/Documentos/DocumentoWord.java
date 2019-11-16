package com.fct.miei.ipm.fragments.Documentos;


import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.brutal.ninjas.hackaton19.R;

import static android.content.Context.MODE_PRIVATE;


public class DocumentoWord extends Fragment {


    private Dialog myDialog;
    private View vista;

    public DocumentoWord() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_documento, container, false);
        myDialog = new Dialog(getContext());
        WebView webView = view.findViewById(R.id.webview);

        if (Build.VERSION.SDK_INT >= 19) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

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
                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content, new Documentos());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);

        webView.loadUrl("https://drive.google.com/file/d/1l1NsKXdk6bM6h2RIkzIHgRSytbKVUpU1/view?usp=sharing");


        return view;
    }


    public void ShowPopup(View v) {
        myDialog.setContentView(R.layout.popup_avaliar_documento);
        //Estrelas
        RatingBar rating = myDialog.findViewById(R.id.ratingBar);
        SharedPreferences prefs = getContext().getSharedPreferences("RatingWord", MODE_PRIVATE);
        float saved = prefs.getFloat("RatingWord", 0);
        if(saved > 0)
            rating.setRating(saved);

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
