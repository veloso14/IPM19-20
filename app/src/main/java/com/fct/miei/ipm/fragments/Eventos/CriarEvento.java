package com.fct.miei.ipm.fragments.Eventos;

import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.brutal.ninjas.hackaton19.R;
import com.fct.miei.ipm.fragments.Partilhar.PartilharCom;

import java.util.Calendar;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

public class CriarEvento extends Fragment {

    private int inited = 0;
    private String date;
    private boolean allDay = false;

    public CriarEvento() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_criar_evento, container, false);


        //Get Selected date
        SharedPreferences prefs = getActivity().getSharedPreferences("DATECLICKED", MODE_PRIVATE);
        date  = prefs.getString("DATECLICKED", "14/10/2019");
        Log.d("DATE" , "Received" + date);



        //Data inicio picker
        TextView inicio = view.findViewById(R.id.turno);
        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        Log.d( "TIMEPICKER" , selectedHour + ":" + ((selectedMinute != 0 ) ? selectedMinute : "00"));
                        inicio.setText(selectedHour + ":" + ((selectedMinute != 0 ) ? selectedMinute : "00"));
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        //End of day
        TextView fim =  view.findViewById(R.id.descricao);
        fim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        Log.d( "TIMEPICKER" , selectedHour + ":" + ((selectedMinute != 0 ) ? selectedMinute : "00"));
                        fim.setText(selectedHour + ":" + ((selectedMinute != 0 ) ? selectedMinute : "00"));
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        TextView fimText =  view.findViewById(R.id.fimText);
        TextView inicioTexto =  view.findViewById(R.id.inicioTexto);
        //Switch
        Switch mySwitch = view.findViewById(R.id.allday);
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                allDay = isChecked;
                if(isChecked){
                    fim.setVisibility(View.INVISIBLE);
                    fimText.setVisibility(View.INVISIBLE);
                    inicioTexto.setVisibility(View.INVISIBLE);
                    inicio.setVisibility(View.INVISIBLE);
                }
                else{
                    fim.setVisibility(View.VISIBLE);
                    fimText.setVisibility(View.VISIBLE);
                    inicioTexto.setVisibility(View.INVISIBLE);
                    inicio.setVisibility(View.INVISIBLE);
                }
            }
        });

        ImageView back = view.findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content, new Eventos());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        EditText titulo = view.findViewById(R.id.titulo);
        EditText local = view.findViewById(R.id.unidade2);
        EditText numPessoas = view.findViewById(R.id.professor);
        //Criar evento
        Button criar = view.findViewById(R.id.concluido);

        criar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Save

                if(titulo.getText().toString().isEmpty()){
                    Toast.makeText(getContext(),"O título não pode estár vazio" ,Toast.LENGTH_LONG).show();
                }
                else if(local.getText().toString().isEmpty()){
                    Toast.makeText(getContext(),"O local não pode estár vazio" ,Toast.LENGTH_LONG).show();
                }
                else if(numPessoas.getText().toString().isEmpty()){
                    Toast.makeText(getContext(),"O número de pessoas não pode estár vazio" ,Toast.LENGTH_LONG).show();
                }
                else if( Integer.valueOf( numPessoas.getText().toString()) < 1){
                    Toast.makeText(getContext(),"O número de pessoas é invalido" ,Toast.LENGTH_LONG).show();
                }
                else if( !allDay && (  !inicio.getText().toString().contains(":") && !fim.getText().toString().contains(":"))){
                    Log.d("ALLDAY" ,  inicio.getText().toString());
                    Log.d("ALLDAY" ,  fim.getText().toString());
                    Toast.makeText(getContext(),"Indique a duracção" ,Toast.LENGTH_LONG).show();
                }
                else{
                    SharedPreferences settings = getContext().getSharedPreferences("Eventos", 0);
                    String[] playlists = settings.getString("Eventos", "").split(";");
                    playlists = increaseArray(playlists, 1);
                    SharedPreferences.Editor editor = settings.edit();

                    //Random color
                    Random obj = new Random();
                    int rand_num = obj.nextInt(0xffffff + 1);
                    // format it as hexadecimal string and print
                    String colorCode = String.format("#%06x", rand_num);

                    playlists[playlists.length - 1] = "{\n" +
                            "    \"time\": \"" + date + "\",\n" +
                            "    \"color\": \"" + colorCode + "\",\n" +
                            "    \"name\": \"" + titulo.getText().toString() + "\"\n" +
                            "  },\n";

                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < playlists.length; i++) {
                        sb.append(playlists[i]).append(";");
                    }
                    editor.putString("Eventos", sb.toString());
                    editor.commit();

                    Toast.makeText(getContext(), "Evento criado com sucesso", Toast.LENGTH_LONG).show();
                    android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.content, new Eventos());
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            }
        });


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
                                editor.putString("titulo", titulo.getText().toString());
                                editor.putString("local", local.getText().toString());
                                editor.putString("inicio", inicio.getText().toString());
                                editor.putString("fim", fim.getText().toString());
                                editor.putString("npessoas", numPessoas.getText().toString());
                                editor.apply();
                                //Ir para o fragmento
                                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                                ft.replace(R.id.content, new PartilharCom());
                                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                ft.addToBackStack(null);
                                ft.commit();
                            }
                        }
                        inited++;

                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });


        //Check Selector
        SharedPreferences settings = getActivity().getSharedPreferences("selector", 0);
        int selected = settings.getInt("selector", 0);
        dropdown.setSelection(selected);



        SharedPreferences prefsUI = getActivity().getSharedPreferences("CriarEventosInput", MODE_PRIVATE);
        titulo.setText(prefsUI.getString("titulo", ""));
        local.setText(prefsUI.getString("local", ""));
        inicio.setText(prefsUI.getString("inicio", ""));
        fim.setText(prefsUI.getString("fim", ""));
        numPessoas.setText(prefsUI.getString("npessoas", ""));
        //Limpa a variavel
        SharedPreferences.Editor editorUI = prefsUI.edit();
        editorUI.remove("titulo");
        editorUI.remove("local");
        editorUI.remove("inicio");
        editorUI.remove("fim");
        editorUI.remove("npessoas");
        editorUI.commit();


        //Limpa a variavel
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("selector", 0);
        editor.commit();



        return view;
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
