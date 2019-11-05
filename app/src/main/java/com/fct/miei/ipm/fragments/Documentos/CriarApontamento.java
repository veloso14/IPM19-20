package com.fct.miei.ipm.fragments.Documentos;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.brutal.ninjas.hackaton19.R;
import com.fct.miei.ipm.semNet;

public class CriarApontamento extends Fragment {


    public CriarApontamento() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_criar_apontamento, container, false);


        return view;
    }

}