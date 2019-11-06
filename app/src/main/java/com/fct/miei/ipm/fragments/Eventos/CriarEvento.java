package com.fct.miei.ipm.fragments.Eventos;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.brutal.ninjas.hackaton19.R;
import com.fct.miei.ipm.fragments.Partilhar.PartilharCom;

public class CriarEvento  extends Fragment {


    public CriarEvento() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_criar_evento , container, false);


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

                        Object item = parent.getItemAtPosition(pos);
                        System.out.println(item.toString());     //prints the text in spinner item.
                        //Clicou no partilhado com
                        if(pos == 2){
                            android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.content, new PartilharCom());
                            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                            ft.addToBackStack(null);
                            ft.commit();
                        }

                    }
                    public void onNothingSelected(AdapterView<?> parent) {
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

        TextInputLayout titulo = view.findViewById(R.id.tituloinput);
        //Criar evento
        Button criar = view.findViewById(R.id.concluido);

        criar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Save
                SharedPreferences settings = getContext().getSharedPreferences("Eventos", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("Eventos",
                        "  {\n" +
                        "    \"time\": \"19/08/2019\",\n" +
                        "    \"color\": \"#08A5CB\",\n" +
                        "    \"name\": \"" + titulo.getEditText().getText().toString() + "\"\n" +
                        "  },\n" );
                editor.commit();

                Toast.makeText(getContext(),"Evento criado com sucesso",Toast.LENGTH_LONG).show();
                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content, new Eventos());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        return view;
    }
}
