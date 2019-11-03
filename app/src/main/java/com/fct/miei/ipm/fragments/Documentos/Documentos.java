package com.fct.miei.ipm.fragments.Documentos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brutal.ninjas.hackaton19.R;

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
        operatingSystems.add(new RecyclerViewItem(R.drawable.doc, "Simplex"));
        operatingSystems.add(new RecyclerViewItem(R.drawable.pdf, "Ex 2."));
        operatingSystems.add(new RecyclerViewItem(R.drawable.csv, "Ex 2."));
        operatingSystems.add(new RecyclerViewItem(R.drawable.pdf, "Ex 2."));
        operatingSystems.add(new RecyclerViewItem(R.drawable.ppt, "Simplex"));
        operatingSystems.add(new RecyclerViewItem(R.drawable.zip, "Simplex"));
        operatingSystems.add(new RecyclerViewItem(R.drawable.doc, "Simplex"));

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_documentos, container, false);


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