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
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.brutal.ninjas.hackaton19.R;
import com.fct.miei.ipm.semNet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Ranking extends Fragment {

    // Array of strings for ListView Title
    String[] listviewTitle = new String[]{
            "1º Lugar                                      ⭐ 1500",
            "2º Lugar                                      ⭐ 1210",
            "3º Lugar                                      ⭐ 1000",
            "4º Lugar                                      ⭐ 502",
    };


    int[] listviewImage = new int[]{
            R.drawable.veloso,
            R.drawable.rita,
            R.drawable.carlos,
            R.drawable.grilo,
    };

    String[] listviewShortDescription = new String[]{
            "\nJoão Veloso\nMestrado Integrado em Engenharia Informática",
            "\nRita Rebelo\nMestrado Integrado em Engenharia Informática",
            "\nCarlos Quendera\nMestrado Integrado em Engenharia Informática",
            "\nLuís António Grilo\nLicenciatura em Bioquímica",
      };

    public Ranking() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ranking, container, false);
        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < 4; i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("listview_title", listviewTitle[i]);
            hm.put("listview_discription", listviewShortDescription[i]);
            hm.put("listview_image", Integer.toString(listviewImage[i]));
            aList.add(hm);
        }

        String[] from = {"listview_image", "listview_title", "listview_discription"};
        int[] to = {R.id.listview_image, R.id.listview_item_title, R.id.listview_item_short_description};

        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), aList, R.layout.listview_activity_ranking, from, to);
        ListView androidListView = (ListView) view.findViewById(R.id.list_view);
        androidListView.setAdapter(simpleAdapter);


        return view;
    }

}