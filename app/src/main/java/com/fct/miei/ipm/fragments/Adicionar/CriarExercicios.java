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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.brutal.ninjas.hackaton19.R;
import com.fct.miei.ipm.NDSpinner;
import com.fct.miei.ipm.fragments.Documentos.Documentos;
import com.fct.miei.ipm.fragments.Partilhar.PartilharCom;

import java.io.File;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

import static android.content.Context.MODE_PRIVATE;

public class CriarExercicios extends Fragment implements EasyPermissions.PermissionCallbacks {


    private boolean BackShowAdicionar = false;
    private boolean BackDocumentos = false;
    public static final int PICKFILE_RESULT_CODE = 1;
    private int inited = 0;

    private Uri fileUri;
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
    private String fileName = "";
    private TextView fihcieroSelected;
    private View vista;

    public CriarExercicios() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_criar_exercicio, container, false);

        this.vista = view;
        //Partilhar com
        //get the spinner from the xml.
        NDSpinner dropdown = view.findViewById(R.id.spinner1);
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
                                SharedPreferences.Editor editor = getActivity().getSharedPreferences("CriarExercicios", MODE_PRIVATE).edit();
                                editor.putString("unidade", ((TextInputLayout)vista.findViewById(R.id.unidade)).getEditText().getText().toString() );
                                editor.putString("turno", ((EditText)vista.findViewById(R.id.turno)).getText().toString()  );
                                editor.putString("assunto", ((EditText)vista.findViewById(R.id.assunto)).getText().toString()  );
                                editor.putString("ficheiros", ((TextView)vista.findViewById(R.id.ficheiros)).getText().toString()  );
                                editor.putInt("radioGroup", ((RadioGroup)vista.findViewById(R.id.radioGroup)).getCheckedRadioButtonId()  );
                                editor.apply();
                                SharedPreferences settings = getContext().getSharedPreferences("Back", 0);
                                SharedPreferences.Editor editorr = settings.edit();
                                editorr.putBoolean("BackCriarExercicio", true);
                                editorr.commit();
                                //Ir para o fragmento
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

        spinner = view.findViewById(R.id.spinner1);

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
                else if( fileName.isEmpty()){
                    new AlertDialog.Builder(getContext())
                            .setTitle("Erro")
                            .setMessage("Escolha um anexo")
                            .setNegativeButton(android.R.string.yes, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                else {
                    SharedPreferences.Editor prefs = getContext().getSharedPreferences("pref", MODE_PRIVATE).edit();
                    prefs.remove("pref");
                    prefs.commit();
                    Toast.makeText(getContext(),"Exercício cria com sucesso",Toast.LENGTH_LONG).show();
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

        //Check Selector
        SharedPreferences settingss = getActivity().getSharedPreferences("selector", 0);
        int selected = settingss.getInt("selector", 0);
        dropdown.setSelection(selected);
        settingss.edit().remove("selector").commit();

        //Restore
        SharedPreferences Restore = getActivity().getSharedPreferences("CriarExercicios", MODE_PRIVATE);
        ((AutoCompleteTextView)view.findViewById(R.id.local)).setText(Restore.getString("unidade",""));
        ((EditText)view.findViewById(R.id.turno)).setText(Restore.getString("turno",""));
        ((EditText)view.findViewById(R.id.assunto)).setText(Restore.getString("assunto",""));
        ((TextView)view.findViewById(R.id.ficheiros)).setText(Restore.getString("ficheiros",""));

        //RadioButton
        int id = Restore.getInt("radioGroup" ,-1);

        switch (id) {
            case R.id.Chest:
                ((RadioButton)((RadioGroup)view.findViewById(R.id.radioGroup)).getChildAt(0)).setChecked(true);
                break;
            case R.id.Leg:
                ((RadioButton)((RadioGroup)view.findViewById(R.id.radioGroup)).getChildAt(1)).setChecked(true);
                break;
            case R.id.Shoulder:
                ((RadioButton)((RadioGroup)view.findViewById(R.id.radioGroup)).getChildAt(2)).setChecked(true);
                break;
            //other checks for the other RadioButtons ids from the RadioGroup
            case -1:
                // no RadioButton is checked inthe Radiogroup
                break;
        }
        //Limpa a variavel
        SharedPreferences.Editor selector = Restore.edit();
        selector.putInt("selector", 0);
        selector.remove("unidade");
        selector.remove("turno");
        selector.remove("assunto");
        selector.remove("ficheiros");
        selector.remove("radioGroup");
        selector.commit();

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
                    File f = new File(fileUri.getPath());
                    fileName = f.getName();
                    this.fihcieroSelected.setText(f.getName());
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