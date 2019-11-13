package com.fct.miei.ipm.fragments.Duvidas;


import android.Manifest;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.brutal.ninjas.hackaton19.R;
import com.fct.miei.ipm.fragments.Adicionar.Adicionar;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class CriarDuvidas extends Fragment implements AdapterView.OnItemSelectedListener, EasyPermissions.PermissionCallbacks {


    private static final String[] paths = {"Público", "Privado"};
    private Spinner spinner;
    public static final int PICKFILE_RESULT_CODE = 1;

    private Uri fileUri;

    public CriarDuvidas() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_criar_duvida, container, false);

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


                if( unidade.getEditText().getText().toString().isEmpty() || assunto.getText().toString().isEmpty() || descricao.getText().toString().isEmpty()){

                    new AlertDialog.Builder(getContext())
                            .setTitle("Erro")
                            .setMessage("Por favor complete todos os espaços")
                            .setNegativeButton(android.R.string.yes, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                else {
                    android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.content, new Duvidas());
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.addToBackStack(null);
                    ft.commit();
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