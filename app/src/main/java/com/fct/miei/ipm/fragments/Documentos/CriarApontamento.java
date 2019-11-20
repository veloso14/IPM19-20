package com.fct.miei.ipm.fragments.Documentos;

import android.Manifest;
import android.app.FragmentTransaction;
import android.content.Context;
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
import com.fct.miei.ipm.fragments.Adicionar.ShowAdicionar;
import com.fct.miei.ipm.fragments.Partilhar.PartilharCom;
import com.google.android.gms.common.util.ArrayUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pub.devrel.easypermissions.EasyPermissions;

import static android.content.Context.MODE_PRIVATE;

public class CriarApontamento extends Fragment implements  AdapterView.OnItemSelectedListener , EasyPermissions.PermissionCallbacks {





    private boolean BackShowAdicionar = false;
    private int inited = 0;
    private String fileName = "";
    private TextView fihcieroSelected;
    private View vista;

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
        this.vista = view;

        Spinner spinner_uc = view.findViewById(R.id.spinner_uc);
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
                                editor.putInt("unidade", ((Spinner)vista.findViewById(R.id.spinner_uc)).getSelectedItemPosition() );
                                editor.putString("turno", ((EditText)vista.findViewById(R.id.turno)).getText().toString()  );
                                editor.putString("professor", ((EditText)vista.findViewById(R.id.professor)).getText().toString()  );
                                editor.putString("assunto", ((EditText)vista.findViewById(R.id.assunto)).getText().toString()  );
                                editor.putString("descricao", ((EditText)vista.findViewById(R.id.descricao)).getText().toString()  );
                                editor.putString("ficheiros", ((TextView)vista.findViewById(R.id.ficheiros)).getText().toString()  );
                               // Log.d("RadioButton" , "dei set " + ((RadioGroup)vista.findViewById(R.id.radiogroupo)).getCheckedRadioButtonId());
                                editor.putInt("radioGroup", ((RadioGroup)vista.findViewById(R.id.radiogroupo)).getCheckedRadioButtonId()  );
                                editor.apply();
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

        RadioGroup tipo  = view.findViewById(R.id.radiogroupo);

        publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences uc = getActivity().getSharedPreferences("MYPREFS", 0);
                String unidade = uc.getString("UC", "default");
                Log.d( "UC" ,  unidade);

                if(  assunto.getText().toString().isEmpty() ||
                        descricao.getText().toString().isEmpty() ||
                        professor.getText().toString().isEmpty()){

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

                    new AlertDialog.Builder(getContext())
                            .setTitle("Confirmação")
                            .setMessage("Tem a certeza que pretende adicionar apontamento?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    SharedPreferences.Editor prefs = getContext().getSharedPreferences("pref", MODE_PRIVATE).edit();
                                    prefs.remove("pref");
                                    prefs.commit();
                                    Toast.makeText(getContext(),"Apontamento criado com sucesso",Toast.LENGTH_LONG).show();
                                    linker();
                                }})
                            .setNegativeButton(android.R.string.no, null).show();

                }

            }
        });

        //Check Selector
        SharedPreferences settingss = getActivity().getSharedPreferences("selector", 0);
        int selected = settingss.getInt("selector", 0);
        dropdown.setSelection(selected);
        settingss.edit().remove("selector").commit();
        if(selected == 2){
            ArrayList<String> nomes = getArrayList();
            Set<String> set = new HashSet<>(nomes);
            nomes.clear();
            nomes.addAll(set);
            String selecionados = "";
            for(int i = 0 ; i < nomes.size() ; i++){
                selecionados = selecionados + nomes.get(i) + " ";
                if( (i + 1 )< nomes.size())
                    selecionados = selecionados + "," ;

            }

            TextView pessoas = view.findViewById(R.id.pessoas);
            pessoas.setText(selecionados);

        }

        //Restore
        SharedPreferences Restore = getActivity().getSharedPreferences("CriarExercicios", MODE_PRIVATE);
        ((Spinner)view.findViewById(R.id.spinner_uc)).setSelection(Restore.getInt("unidade",0));
        ((EditText)view.findViewById(R.id.turno)).setText(Restore.getString("turno",""));
        ((EditText)view.findViewById(R.id.professor)).setText(Restore.getString("professor",""));
        ((EditText)view.findViewById(R.id.descricao)).setText(Restore.getString("descricao",""));
        ((EditText)view.findViewById(R.id.assunto)).setText(Restore.getString("assunto",""));
        ((TextView)view.findViewById(R.id.ficheiros)).setText(Restore.getString("ficheiros",""));

        //RadioButton
        int id = Restore.getInt("radioGroup" ,-1);

        switch (id) {
            case R.id.Chest:
                ((RadioButton)((RadioGroup)view.findViewById(R.id.radiogroupo)).getChildAt(0)).setChecked(true);
                break;
            case R.id.Leg:
                ((RadioButton)((RadioGroup)view.findViewById(R.id.radiogroupo)).getChildAt(1)).setChecked(true);
                break;
            case R.id.Shoulder:
                ((RadioButton)((RadioGroup)view.findViewById(R.id.radiogroupo)).getChildAt(2)).setChecked(true);
                break;
            //other checks for the other RadioButtons ids from the RadioGroup
            case -1:
                // no RadioButton is checked inthe Radiogroup
                break;
        }

        ((TextView)view.findViewById(R.id.ficheiros)).setText(Restore.getString("ficheiros",""));

        //Limpa a variavel
        SharedPreferences.Editor selector = Restore.edit();
        selector.putInt("selector", 0);
        selector.remove("unidade");
        selector.remove("turno");
        selector.remove("assunto");
        selector.remove("descricao");
        selector.remove("ficheiros");
        selector.remove("professor");
        selector.remove("radioGroup");
        selector.commit();

        //Limpa a variavel
        SharedPreferences.Editor clean = settingss.edit();
        clean.putInt("selector", 0);
        clean.commit();

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

        SharedPreferences prefs = getContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("pref");
        editor.apply();     // This line is IMPORTANT !!!

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

    public ArrayList<String> getArrayList(){
        SharedPreferences prefs = getContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("pref", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
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