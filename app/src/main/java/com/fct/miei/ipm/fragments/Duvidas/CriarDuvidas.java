package com.fct.miei.ipm.fragments.Duvidas;


import android.Manifest;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.brutal.ninjas.hackaton19.R;
import com.fct.miei.ipm.NDSpinner;
import com.fct.miei.ipm.fragments.Adicionar.Adicionar;
import com.fct.miei.ipm.fragments.Home.ImageAdapter;
import com.google.android.gms.common.util.ArrayUtils;

import java.io.File;
import java.util.List;
import java.util.Timer;

import pub.devrel.easypermissions.EasyPermissions;

import static android.content.Context.MODE_PRIVATE;

public class CriarDuvidas extends Fragment implements AdapterView.OnItemSelectedListener, EasyPermissions.PermissionCallbacks {


    private static final String[] paths = {"Público", "Privado"};
    private static final String[] cadeiras = { "Interação Pessoa-Máquina",
            "Sistemas de Computação em Cloud",
            "Arquitetura e Protocolos de Redes de Computadores",
            "Introdução à Investigação Operacional",
            "Segurança de Software",
            "Criptografia",
            "Sistemas de Bases de Dados",
            "Sistemas de Computação Móvel e Ubíqua",
            "Inteligência Artificial" ,
            "Interpretação e Compilação de Linguagens"};
    private NDSpinner spinner;
    public static final int PICKFILE_RESULT_CODE = 1;
    private String fileName = "";
    private TextView fihcieroSelected;
    private Uri fileUri;

    public CriarDuvidas() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_criar_duvida, container, false);


        Spinner spinner_uc = view.findViewById(R.id.spinner_uc3);
        SharedPreferences prefs = getActivity().getSharedPreferences("Cadeiras", MODE_PRIVATE);
        String parse = prefs.getString("Cadeiras", "AA,IIO,SPBD,AM,RIT,PTE,ST");//The default value.
        String[] selecione = new String[]{"Selecione uma UC"};
        String[] cadeiras = parse.split(",");

        ArrayAdapter<String> adp = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, ArrayUtils.concat(selecione, cadeiras));
        //set the spinners adapter to the previously created one.
        spinner_uc.setAdapter(adp);

        spinner_uc.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        SharedPreferences uc = getActivity().getSharedPreferences("MYPREFS", 0);
                        SharedPreferences.Editor editor = uc.edit();
                        Log.d("UC", parent.getItemAtPosition(pos).toString());
                        editor.putString("UC", parent.getItemAtPosition(pos).toString());
                        editor.commit();
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });



        fihcieroSelected = view.findViewById(R.id.ficheiros);
        spinner = view.findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);



        ImageView filemanager = view.findViewById(R.id.fileChooser);

        filemanager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!EasyPermissions.hasPermissions(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    EasyPermissions.requestPermissions(getActivity(), "É necessário para anexar os documentos", PICKFILE_RESULT_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
                }
                else {
                    Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                    chooseFile.setType("*/*");
                    chooseFile = Intent.createChooser(chooseFile, "Choose a file");
                    startActivityForResult(chooseFile, PICKFILE_RESULT_CODE);
                }
            }
        });

        //Back
        ImageView back = view.findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content, new Duvidas());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        //Text Inputs
        TextInputLayout unidade = view.findViewById(R.id.unidade);
        EditText assunto = view.findViewById(R.id.assunto);
        EditText descricao = view.findViewById(R.id.descricao);
        //Publicat
        Button publicar = view.findViewById(R.id.publicar);

        publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences uc = getActivity().getSharedPreferences("MYPREFS", 0);
                String unidade = uc.getString("UC", "default");
                Log.d( "UC" ,  unidade);

                if(
                        assunto.getText().toString().isEmpty() || descricao.getText().toString().isEmpty()){

                    new AlertDialog.Builder(getContext())
                            .setTitle("Erro")
                            .setMessage("Por favor complete todos os espaços")
                            .setNegativeButton(android.R.string.yes, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                else if(unidade.compareTo("Selecione uma UC") == 0){
                    new AlertDialog.Builder(getContext())
                            .setTitle("Erro")
                            .setMessage("Por favor escolha uma unidade curricular")
                            .setNegativeButton(android.R.string.yes, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                else {

                    new AlertDialog.Builder(getContext())
                            .setTitle("Confirmação")
                            .setMessage("Tem a certeza que pretende adicionar dúvida?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    SharedPreferences.Editor prefs = getContext().getSharedPreferences("pref", MODE_PRIVATE).edit();
                                    prefs.remove("pref");
                                    prefs.commit();
                                    Toast.makeText(getContext(),"Dúvida publicada", Toast.LENGTH_LONG).show();
                                    android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                                    ft.replace(R.id.content, new Duvidas());
                                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                    ft.addToBackStack(null);
                                    ft.commit();
                                    Duvidas.duvidas.add(0, new String[]{assunto.getText().toString(), descricao.getText().toString(), "Agora mesmo"});

                                }})
                            .setNegativeButton(android.R.string.no, null).show();
                }

            }
        });


        return view;
    }


    @Override
    public void onPermissionsGranted(int requestCode, List perms) {
        // Add your logic here
    }

    @Override
    public void onPermissionsDenied(int requestCode, List perms) {
        // Add your logic here
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                if (resultCode == -1) {
                    fileUri = data.getData();
                    Log.d( "FILE" ,  fileUri.getPath());
                    File f = new File(fileUri.getPath());
                    fileName = f.getName();
                    this.fihcieroSelected.setText(f.getName());
                }

                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 0:
                // Whatever you want to happen when the first item gets selected
                break;
            case 1:
                // Whatever you want to happen when the second item gets selected
                break;
            case 2:
                // Whatever you want to happen when the thrid item gets selected
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}