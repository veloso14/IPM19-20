package com.fct.miei.ipm.fragments.Documentos;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.brutal.ninjas.hackaton19.R;
import com.fct.miei.ipm.fragments.Duvidas;
import com.fct.miei.ipm.fragments.Home;

import java.util.ArrayList;

public class Documentos extends Fragment {

    private RecyclerView gridView;
    private GridViewAdapterDocumentos gridViewAdapter;
    private ArrayList<RecyclerViewItem> operatingSystems;

    public Documentos() {
        // Required empty public constructor
    }


    private void setDummyData() {

        operatingSystems = new ArrayList<>();
        operatingSystems.add(new RecyclerViewItem(R.drawable.doc, "Simplex" , 100));
        operatingSystems.add(new RecyclerViewItem(R.drawable.pdf, "Ex 2." , 44));
        operatingSystems.add(new RecyclerViewItem(R.drawable.csv, "Ex 2."  , 40));
        operatingSystems.add(new RecyclerViewItem(R.drawable.pdf, "Ex 2."  , 20));
        operatingSystems.add(new RecyclerViewItem(R.drawable.ppt, "Simplex"  , 80));
        operatingSystems.add(new RecyclerViewItem(R.drawable.zip, "Simplex"  , 70));
        operatingSystems.add(new RecyclerViewItem(R.drawable.doc, "Simplex"  , 60));

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_documentos, container, false);


       ImageView back = view.findViewById(R.id.back);

       back.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
               ft.replace(R.id.content, new Home());
               ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
               ft.addToBackStack(null);
               ft.commit();
           }
       });

       //Duvidas go to
        ImageView duvidas = view.findViewById(R.id.duvidas);

        duvidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content, new Duvidas());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        gridView = (RecyclerView) view.findViewById(R.id.grid);

        setDummyData();

        gridView.setHasFixedSize(true);


        //set layout manager and adapter for "GridView"
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        gridView.setLayoutManager(layoutManager);
        gridViewAdapter = new GridViewAdapterDocumentos(getActivity(), operatingSystems);
        gridView.setAdapter(gridViewAdapter);

        return view;
    }



}