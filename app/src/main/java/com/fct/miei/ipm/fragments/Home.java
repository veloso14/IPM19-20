package com.fct.miei.ipm.fragments;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.brutal.ninjas.hackaton19.R;
import com.fct.miei.ipm.ImageAdapter;
import com.fct.miei.ipm.LoginActivity;

import static android.content.Context.MODE_PRIVATE;

public class Home extends Fragment {


    private Dialog myDialog;
    private View vista;
    private GridView gridView;

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
        View view = inflater.inflate(R.layout.fragment_ver_aulas, container, false);
        myDialog = new Dialog(getContext());
        this.vista = view;

        //Pesquisar por cadeira
        EditText search = view.findViewById(R.id.searchbycadeira);
        search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(search.getText().toString().isEmpty()){
                    Log.d("CHANGED" , "Vazio");
                    gridView.setAdapter(new ImageAdapter(getActivity()));
                    gridView.invalidateViews();
                }
                else {
                    Log.d("CHANGED" , s.toString());
                    gridView.setAdapter(new ImageAdapter(getActivity(), s.toString()));
                    gridView.invalidateViews();

                }
            }
        });


        //Add duvida
        ImageView add = view.findViewById(R.id.adicionar);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Mostrar Popup
                Log.d("POPUP", "Mostra POPUP");
                ShowPopup(vista.findViewById(android.R.id.content));
            }
        });

        //Porque o Android é atrasado
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        gridView = view.findViewById(R.id.course_container);
        gridView.setAdapter(new ImageAdapter(getActivity()));

        return view;
    }


    public void ShowPopup(View v) {
        TextView txtclose;
        myDialog.setContentView(R.layout.popup_nova_cadeira);
        txtclose = myDialog.findViewById(R.id.txtclose);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        //buscar
        EditText cadeira =  myDialog.findViewById(R.id.cadeira);
        TextView pesquisar = myDialog.findViewById(R.id.pesquisar);
        pesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cadeiraString = cadeira.getText().toString();
                if(cadeiraString.isEmpty()){
                    new AlertDialog.Builder(getContext())
                            .setMessage("Preencha o nome da cadeira")
                            .setPositiveButton("Ok", ((dialog, which) -> {}) )
                            .create().show();

                }
                else{
                    //Adiciona
                    SharedPreferences prefs = getActivity().getSharedPreferences("Cadeiras", MODE_PRIVATE);
                    String parse = prefs.getString("Cadeiras", "AA,IIO,SPBD,AM,RIT,PTE,ST");//The default value.
                    String[] nomesCadeiras = parse.split(",");
                    nomesCadeiras = increaseArray(nomesCadeiras,1);
                    nomesCadeiras[nomesCadeiras.length - 1] = cadeiraString;

                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < nomesCadeiras.length; i++) {
                        sb.append(nomesCadeiras[i]).append(",");
                    }

                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("Cadeiras", MODE_PRIVATE).edit();
                    editor.putString("Cadeiras", sb.toString());
                    editor.apply();

                    //atualiza view
                    gridView.setAdapter(new ImageAdapter(getActivity()));
                    gridView.invalidateViews();
                    //Fecha Popup
                    myDialog.dismiss();
                }

            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }


    public String[] increaseArray(String[] theArray, int increaseBy)
    {
        int i = theArray.length;
        int n = ++i;
        String[] newArray = new String[n];
        for(int cnt=0;cnt<theArray.length;cnt++)
        {
            newArray[cnt] = theArray[cnt];
        }
        return newArray;
    }
}
