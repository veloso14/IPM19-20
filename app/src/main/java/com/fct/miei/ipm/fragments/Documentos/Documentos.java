package com.fct.miei.ipm.fragments.Documentos;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.brutal.ninjas.hackaton19.R;
import com.fct.miei.ipm.fragments.Adicionar.CriarExercicios;
import com.fct.miei.ipm.fragments.Duvidas.Duvidas;
import com.fct.miei.ipm.fragments.Home.Home;

import java.util.ArrayList;

public class Documentos extends Fragment {

    private RecyclerView gridView;
    private GridViewAdapterDocumentos gridViewAdapter;
    private ArrayList<RecyclerViewItem> operatingSystems;
    private Dialog myDialog;
    private View vista;
    private int searched ;

    public Documentos() {
        // Required empty public constructor
    }

    //0
    private void setDummyData() {

        operatingSystems = new ArrayList<>();
        operatingSystems.add(new RecyclerViewItem(R.drawable.csv, "Ex 2.", 40));
        operatingSystems.add(new RecyclerViewItem(R.drawable.pdf, "Ex 2.", 20));
        operatingSystems.add(new RecyclerViewItem(R.drawable.doc, "Ag. Dual", 100));
        operatingSystems.add(new RecyclerViewItem(R.drawable.zip, "Simplex", 70));
        operatingSystems.add(new RecyclerViewItem(R.drawable.doc, "Simplex", 60));
        operatingSystems.add(new RecyclerViewItem(R.drawable.ppt, "Simplex", 80));
        operatingSystems.add(new RecyclerViewItem(R.drawable.pdf, "Ex 2.", 44));



    }
    //1
    private void setOrderedDummyDataRuyCosta() {

        operatingSystems = new ArrayList<>();
        operatingSystems.add(new RecyclerViewItem(R.drawable.doc, "Ag. Dual", 100));
        operatingSystems.add(new RecyclerViewItem(R.drawable.pdf, "Ex 2.", 44));

    }
    //2
    private void setOrderedAplhabeticedDummyData() {

        operatingSystems = new ArrayList<>();
        operatingSystems.add(new RecyclerViewItem(R.drawable.pdf, "Ex 2.", 44));
        operatingSystems.add(new RecyclerViewItem(R.drawable.csv, "Ex 2.", 40));
        operatingSystems.add(new RecyclerViewItem(R.drawable.pdf, "Ex 2.", 20));
        operatingSystems.add(new RecyclerViewItem(R.drawable.doc, "Ag. Dual", 100));
        operatingSystems.add(new RecyclerViewItem(R.drawable.ppt, "Simplex", 80));
        operatingSystems.add(new RecyclerViewItem(R.drawable.zip, "Simplex", 70));
        operatingSystems.add(new RecyclerViewItem(R.drawable.doc, "Simplex", 60));

    }
    //3
    private void setOrderedLeaseRecenteDummyData() {

        operatingSystems = new ArrayList<>();
        operatingSystems.add(new RecyclerViewItem(R.drawable.zip, "Simplex", 70));
        operatingSystems.add(new RecyclerViewItem(R.drawable.doc, "Simplex", 60));
        operatingSystems.add(new RecyclerViewItem(R.drawable.pdf, "Ex 2.", 20));
        operatingSystems.add(new RecyclerViewItem(R.drawable.pdf, "Ex 2.", 44));
        operatingSystems.add(new RecyclerViewItem(R.drawable.csv, "Ex 2.", 40));
        operatingSystems.add(new RecyclerViewItem(R.drawable.ppt, "Simplex", 80));
        operatingSystems.add(new RecyclerViewItem(R.drawable.doc, "Ag. Dual", 100));


    }
    //4
    private void setOrderedMostRecenteDummyData() {

        operatingSystems = new ArrayList<>();
        operatingSystems.add(new RecyclerViewItem(R.drawable.doc, "Ag. Dual", 100));
        operatingSystems.add(new RecyclerViewItem(R.drawable.ppt, "Simplex", 80));
        operatingSystems.add(new RecyclerViewItem(R.drawable.csv, "Ex 2.", 40));
        operatingSystems.add(new RecyclerViewItem(R.drawable.pdf, "Ex 2.", 44));
        operatingSystems.add(new RecyclerViewItem(R.drawable.pdf, "Ex 2.", 20));
        operatingSystems.add(new RecyclerViewItem(R.drawable.doc, "Simplex", 60));
        operatingSystems.add(new RecyclerViewItem(R.drawable.zip, "Simplex", 70));


    }
    //5
    private void setOrderedDummyData() {

        operatingSystems = new ArrayList<>();
        operatingSystems.add(new RecyclerViewItem(R.drawable.doc, "Ag. Dual", 100));
        operatingSystems.add(new RecyclerViewItem(R.drawable.ppt, "Simplex", 80));
        operatingSystems.add(new RecyclerViewItem(R.drawable.zip, "Simplex", 70));
        operatingSystems.add(new RecyclerViewItem(R.drawable.doc, "Simplex", 60));
        operatingSystems.add(new RecyclerViewItem(R.drawable.pdf, "Ex 2.", 44));
        operatingSystems.add(new RecyclerViewItem(R.drawable.csv, "Ex 2.", 40));
        operatingSystems.add(new RecyclerViewItem(R.drawable.pdf, "Ex 2.", 20));


    }


    public void ShowPopup(View v) {

        myDialog.setContentView(R.layout.popup_adicionar_documento);
        //Adicionar um apontamento
        Button apontamentos = myDialog.findViewById(R.id.apontamentos);
        apontamentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myDialog.dismiss();
                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content, new CriarApontamento());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        //Adicionar um exercicios
        Button exercicios = myDialog.findViewById(R.id.exercicios);
        exercicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
                SharedPreferences settings = getContext().getSharedPreferences("Back", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("BackDocumentos", true);
                editor.commit();

                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content, new CriarExercicios());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_documentos, container, false);

        SharedPreferences settingsPreferences = getContext().getSharedPreferences("DOCSEARCHSELECTED", 0);
        searched = settingsPreferences.getInt("DOCSEARCHSELECTED", 0);

        ImageView adicionar = view.findViewById(R.id.adicionar);

        this.vista = view;
        myDialog = new Dialog(getContext());

        adicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPopup(vista.findViewById(android.R.id.content));
            }
        });


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
        ImageView gotoDuvidas = view.findViewById(R.id.duvidas);

        gotoDuvidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content, new Duvidas());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        gridView = view.findViewById(R.id.grid);

        switch (searched){
            case 0:
                setDummyData();
                break;

            case 1:
                setOrderedDummyDataRuyCosta();
                break;

            case 2:
                setOrderedAplhabeticedDummyData();
                break;

            case 3:
                setOrderedLeaseRecenteDummyData();
                break;

            case 4:
                setOrderedMostRecenteDummyData();
                break;

            case 5:
                setOrderedDummyData();
                break;

                default:
                    setDummyData();
        }


        gridView.setHasFixedSize(true);

        //listeners dos layouts
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

        //set layout manager and adapter for "GridView"
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        gridView.setLayoutManager(layoutManager);
        gridViewAdapter = new GridViewAdapterDocumentos(getActivity(), operatingSystems);
        gridView.setAdapter(gridViewAdapter);

        ItemClickSupport.addTo(gridView)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        // do it
                        Log.d("GRIDVIEW", "Position" + position);
                        //TODO extender para mais ficheiros
                      //  if (position == 0) {
                            android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.content, new DocumentoWord());
                            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                            ft.addToBackStack(null);
                            ft.commit();
                       // }
                    }
                });


        //listeners dos settings
        ImageView filtrarpor = view.findViewById(R.id.settings);

        filtrarpor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPopupFiltrarPor(vista.findViewById(android.R.id.content));
            }
        });

        ImageView settings = view.findViewById(R.id.filtrarpor);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPopupOrdenarPor(vista.findViewById(android.R.id.content));
            }
        });


        return view;
    }

    public void ShowPopupPesquisarProfessor(View v) {
        myDialog.setContentView(R.layout.popup_search_professor);
        //buscar
        TextView fechar = myDialog.findViewById(R.id.txtclose);
        fechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
                ShowPopupFiltrarPor(vista.findViewById(android.R.id.content));
            }
        });
        //Fechar
        TextView pesquisar = myDialog.findViewById(R.id.pesquisar);
        pesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOrderedDummyDataRuyCosta();
                SharedPreferences settings = getContext().getSharedPreferences("DOCSEARCHSELECTED", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("DOCSEARCHSELECTED", 1);
                editor.commit();
                gridViewAdapter = new GridViewAdapterDocumentos(getActivity(), operatingSystems);
                gridView.setAdapter(gridViewAdapter);
                myDialog.dismiss();

            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }


    public void ShowPopupOrdenarPor(View v) {
        myDialog.setContentView(R.layout.popup_ordenar_por);
        //buscar
        TextView pesquisar = myDialog.findViewById(R.id.txtclose);
        pesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        //Melhor classificaçãoSort
        RadioButton melhorClassificacao = myDialog.findViewById(R.id.melhorClassificacao);
        RadioButton maisAntigo = myDialog.findViewById(R.id.maisAntigo);
        RadioButton maisRecente = myDialog.findViewById(R.id.maisRecente);
        RadioButton Algabetico = myDialog.findViewById(R.id.Algabetico);
        melhorClassificacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOrderedDummyData();
                SharedPreferences settings = getContext().getSharedPreferences("DOCSEARCHSELECTED", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("DOCSEARCHSELECTED", 5);
                editor.commit();
                gridViewAdapter = new GridViewAdapterDocumentos(getActivity(), operatingSystems);
                gridView.setAdapter(gridViewAdapter);
            }
        });

        maisAntigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOrderedLeaseRecenteDummyData();
                SharedPreferences settings = getContext().getSharedPreferences("DOCSEARCHSELECTED", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("DOCSEARCHSELECTED", 3);
                editor.commit();
                gridViewAdapter = new GridViewAdapterDocumentos(getActivity(), operatingSystems);
                gridView.setAdapter(gridViewAdapter);
            }
        });

        maisRecente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOrderedMostRecenteDummyData();
                SharedPreferences settings = getContext().getSharedPreferences("DOCSEARCHSELECTED", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("DOCSEARCHSELECTED", 4);
                editor.commit();
                gridViewAdapter = new GridViewAdapterDocumentos(getActivity(), operatingSystems);
                gridView.setAdapter(gridViewAdapter);
            }
        });

        Algabetico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setOrderedAplhabeticedDummyData();
                SharedPreferences settings = getContext().getSharedPreferences("DOCSEARCHSELECTED", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("DOCSEARCHSELECTED", 2);
                editor.commit();
                gridViewAdapter = new GridViewAdapterDocumentos(getActivity(), operatingSystems);
                gridView.setAdapter(gridViewAdapter);
            }
        });

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    public void ShowPopupFiltrarPor(View v) {
        myDialog.setContentView(R.layout.popup_filtrar_por);
        //buscar
        TextView pesquisar = myDialog.findViewById(R.id.txtclose);
        pesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        //Pesquisar por professor
        TextView professor = myDialog.findViewById(R.id.professor);
        professor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
                ShowPopupPesquisarProfessor(vista.findViewById(android.R.id.content));
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }


}