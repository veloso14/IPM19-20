package com.fct.miei.ipm.fragments;

import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.brutal.ninjas.hackaton19.R;

public class Adicionar extends Fragment {


    public Adicionar() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_adicionar, container, false);

        ImageButton addDocumentBtn = view.findViewById(R.id.addDocumentButton);

        addDocumentBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ImageButton btn = (ImageButton) view;
                        btn.setVisibility(View.GONE);
                    }
                }
        );

        return view;
    }

}