package com.fct.miei.ipm.fragments.Partilhar;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.brutal.ninjas.hackaton19.R;

import java.util.List;

public class ListViewAdaptor extends RecyclerView.Adapter<ListViewAdaptor.MyViewHolder> {
    private List<Data> mDataList;

    public ListViewAdaptor(List<Data> dataList) {
        this.mDataList = dataList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view_contactos, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Data data = mDataList.get(position);
        holder.name.setText(data.getName());
        holder.imageView.setImageResource(data.getImageId());
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
}