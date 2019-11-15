package com.fct.miei.ipm.fragments.Documentos;

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
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.brutal.ninjas.hackaton19.R;
import com.fct.miei.ipm.fragments.Adicionar.ShowAdicionar;
import com.fct.miei.ipm.fragments.Duvidas.Duvidas;
import com.fct.miei.ipm.fragments.Partilhar.PartilharCom;

import java.io.File;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

import static android.content.Context.MODE_PRIVATE;

public class CriarApontamento extends Fragment implements  AdapterView.OnItemSelectedListener , EasyPermissions.PermissionCallbacks {



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
    private Spinner spinner;
    private boolean BackShowAdicionar = false;
    private int inited = 0;
    private String fileName = "";
    private TextView fihcieroSelected;

    public static final int PICKFILE_RESULT_CODE = 1;

    private Uri fileUri;

    public CriarApontamento() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_criar_apontamento, container, false);

        //Partilhar com
        //get the spinner from the xml.
        Spinner dropdown = view.findViewById(R.id.spinner1);
        //create a list of items for the spinner.
        String[] items = new String[]{"Público", "Privado", "Partilhar com"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                        Log.d("Clicked", "Clicou em " + pos);

                        if (inited >= 1) {
                            Object item = parent.getItemAtPosition(pos);
                            System.out.println(item.toString());     //prints the text in spinner item.
                            //Clicou no partilhado com
                            if (pos == 2) {

                                //Salvar as variaveis
                                SharedPreferences.Editor editor = getActivity().getSharedPreferences("CriarEventosInput", MODE_PRIVATE).edit();
                              /*  editor.putString("titulo", titulo.getText().toString());
                                editor.putString("local", local.getText().toString());
                                editor.putString("inicio", inicio.getText().toString());
                                editor.putString("fim", fim.getText().toString());
                                editor.putString("npessoas", numPessoas.getText().toString());
                                editor.apply();*/
                                //Ir para o fragmento
                                SharedPreferences settings = getContext().getSharedPreferences("Back", 0);
                                SharedPreferences.Editor editorr = settings.edit();
                                editorr.putBoolean("BackCriarApontamento", true);
                                editorr.commit();
                                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                                ft.replace(R.id.content, new PartilharCom());
                                ft.setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                ft.addToBackStack(null);
                                ft.commit();
                            }
                        }
                        inited++;

                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });


        //AutoComplete
        ArrayAdapter<String> adapterSearch = new ArrayAdapter<String>(getContext(),android.R.layout.select_dialog_item, cadeiras);
        //Find TextView control
        AutoCompleteTextView acTextView = (AutoCompleteTextView) view.findViewById(R.id.local);
        //Set the number of characters the user must type before the drop down list is shown
        acTextView.setThreshold(1);
        //Set the adapter
        acTextView.setAdapter(adapterSearch);

        fihcieroSelected = view.findViewById(R.id.ficheiros);


        //BackButton
        SharedPreferences settings = getContext().getSharedPreferences("Back", 0);
        BackShowAdicionar = settings.getBoolean("BackShowAdicionar", false);
        //Delete
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("BackAdicionar", false);
        editor.putBoolean("BackShowAdicionar", false);
        editor.commit();




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

        Button publicar = view.findViewById(R.id.publicar);
        EditText descricao = view.findViewById(R.id.descricao);
        EditText assunto = view.findViewById(R.id.assunto);
        EditText professor = view.findViewById(R.id.professor);
        TextInputLayout unidade = view.findViewById(R.id.unidade);
        RadioGroup tipo  = view.findViewById(R.id.aula);

        publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if( unidade.getEditText().getText().toString().isEmpty() ||
                        assunto.getText().toString().isEmpty() ||
                        descricao.getText().toString().isEmpty() ||
                        professor.getText().toString().isEmpty()){

                    new AlertDialog.Builder(getContext())
                            .setTitle("Erro")
                            .setMessage("Por favor complete todos os espaços")
                            .setNegativeButton(android.R.string.yes, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                else if( fileName.isEmpty()){

                    new AlertDialog.Builder(getContext())
                            .setTitle("Erro")
                            .setMessage("Por favor escolha um ficheiro")
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
                    Toast.makeText(getContext(),"Publicado com Sucesso" , Toast.LENGTH_LONG).show();
                    linker();
                }

            }
        });

        return view;
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


    private void linker() {
        if (BackShowAdicionar) {
            android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.content, new ShowAdicionar());
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
    public void onPermissionsGranted(int requestCode, List perms) {
        // Add your logic here
    }

    @Override
    public void onPermissionsDenied(int requestCode, List perms) {
        // Add your logic here
    }

}