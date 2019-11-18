package com.fct.miei.ipm.fragments.Partilhar;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.brutal.ninjas.hackaton19.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListViewAdaptor extends RecyclerView.Adapter<ListViewAdaptor.MyViewHolder> {
    private List<Data> mDataList;
    private int totalSelected = 0;
    private ArrayList<String> selecionados ;
    private Context context;

    public ListViewAdaptor(List<Data> dataList , Context  context) {
        this.mDataList = dataList;
        this.context = context;
        this.selecionados = getArrayList();
        if(this.selecionados == null)
            this.selecionados = new ArrayList<String>();

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view_contactos, parent, false);

        return new MyViewHolder(itemView);
    }


    public ArrayList<String> getContactos(){
        return selecionados;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        int pos = position;
        Data data = mDataList.get(pos);
        holder.name.setText(data.getName());
        holder.imageView.setImageResource(data.getImageId());
        holder.radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked) {
                    totalSelected++;
                    selecionados.add(data.getName());
                    saveArrayList(selecionados );

                }
                else{
                        selecionados.remove(pos);
                        totalSelected--;
                        saveArrayList(selecionados );
                    }
            }
        }
        );
        if(selecionados.contains(data.getName())){
            holder.radioButton.setChecked (true);
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public CheckBox radioButton;
        public ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            radioButton = view.findViewById(R.id.radioButton);
            imageView = view.findViewById(R.id.image);
        }
    }

    public void saveArrayList(ArrayList<String> list){
        SharedPreferences prefs = this.context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("pref", json);
        editor.apply();     // This line is IMPORTANT !!!
    }

    public ArrayList<String> getArrayList(){
        SharedPreferences prefs = this.context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("pref", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }

}