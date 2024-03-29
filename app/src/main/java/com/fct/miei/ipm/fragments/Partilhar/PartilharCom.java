package com.fct.miei.ipm.fragments.Partilhar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.brutal.ninjas.hackaton19.R;
import com.fct.miei.ipm.fragments.Adicionar.CriarExercicios;
import com.fct.miei.ipm.fragments.Documentos.CriarApontamento;
import com.fct.miei.ipm.fragments.Eventos.CriarEvento;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PartilharCom extends Fragment {


    private RecyclerView mRecyclerView;
    private ListViewAdaptor mAdapter;
    private List<Data> mDataList = new ArrayList<>();

    private RecyclerView mRecyclerViewContactos;
    private ListViewAdaptor mAdapterContactos;
    private List<Data> mDataListContactos = new ArrayList<>();

    public PartilharCom() {
        // Required empty public constructor
    }

    public void prepareList() {
        
        Data data = new Data("Francisco Matos", R.drawable.pessoa6);
        mDataList.add(data);
        data = new Data("Ana, Vitória", R.drawable.group);
        mDataList.add(data);
        data = new Data("Pedro", R.drawable.pessoa2);
        mDataList.add(data);
        data = new Data("João Veloso", R.drawable.perfil_joao);
        mDataList.add(data);
        data = new Data("Miguel Raposo", R.drawable.pessoa8);
        mDataList.add(data);
        data = new Data("Marco", R.drawable.pessoa4);
        mDataList.add(data);
        data = new Data("Carolina", R.drawable.pessoa3);
        mDataList.add(data);


    }



    private Boolean BackCriarApontamento;
    private Boolean BackCriarExercicio;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_partilharcom, container, false);

        SharedPreferences settings = getContext().getSharedPreferences("Back", 0);
        BackCriarApontamento = settings.getBoolean("BackCriarApontamento", false);
        BackCriarExercicio = settings.getBoolean("BackCriarExercicio", false);
        //Delete
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("BackCriarApontamento", false);
        editor.putBoolean("BackCriarExercicio", false);
        editor.commit();


        prepareList();
        mRecyclerView = view.findViewById(R.id.recycler_view);

        mAdapter = new ListViewAdaptor(mDataList , getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);


        //Contactos


        //Go Back

        ImageView back = view.findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Save settings
                SharedPreferences settings = getContext().getSharedPreferences("selector", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("selector", 2);
                editor.commit();
                //Go to fragment

                if(BackCriarApontamento){
                    android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.content, new CriarApontamento());
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.addToBackStack(null);
                    ft.commit();

                }
                else if(BackCriarExercicio){
                    android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.content, new CriarExercicios());
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.addToBackStack(null);
                    ft.commit();
                }
                else {
                    android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.content, new CriarEvento());
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            }
        });


        Button concluido = view.findViewById(R.id.concluido);

        concluido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mAdapter.getContactos().size() == 0 && mAdapterContactos.getContactos().size() == 0){
                    Toast.makeText(getContext(),"Selecione pelo menos um contacto" , Toast.LENGTH_LONG).show();
                }
                else{
                    //Save settings
                    SharedPreferences settings = getContext().getSharedPreferences("selector", 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putInt("selector", 2);
                    editor.commit();
                    mAdapter.saveState();
                    //Go to fragment

                    if (BackCriarApontamento) {
                        android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.content, new CriarApontamento());
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        ft.addToBackStack(null);
                        ft.commit();

                    } else if (BackCriarExercicio) {
                        android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.content, new CriarExercicios());
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        ft.addToBackStack(null);
                        ft.commit();
                    } else {
                        android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.content, new CriarEvento());
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        ft.addToBackStack(null);
                        ft.commit();
                    }
                }

            }
        });


        return view;
    }


}