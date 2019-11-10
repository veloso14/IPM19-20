package com.fct.miei.ipm.fragments.Adicionar;

import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.brutal.ninjas.hackaton19.R;
import com.fct.miei.ipm.fragments.Documentos.Documentos;

public class Exercicios extends Fragment {


    private boolean BackShowAdicionar = false;

    public Exercicios() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_criar_exercicio, container, false);

        //BackButton
        SharedPreferences settings = getContext().getSharedPreferences("Back", 0);
        BackShowAdicionar = settings.getBoolean("BackShowAdicionar", false);
        //Delete
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("BackAdicionar", false);
        editor.putBoolean("BackShowAdicionar", false);
        editor.commit();


        ImageView back = view.findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(BackShowAdicionar){
                    android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.content, new ShowAdicionar());
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.addToBackStack(null);
                    ft.commit();
                }
                else{
                    android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.content, new Documentos());
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.addToBackStack(null);
                    ft.commit();
                }

            }
        });

        return view;
    }

}