package com.fct.miei.ipm.fragments.Eventos;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brutal.ninjas.hackaton19.R;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CalendarioAdapter extends RecyclerView.Adapter<CalendarioAdapter.ViewHolder> {

    private ArrayList<Event> dataset;
    private eventoListener evento_listener;

    CalendarioAdapter(ArrayList<Event> dataset, eventoListener evento_listener) {
        this.dataset = dataset;
        this.evento_listener = evento_listener;
    }


    @Override
    public CalendarioAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_evento, parent, false);
        return new ViewHolder(v, evento_listener);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int pos) {
        Event evt = dataset.get(pos);
        if (evt != null) {
            holder.titulo.setText((String) evt.getData());
            long time = evt.getTimeInMillis();
            if (time > 0) {
                holder.data.setText(new SimpleDateFormat("dd MMMM", Locale.getDefault()).format(time));
                holder.cor.setBackgroundColor(evt.getColor());
            }
        }

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public interface eventoListener {
        void onEventoClick(int pos);
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView titulo;
        TextView data;
        TextView cor;

        eventoListener evento_listener;

        ViewHolder(View v, eventoListener evento_listener) {
            super(v);
            titulo = v.findViewById(R.id.titulo);
            data = v.findViewById(R.id.data);
            cor = v.findViewById(R.id.cor);
            this.evento_listener = evento_listener;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            evento_listener.onEventoClick(getAdapterPosition());
        }
    }
}
