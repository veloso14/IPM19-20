package com.fct.miei.ipm.fragments.Eventos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.brutal.ninjas.hackaton19.R;
import com.fct.miei.ipm.semNet;

public class CriarEvento  extends Fragment {


    public CriarEvento() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_criar_evento , container, false);


        ImageView back = view.findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content, new Eventos());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        TextInputLayout titulo = view.findViewById(R.id.tituloinput);
        //Criar evento
        Button criar = view.findViewById(R.id.criar);

        criar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Save
                SharedPreferences settings = getContext().getSharedPreferences("Eventos", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("Eventos",
                        "  {\n" +
                        "    \"time\": \"19/08/2019\",\n" +
                        "    \"color\": \"#08A5CB\",\n" +
                        "    \"name\": \"" + titulo.getEditText().getText().toString() + "\"\n" +
                        "  },\n" );
                editor.commit();

                Toast.makeText(getContext(),"Evento criado com sucesso",Toast.LENGTH_LONG).show();
                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content, new Eventos());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        return view;
    }
}
