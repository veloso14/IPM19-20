package com.fct.miei.ipm.fragments.Duvidas;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.brutal.ninjas.hackaton19.R;
import com.fct.miei.ipm.fragments.Adicionar.Adicionar;
import com.fct.miei.ipm.fragments.Adicionar.ShowAdicionar;
import com.fct.miei.ipm.fragments.Comentarios.Comentarios;
import com.fct.miei.ipm.fragments.Documentos.Documentos;
import com.fct.miei.ipm.fragments.Home.ImageAdapter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class Duvidas extends Fragment {

    private static final String DUVIDA_1 = "Exercício 2 do teste de 2017";

    private NonScrollListView nonScrollListView;
    private Dialog myDialog;
    private View vista;
    private boolean BackAdicionar = false;
    private boolean BackShowAdicionar = false;
    private GridView gridView;
    public static List<String[]> duvidas= initList();

    public Duvidas() {}

    private static List<String[]> initList(){   //default values
        List<String[]> list= new LinkedList<String[]>();
        list.add(new String[]{"Exercício 2 do teste de 2017", "Estou com dúvidas a resolver o segundo exercício 2 do teste de 2017. Alguém conseguiu resolver?", "12 min"});
        list.add(new String[]{"Algoritmo Simplex", "Não percebo nada sobre este algoritmo", "3 d"});
        list.add(new String[]{"Dual - Ex. 7", "Alguém conseguiu resolver o exercício 7 dos exercícios suplementares? A minha resolução não coincide com as soluções.", "10 Nov"});
        return list;
    }

    public void ShowPopupResultados(View v) {
        myDialog.setContentView(R.layout.popup_resultado_duvida);
        TextView txtclose = myDialog.findViewById(R.id.txtclose);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        Button pesquisar = myDialog.findViewById(R.id.submit);
        pesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content, new CriarDuvidas());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();

    }

    public void ShowPopup(View v) {
        TextView txtclose;
        myDialog.setContentView(R.layout.popup_add_duvida);
        txtclose = myDialog.findViewById(R.id.txtclose);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        //buscar
        TextView pesquisar = myDialog.findViewById(R.id.submit);
        pesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
                ShowPopupResultados(v);
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
        //BackButton
        SharedPreferences settings = getContext().getSharedPreferences("Back", 0);
        BackAdicionar = settings.getBoolean("BackAdicionar", false);
        BackShowAdicionar = settings.getBoolean("BackShowAdicionar", false);
        //Delete
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("BackAdicionar", false);
        editor.putBoolean("BackShowAdicionar", false);
        editor.commit();

        //Add duvida
        ImageView add = view.findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Mostrar Popup
                Log.d("POPUP", "Mostra POPUP");
                ShowPopup(vista.findViewById(android.R.id.content));
            }
        });

        //Duvidas go to
        ImageView goBack = view.findViewById(R.id.back2);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(BackAdicionar){
                    android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.content, new Adicionar());
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.addToBackStack(null);
                    ft.commit();

                    //atualiza view
//                    gridView.setAdapter(new ImageAdapter(getActivity()));
//                    gridView.invalidateViews();

                }
                else if(BackShowAdicionar){
                    android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.content, new ShowAdicionar());
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.addToBackStack(null);
                    ft.commit();
                }
                else {
                    android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.content, new Documentos());
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            }
        });


        nonScrollListView.setAdapter(new ArrayAdapter<String[]>(getContext(), R.layout.listview_activity_duvida, android.R.id.text1, duvidas) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                // Get the current item from ListView
                View view = super.getView(position, convertView, parent);

                String[] entry = duvidas.get(position);
                TextView text1 = view.findViewById(android.R.id.text1);
                TextView text2 = view.findViewById(android.R.id.text2);
                TextView date = view.findViewById(R.id.date);

                text1.setText(entry[0]);
                text2.setText(entry[1]);
                date.setText(entry[2]);

                // Get the Layout Parameters for ListView Current Item View
                ViewGroup.LayoutParams params = view.getLayoutParams();

                // Set the height of the Item View
                view.setLayoutParams(params);

                view.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        if (duvidas.get(position)[0]==DUVIDA_1) {
                            android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.content, new Comentarios());
                            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                            ft.addToBackStack(null);
                            ft.commit();
                        }
                    }
                });

                return view;
            }
        });

        return view;
    }

}