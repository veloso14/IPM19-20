package com.fct.miei.ipm;

import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.brutal.ninjas.hackaton19.R;
import com.fct.miei.ipm.fragments.Documentos.Documentos;

public class ImageAdapter extends BaseAdapter {
    // Keep all Images in array
    //Antigo
    //TODO aproveitar os cantos ou ficam redondos??
    public Integer[] thumbnails = {
            R.drawable.aa, R.drawable.spbd,
            R.drawable.am, R.drawable.rit,
            R.drawable.pte, R.drawable.st,

    };


    public String[] coresCadeiras = {
            "#F99E2C",
            "#23B04C",
            "#B71414",
            "#3A64B0" ,
            "#D6D412",
            "#843494"

    };

    public String[] nomesCadeiras = {
            "AA",
            "SPBD",
            "AM",
            "RIT" ,
            "PTE",
            "ST"

    };
    private Context mContext;

    // Constructor
    public ImageAdapter(Context c) {
        mContext = c;
    }

    @Override
    public int getCount() {
        return nomesCadeiras.length;
    }

    @Override
    public Object getItem(int position) {
        return nomesCadeiras[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.feed_item_layout, parent,
                false);

        ImageView background = rowView.findViewById(R.id.background);
        background.setOnClickListener((event) -> {
            FragmentManager manager = ((AppCompatActivity) mContext).getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.content, new Documentos());
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(null);
            ft.commit();
        });
        background.setAdjustViewBounds(true);
        int padding = 8 * 4;
        background.setPadding(padding, padding, padding, padding);
        background.setBackgroundColor(Color.parseColor(coresCadeiras[position]));
        //Titulo Cadeira
        TextView cadeira  = rowView.findViewById(R.id.texto);
        cadeira.setText(nomesCadeiras[position]);


        return rowView;
    }

}