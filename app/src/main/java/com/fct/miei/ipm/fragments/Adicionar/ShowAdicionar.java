package com.fct.miei.ipm.fragments.Adicionar;

import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.brutal.ninjas.hackaton19.R;
import com.fct.miei.ipm.fragments.Documentos.CriarApontamento;
import com.fct.miei.ipm.fragments.Duvidas.Duvidas;

public class ShowAdicionar extends Fragment {


    public ShowAdicionar() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_adicionar, container, false);

        //Apontamentos
        Button exercicios = view.findViewById(R.id.exercicios);

        exercicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                SharedPreferences settings = getContext().getSharedPreferences("Back", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("BackShowAdicionar", true);
                editor.commit();

                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content, new CriarExercicios());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        //Apontamentos
        Button apontamentos = view.findViewById(R.id.apontamentos);

        apontamentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences settings = getContext().getSharedPreferences("Back", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("BackShowAdicionar", true);
                editor.commit();

                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content, new CriarApontamento());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        //backbutton
        ImageView backbutton = view.findViewById(R.id.backbutton);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content, new Adicionar());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();
            }
        });



        ImageView duvidas = view.findViewById(R.id.duvidas);

        duvidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences settings = getContext().getSharedPreferences("Back", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("BackShowAdicionar", true);
                editor.commit();

                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content, new Duvidas());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();
            }
        });




        return view;
    }
}