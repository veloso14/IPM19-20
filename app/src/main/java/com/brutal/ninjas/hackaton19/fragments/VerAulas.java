package com.brutal.ninjas.hackaton19.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.brutal.ninjas.hackaton19.ImageAdapter;
import com.brutal.ninjas.hackaton19.R;

public class VerAulas extends Fragment {

    // TODO: Rename and change types of parameters


    public VerAulas() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_ver_aulas, container, false);

        GridView gridView = (GridView)  view.findViewById(R.id.course_container) ;
        gridView.setAdapter(new ImageAdapter(getActivity()));

        return view;
    }
}
