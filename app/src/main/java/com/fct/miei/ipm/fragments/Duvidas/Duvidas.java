package com.fct.miei.ipm.fragments.Duvidas;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.brutal.ninjas.hackaton19.R;
import com.fct.miei.ipm.MainActivity;
import com.fct.miei.ipm.fragments.Documentos.Documentos;
import com.fct.miei.ipm.semNet;

import java.util.LinkedList;
import java.util.List;

public class Duvidas extends Fragment {


    private NonScrollListView nonScrollListView;
    private    Dialog myDialog;
    private  View vista;


    public Duvidas() {
        // Required empty public constructor
    }


    public void ShowPopup(View v) {
        TextView txtclose;
        myDialog.setContentView(R.layout.popup_add_duvida);
        txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_duvidas, container, false);
        this.vista = view;
        myDialog = new Dialog(getContext());
        nonScrollListView = view.findViewById(R.id.listView);

        //Add duvida
        ImageView add = view.findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //Mostrar Popup
                Log.d("POPUP" , "Mostra POPUP");
                ShowPopup( vista.findViewById(android.R.id.content) );
            }
        });

        //Duvidas go to
        ImageView goBack = view.findViewById(R.id.back2);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content, new Documentos());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        //Preencher lista
        final List<String[]> values = new LinkedList<String[]>();
        values.add(new String[]{"Exercício 2 do teste de 2017                \uD83C\uDD95", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque ac erat laoreet, fermentum justo ac, lobortis nisi. Vivamus ex lorem, venenatis ac leo sit amet, ultricies fermentum urna. Donec congue arcu in libero consectetur, eleifend porttitor augue maximus. Donec et nibh tellus. Sed suscipit lacus leo, quis fermentum tellus suscipit vitae. Duis ac augue hendrerit augue auctor cursus eu quis nisl. Nulla enim magna, tempor vitae venenatis sit amet, congue semper nisl. Ut blandit mi id tincidunt maximus. Duis suscipit vestibulum arcu."});
        values.add(new String[]{"Algoritmo Simplex", "Não percebo nada"});
        values.add(new String[]{"Dual - Ex. 7", "Praesent placerat diam in luctus pulvinar. Vestibulum ac leo nec nunc feugiat sagittis. Duis placerat vulputate finibus. Curabitur ligula enim, iaculis id elit quis, vulputate porta massa. Aenean erat ligula, venenatis sed mauris in, luctus sagittis tortor. Curabitur viverra metus ut massa eleifend, et tempus massa facilisis. Proin rutrum non enim ac cursus. In feugiat magna a augue placerat auctor. In auctor tortor tempor mattis sodales. Nulla aliquet ligula nec interdum luctus. Duis tincidunt leo non libero varius malesuada. Maecenas tempus interdum erat nec molestie."});
        values.add(new String[]{"Teste 1 de 17/18", "Nullam non felis sem. Proin tempor libero ut felis bibendum, ut molestie nisi ullamcorper. Proin vulputate iaculis maximus. Aenean a nunc bibendum, congue nunc vel, sodales nunc. Vestibulum id pharetra urna, quis bibendum orci. Donec nec lorem ac ante gravida cursus. Nullam et arcu sapien. Sed orci quam, commodo sit amet tempor vel, bibendum ut erat. Donec eu bibendum eros, vel semper nisl. Sed ac felis iaculis, tincidunt sem nec, suscipit metus. Suspendisse eros felis, malesuada quis fermentum id, ullamcorper molestie urna. Donec vulputate sed lorem consectetur tristique. Curabitur non leo neque."});


        nonScrollListView.setAdapter(new ArrayAdapter<String[]>(getContext(), android.R.layout.simple_expandable_list_item_2, android.R.id.text1, values) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                // Get the current item from ListView
                View view = super.getView(position, convertView, parent);

                String[] entry = values.get(position);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                text1.setTextSize(20);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                text1.setText(entry[0]);
                text2.setText(entry[1]);
                text2.setTextSize(14);

                // Get the Layout Parameters for ListView Current Item View
                ViewGroup.LayoutParams params = view.getLayoutParams();

                // Set the height of the Item View
                params.height = 260;
                view.setLayoutParams(params);
                return view;
            }
        });



        return view;
    }

}