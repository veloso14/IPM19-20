package com.fct.miei.ipm.fragments;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.brutal.ninjas.hackaton19.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NotificationPanel extends Fragment {

    // Array of strings for ListView Title
    String[] listviewTitle = new String[]{
            "Partilha",
            "Partilha",
            "Dúvida",
            "Partilha",
            "Dúvida",
            "Dúvida",
    };


    int[] listviewImage = new int[]{
            R.drawable.mail ,
            R.drawable.mail ,
            R.drawable.question ,
            R.drawable.mail ,
            R.drawable.question ,
            R.drawable.question
    };

    String[] listviewShortDescription = new String[]{
            "Luís Grilo Partilhou consigo apontamentos de IIO",
            "Rita Rebelo Partilhou consigo apontamentos de IIO",
            "Carlos Quendera publicou uma dúvida da prática de IIO",
            "Carlos Quendera Partilhou consigo apontamentos de ICL",
            "Luís Grilo publicou uma dúvida de CIAI",
            "Luís Grilo publicou uma dúvida de AM 2"
    };

    public NotificationPanel() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.listview_with_image_and_text, container, false);

        ImageView noti = view.findViewById(R.id.back);
        noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content, new Perfil());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < listviewImage.length; i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("listview_title", listviewTitle[i]);
            hm.put("listview_discription", listviewShortDescription[i]);
            hm.put("listview_image", Integer.toString(listviewImage[i]));
            aList.add(hm);
        }

        String[] from = {"listview_image", "listview_title", "listview_discription"};
        int[] to = {R.id.listview_image, R.id.listview_item_title, R.id.listview_item_short_description};

        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), aList, R.layout.listview_activity, from, to);
        ListView androidListView = (ListView) view.findViewById(R.id.list_view);
        androidListView.setAdapter(simpleAdapter);

        return view;
    }



}