package com.fct.miei.ipm.fragments;

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

public class Ranking extends Fragment {


    public Ranking() {
        // Required empty public constructor
    }


    public boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (!isInternetAvailable()) {
            Intent intent = new Intent(getContext(), semNet.class);
            startActivity(intent);
        }

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ranking, container, false);


        WebView webView = (WebView) view.findViewById(R.id.webView);

        if (Build.VERSION.SDK_INT >= 19) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);

        webView.loadUrl("http://ninfgackaton19.atspace.cc/ranking/timetable.html");

        return view;
    }

}