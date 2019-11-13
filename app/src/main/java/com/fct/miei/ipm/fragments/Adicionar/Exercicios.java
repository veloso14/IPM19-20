package com.fct.miei.ipm.fragments.Adicionar;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.brutal.ninjas.hackaton19.R;
import com.fct.miei.ipm.fragments.Documentos.Documentos;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class Exercicios extends Fragment implements EasyPermissions.PermissionCallbacks {


    private boolean BackShowAdicionar = false;
    private boolean BackDocumentos = false;
    public static final int PICKFILE_RESULT_CODE = 1;

    private Uri fileUri;
    private static final String[] paths = {"Público", "Privado"};
    private Spinner spinner;

    public Exercicios() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_criar_exercicio, container, false);

        spinner = view.findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
       // spinner.setOnItemSelectedListener(this);
        //BackButton
        SharedPreferences settings = getContext().getSharedPreferences("Back", 0);
        BackShowAdicionar = settings.getBoolean("BackShowAdicionar", false);
        BackDocumentos = settings.getBoolean("BackDocumentos", false);
        //Delete
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("BackAdicionar", false);
        editor.putBoolean("BackShowAdicionar", false);
        editor.putBoolean("BackDocumentos", false);
        editor.commit();

        TextInputLayout unidade = view.findViewById(R.id.unidade);
        EditText turno = view.findViewById(R.id.turno);
        EditText assunto = view.findViewById(R.id.assunto);
        RadioGroup tipo  = view.findViewById(R.id.radioGroup);

        Button publicar = view.findViewById(R.id.publicar);
        publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if( unidade.getEditText().getText().toString().isEmpty() ||
                        assunto.getText().toString().isEmpty() ||
                        turno.getText().toString().isEmpty()){

                    new AlertDialog.Builder(getContext())
                            .setTitle("Erro")
                            .setMessage("Por favor complete todos os espaços")
                            .setNegativeButton(android.R.string.yes, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                else if(tipo.getCheckedRadioButtonId() == -1){
                    new AlertDialog.Builder(getContext())
                            .setTitle("Erro")
                            .setMessage("Escolha o tipo de aula")
                            .setNegativeButton(android.R.string.yes, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                else {
                linker();
                }

            }
        });

        ImageView back = view.findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linker();

            }
        });

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

        return view;
    }

    private void linker() {
        if (BackShowAdicionar) {
            android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.content, new ShowAdicionar());
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(null);
            ft.commit();
        }
        else if (BackDocumentos) {
            android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.content, new Documentos());
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(null);
            ft.commit();
        } else {
            android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.content, new Documentos());
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(null);
            ft.commit();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                if (resultCode == -1) {
                    fileUri = data.getData();
                    Log.d( "FILE" ,  fileUri.getPath());
                }

                break;
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List perms) {
        // Add your logic here
    }

    @Override
    public void onPermissionsDenied(int requestCode, List perms) {
        // Add your logic here
    }

}