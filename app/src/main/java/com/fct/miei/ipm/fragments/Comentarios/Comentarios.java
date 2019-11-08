package com.fct.miei.ipm.fragments.Comentarios;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.brutal.ninjas.hackaton19.R;
import com.fct.miei.ipm.fragments.Duvidas.Duvidas;

import java.util.ArrayList;

public class Comentarios extends Fragment {


    ArrayList<ComentarioModel> dataModels;
    ListView listView;
    private static CustomAdapter adapter;

    public Comentarios() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comentario, container, false);


        //Duvidas go to
        ImageView fechar = view.findViewById(R.id.txtclose);

        fechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content, new Duvidas());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        //Comentários load
        listView=(ListView) view.findViewById(R.id.list);

        dataModels= new ArrayList<>();

        //Dummy data
        dataModels.add(new ComentarioModel(R.drawable.pessoa7, "Tenho isso resolvido no caderno mas não percebi" ));
        dataModels.add(new ComentarioModel(R.drawable.pessoa6, "Same \uD83D\uDE2D\uD83D\uDE2D\uD83D\uDE2D" ));



        adapter= new CustomAdapter(dataModels,getContext());

        listView.setAdapter(adapter);


        return view;
    }



}