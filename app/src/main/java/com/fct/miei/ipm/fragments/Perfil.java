package com.fct.miei.ipm.fragments;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.brutal.ninjas.hackaton19.R;
import com.fct.miei.ipm.LoginActivity;
import com.fct.miei.ipm.LoginState;

public class Perfil extends Fragment {

    private Dialog myDialog;
    private View vista;


    public Perfil() {
        // Required empty public constructor
    }

    public void ShowPopup(View v) {
        TextView txtclose;
        myDialog.setContentView(R.layout.popup_nao_implementado);
        txtclose = myDialog.findViewById(R.id.txtclose);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        myDialog.show();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        this.vista = view;
        myDialog = new Dialog(getContext());

        LoginState loginState = new LoginState(getActivity());
        //Logout
        ImageView logout = view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginState.logout();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });


        ImageView noti = view.findViewById(R.id.adicionar);
        noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content, new NotificationPanel());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();
            }
        });



        TextView ver_mais = view.findViewById(R.id.ver_mais);
        ver_mais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Mostrar Popup
                Log.d("POPUP", "Mostra POPUP");
                ShowPopup(vista.findViewById(android.R.id.content));
            }
        });

        ImageView docs = view.findViewById(R.id.imageView6);
        docs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Mostrar Popup
                Log.d("POPUP", "Mostra POPUP");
                ShowPopup(vista.findViewById(android.R.id.content));
            }
        });


        ImageView grupos = view.findViewById(R.id.fileChooser);
        grupos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Mostrar Popup
                Log.d("POPUP", "Mostra POPUP");
                ShowPopup(vista.findViewById(android.R.id.content));
            }
        });


        ImageView eventos = view.findViewById(R.id.imageView8);
        eventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Mostrar Popup
                Log.d("POPUP", "Mostra POPUP");
                ShowPopup(vista.findViewById(android.R.id.content));
            }
        });









        return view;
    }

}