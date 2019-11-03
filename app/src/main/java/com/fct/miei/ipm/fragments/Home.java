package com.fct.miei.ipm.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.fct.miei.ipm.ImageAdapter;
import com.brutal.ninjas.hackaton19.R;

public class Home extends Fragment {

    // TODO: Rename and change types of parameters


    public Home() {
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