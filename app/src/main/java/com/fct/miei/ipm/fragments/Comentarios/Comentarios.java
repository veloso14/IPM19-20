package com.fct.miei.ipm.fragments.Comentarios;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.brutal.ninjas.hackaton19.R;
import com.fct.miei.ipm.fragments.Duvidas.Duvidas;

import java.util.ArrayList;
import java.util.Arrays;

import static android.content.Context.MODE_PRIVATE;

public class Comentarios extends Fragment {


    private static CustomAdapter adapter;
    private ArrayList<ComentarioModel> dataModels;
    private ListView listView;

    public Comentarios() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comentario, container, false);

        //Duvidas go to
        ImageView sendcomment = view.findViewById(R.id.sendcomment);
        EditText  comment = view.findViewById(R.id.comment);

        sendcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comentario = comment.getText().toString();
                if(comentario.isEmpty()){
                    Toast.makeText(getContext(),"Comentário não pode estar vazio" , Toast.LENGTH_SHORT).show();
                }
                else {
                    //Adiciona
                    SharedPreferences prefs = getActivity().getSharedPreferences("Comentarios", MODE_PRIVATE);
                    String parse = prefs.getString("Comentarios", "");//The default value.
                    String[] comentarios = parse.split(",");
                    comentarios = increaseArray(comentarios, 1);
                    comentarios[comentarios.length - 1] = comentario;

                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < comentarios.length; i++) {
                        sb.append(comentarios[i]).append(",");
                    }

                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("Comentarios", MODE_PRIVATE).edit();
                    editor.putString("Comentarios", sb.toString());
                    editor.apply();

                    dataModels.add(new ComentarioModel(R.drawable.perfil_joao, comentario));
                    //Fecha o teclado
                    InputMethodManager inputManager = (InputMethodManager) getActivity()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    //Limpa o editext
                    comment.getText().clear();
                    //Refresh vier
                    listView.invalidateViews();
                    listView.setSelection(dataModels.size() - 1);


                }
            }
        });

        //Duvidas go to
        ImageView fechar = view.findViewById(R.id.txtclose);

        fechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content, new Duvidas());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        //Comentários load
        listView = view.findViewById(R.id.list);

        SharedPreferences prefs = getActivity().getSharedPreferences("Comentarios", MODE_PRIVATE);
        String parse = prefs.getString("Comentarios", "");//The default value.
        String[]  comentariodUser = parse.split(",");

        dataModels = new ArrayList<>();
        //Dummy data
        dataModels.add(new ComentarioModel(R.drawable.pessoa7, "Tenho isso resolvido no caderno mas não percebi"));
        dataModels.add(new ComentarioModel(R.drawable.pessoa6, "Same \uD83D\uDE2D\uD83D\uDE2D\uD83D\uDE2D"));
        for(int i = 1 ; i <  comentariodUser.length ; i++){
            dataModels.add(new ComentarioModel(R.drawable.perfil_joao, comentariodUser[i]));
        }
        //Check for user coments

        adapter = new CustomAdapter(dataModels, getContext());

        listView.setAdapter(adapter);

        listView.setSelection(dataModels.size() - 1);

        return view;
    }


    public String[] increaseArray(String[] theArray, int increaseBy) {
        int i = theArray.length;
        int n = ++i;
        String[] newArray = new String[n];
        for (int cnt = 0; cnt < theArray.length; cnt++) {
            newArray[cnt] = theArray[cnt];
        }
        return newArray;
    }


}