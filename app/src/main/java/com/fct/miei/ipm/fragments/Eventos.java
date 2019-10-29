package com.fct.miei.ipm.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.fct.miei.ipm.GoogleDocsContainer;
import com.brutal.ninjas.hackaton19.R;


public class Eventos extends Fragment {

    private RadioGroup classTypeSelect;

    public Eventos() {
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
        View inflate = inflater.inflate(R.layout.fragment_criar_aula, container, false);
        Context context = inflate.getContext();
        inflate.findViewById(R.id.criar_aula_button).setOnClickListener((event)->{
            int checkedRadioButtonId = classTypeSelect.getCheckedRadioButtonId();
            if (checkedRadioButtonId == R.id.criar_aula_publica) {
                //aula privada, ir para pagina de partilhar documento
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://docs.google.com/document/d/1Ns71SNE2gnDIJwpTlHc6UbnaVdbyUypkyMR_MrCOpb0/edit"));
                context.startActivity(intent);
            }else if (checkedRadioButtonId == R.id.criar_aula_privada){
                //aula publice, ir diretamente para o doc
                Intent intent = new Intent(context, GoogleDocsContainer.class);
                context.startActivity(intent);
            }
        });
        classTypeSelect = inflate.findViewById(R.id.radioGroup2);
        return inflate;
    }

}
