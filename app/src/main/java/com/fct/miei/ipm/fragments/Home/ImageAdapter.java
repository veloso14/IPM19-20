package com.fct.miei.ipm.fragments.Home;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.brutal.ninjas.hackaton19.R;
import com.fct.miei.ipm.fragments.Documentos.Documentos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ImageAdapter extends BaseAdapter {
    // Keep all Images in array
    //Antigo
    //TODO aproveitar os cantos ou ficam redondos??
   /* public Integer[] thumbnails = {
            R.drawable.aa, R.drawable.spbd,
            R.drawable.am, R.drawable.rit,
            R.drawable.pte, R.drawable.st,

    };*/


    public String[] coresCadeiras = {
            "#F99E2C",
            "#23B04C",
            "#B71414",
            "#3A64B0",
            "#D6D412",
            "#843494"

    };

    public String[] nomesCadeiras = {
            "AA",
            "IIO",
            "SPBD",
            "AM",
            "RIT",
            "PTE",
            "ST"

    };
    private Context mContext;

    // Constructor
    public ImageAdapter(Context c) {
        mContext = c;

        SharedPreferences prefs = c.getSharedPreferences("Cadeiras", MODE_PRIVATE);
        String parse = prefs.getString("Cadeiras", "AA,IIO,SPBD,AM,RIT,PTE,ST");//The default value.
        nomesCadeiras = parse.split(",");
        //Alfabeticamente
        Arrays.sort(nomesCadeiras);
    }

    // Constructor
    public ImageAdapter(Context c, String SorteBy) {
        mContext = c;

        SharedPreferences prefs = c.getSharedPreferences("Cadeiras", MODE_PRIVATE);
        String parse = prefs.getString("Cadeiras", "AA,IIO,SPBD,AM,RIT,PTE,ST");//The default value.
        nomesCadeiras = parse.split(",");
        //Alfabeticamente
        List<String> pesquisar = Arrays.asList(nomesCadeiras);
        List<String> mathcSearch = new LinkedList<>();

        for (int i = 0; i < pesquisar.size(); i++) {
            if (pesquisar.get(i).contains(SorteBy)) {
                mathcSearch.add(pesquisar.get(i));

            }
        }
        nomesCadeiras = mathcSearch.toArray(new String[0]);
        Arrays.sort(nomesCadeiras);

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
        //Long key pressed
        //TODO fazer o delete
        background.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                //your stuff
                Log.d("CLICKED", "Long press on cadeira" + nomesCadeiras[position]);
                new AlertDialog.Builder(mContext)
                        .setMessage("Desejar apagar " + nomesCadeiras[position] + " ?")
                        .setNegativeButton("Sim", (dialog, which) -> {

                                    //Inefficient compared to use of System.arraycopy
                                    //Mas fuck it é IPM
                                    List<String> list = new ArrayList<String>(Arrays.asList(nomesCadeiras));
                                    list.remove(nomesCadeiras[position]);
                                    nomesCadeiras = list.toArray(new String[0]);

                                    StringBuilder sb = new StringBuilder();
                                    for (int i = 0; i < nomesCadeiras.length; i++) {
                                        sb.append(nomesCadeiras[i]).append(",");
                                    }

                                    //Save das novas cadeiras
                                    SharedPreferences.Editor editor = mContext.getSharedPreferences("Cadeiras", MODE_PRIVATE).edit();
                                    editor.putString("Cadeiras", sb.toString());
                                    editor.apply();
                                    notifyDataSetInvalidated();
                                }
                        )
                        .setPositiveButton("Não", ((dialog, which) -> {
                        }))
                        .create().show();
                return true;
            }
        });

        background.setAdjustViewBounds(true);
        int padding = 8 * 4;
        background.setPadding(padding, padding, padding, padding);
        background.setBackgroundColor(Color.parseColor(coresCadeiras[position % coresCadeiras.length]));
        //Titulo Cadeira
        TextView cadeira = rowView.findViewById(R.id.texto);
        cadeira.setText(nomesCadeiras[position]);


        return rowView;
    }


}