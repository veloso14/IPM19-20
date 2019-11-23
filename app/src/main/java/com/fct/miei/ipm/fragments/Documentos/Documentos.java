package com.fct.miei.ipm.fragments.Documentos;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.brutal.ninjas.hackaton19.R;
import com.fct.miei.ipm.fragments.Adicionar.CriarExercicios;
import com.fct.miei.ipm.fragments.Duvidas.Duvidas;
import com.fct.miei.ipm.fragments.Home.Home;
import com.fct.miei.ipm.fragments.Home.ImageAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import static android.content.Context.MODE_PRIVATE;

public class Documentos extends Fragment {

    private RecyclerView gridView;
    private GridViewAdapterDocumentos gridViewAdapter;
    public ArrayList<RecyclerViewItem> operatingSystems;
    private Dialog myDialog;
    private View vista;
    private int searched ;
    private int classif_ag_dual;
    private int classif_dual;
    private String searchKey = "";
    private int[] stars = {100, 80};

    private RecyclerViewItem doc1_ex_csv = new RecyclerViewItem(R.drawable.csv, "Ex 2.", 40, 1);
    private RecyclerViewItem doc2_ex_pdf = new RecyclerViewItem(R.drawable.pdf, "Ex 2.", 20, 1);
    private RecyclerViewItem doc3_dual_doc = new RecyclerViewItem(R.drawable.doc, "Ag. Dual", 100, 2);
    private RecyclerViewItem doc4_simp_zip = new RecyclerViewItem(R.drawable.zip, "Simplex", 70, 3);
    private RecyclerViewItem doc5_simp_doc = new RecyclerViewItem(R.drawable.doc, "Simplex", 60, 4);
    private RecyclerViewItem doc6_dual_ppt = new RecyclerViewItem(R.drawable.ppt, "Dual", stars[1], 4);
    private RecyclerViewItem doc7_ex_pdf2 = new RecyclerViewItem(R.drawable.pdf, "Ex 2.", 44, 5);

    public Documentos() {
        // Required empty public constructor
    }

    //0
    private void setDummyData() {

        operatingSystems = new ArrayList<>();
        operatingSystems.add(doc1_ex_csv);
        operatingSystems.add(doc2_ex_pdf);
        operatingSystems.add(doc3_dual_doc);
        operatingSystems.add(doc4_simp_zip);
        operatingSystems.add(doc5_simp_doc);
        operatingSystems.add(doc6_dual_ppt);
        operatingSystems.add(doc7_ex_pdf2);


    }
    //1
    private void setOrderedDummyDataRuyCosta() {

        operatingSystems = new ArrayList<>();
        operatingSystems.add(doc3_dual_doc);
        operatingSystems.add(doc7_ex_pdf2);

    }
    //2
    private void setOrderedAplhabeticedDummyData() {

        if(operatingSystems == null || operatingSystems.size() == 2){
            setDummyData();
        }

        Collections.sort(filterDoc(operatingSystems), RecyclerViewItem.DocNameComp);


    }
    //3
    private void setOrderedLeaseRecenteDummyData() {

        if(operatingSystems == null || operatingSystems.size() == 2){
            setDummyData();
        }

        Collections.sort(filterDoc(operatingSystems), RecyclerViewItem.DocOlderComp);


    }
    //4
    private void setOrderedMostRecenteDummyData() {


        if(operatingSystems == null || operatingSystems.size() == 2){
            setDummyData();
        }

        Collections.sort(filterDoc(operatingSystems), RecyclerViewItem.DocRecentComp);

    }
    //5
    private void setOrderedDummyData() {

        if(operatingSystems == null || operatingSystems.size() == 2){
            setDummyData();
        }

        Collections.sort(filterDoc(operatingSystems), RecyclerViewItem.DocClassificationComp);


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


        SharedPreferences pref = getContext().getSharedPreferences("docRating", MODE_PRIVATE);
        Set<String> lastDocRating = pref.getStringSet("docRating", null);


        SharedPreferences settingsPreferences2 = getContext().getSharedPreferences("classification", MODE_PRIVATE);
        classif_ag_dual = settingsPreferences2.getInt("classification ag dual", 0);
        classif_dual = settingsPreferences2.getInt("classification dual", 0);


        System.out.println("classification ag dual " + classif_ag_dual);
        System.out.println("classification dual " + classif_dual);


        operatingSystems.get(operatingSystems.indexOf(doc3_dual_doc)).incEstrealas(classif_ag_dual);

        if(operatingSystems.size() != 2)
            operatingSystems.get(operatingSystems.indexOf(doc6_dual_ppt)).incEstrealas(classif_dual);

        if(lastDocRating != null && lastDocRating.size() == 2){
            Object[] myArr = lastDocRating.toArray();
            String str1 = myArr[0].toString().split(" ")[0];
            String value1 = myArr[0].toString().split(" ")[1];
            String value2 = myArr[1].toString().split(" ")[1];


            int doc;
            int classif;

            if(str1.equals("class")){
                doc = Integer.parseInt(value2);
                classif = Integer.parseInt(value1);
            }
            else{
                doc = Integer.parseInt(value1);
                classif = Integer.parseInt(value2);
            }

            SharedPreferences settings = getContext().getSharedPreferences("classification", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("classif", classif);
            editor.commit();

            operatingSystems.get(doc).incEstrealas(classif);
        }






        SharedPreferences.Editor edit = getContext().getSharedPreferences("docRating", MODE_PRIVATE).edit();
        edit.putStringSet("docRating", null);
        edit.apply();


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

                        if(operatingSystems.get(position).getName().contains("Ag. Dual") || operatingSystems.get(position).getName().contains("Dual")) {
                            DocumentoWord wordDoc = new DocumentoWord();
                            wordDoc.docSelected(position, operatingSystems.get(position).getName());

                            android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.content, wordDoc);
                            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                            ft.addToBackStack("");
                            ft.commit();
                        }

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


        //Pesquisar por documento
        EditText search = view.findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (search.getText().toString().isEmpty()) {
                    Log.d("CHANGED", "Vazio");
                    searchKey = "";
                    setDummyData();
                    gridView.setAdapter( new GridViewAdapterDocumentos(getActivity(), operatingSystems) );
                    gridView.refreshDrawableState();
                } else {
                    Log.d("CHANGED", search.getText().toString());
                    searchKey = search.getText().toString();
                    ArrayList<RecyclerViewItem> temp = filterDoc(operatingSystems );
                    gridView.setAdapter( new GridViewAdapterDocumentos(getActivity(), temp) );
                    gridView.refreshDrawableState();

                }
            }
        });

        return view;
    }

    private ArrayList<RecyclerViewItem> filterDoc(ArrayList<RecyclerViewItem> operatingSystems ) {

        if(!this.searchKey.isEmpty()) {
            ArrayList<RecyclerViewItem> filtered = new ArrayList<RecyclerViewItem>();
            for (int i = 0; i < operatingSystems.size(); i++) {
                if (operatingSystems.get(i).getName().toUpperCase().contains(this.searchKey.toUpperCase()))
                    filtered.add(operatingSystems.get(i));
            }
            this.operatingSystems = filtered;
            return filtered;
        }

        return this.operatingSystems;
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
        //Fecharm
        EditText prof = myDialog.findViewById(R.id.pesquisar_prof);
        TextView pesquisar = myDialog.findViewById(R.id.pesquisar);
        pesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(  prof.getText().toString().isEmpty()){

                    new AlertDialog.Builder(getContext())
                            .setTitle("Erro")
                            .setMessage("Por favor introduza o nome do professor")
                            .setNegativeButton(android.R.string.yes, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }else {

                    setOrderedDummyDataRuyCosta();
                    SharedPreferences settings = getContext().getSharedPreferences("DOCSEARCHSELECTED", 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putInt("DOCSEARCHSELECTED", 1);
                    editor.commit();
                    gridViewAdapter = new GridViewAdapterDocumentos(getActivity(), operatingSystems);
                    gridView.setAdapter(gridViewAdapter);
                    myDialog.dismiss();


                }
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