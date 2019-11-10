package com.fct.miei.ipm.fragments.Adicionar;

import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.brutal.ninjas.hackaton19.R;
import com.fct.miei.ipm.fragments.Documentos.CriarApontamento;
import com.fct.miei.ipm.fragments.Duvidas.Duvidas;

public class Adicionar extends Fragment {


    public Adicionar() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_adicionar, container, false);


        ImageView duvidas = view.findViewById(R.id.duvidas);

        duvidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences settings = getContext().getSharedPreferences("Back", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("BackAdicionar", true);
                editor.commit();

                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content, new Duvidas());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        ImageView apontamentosOuExercicios = view.findViewById(R.id.novoDocumento);

        apontamentosOuExercicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content, new ShowAdicionar());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();
            }
        });


        return view;
    }
}