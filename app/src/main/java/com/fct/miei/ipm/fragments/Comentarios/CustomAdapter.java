package com.fct.miei.ipm.fragments.Comentarios;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.brutal.ninjas.hackaton19.R;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<ComentarioModel> implements View.OnClickListener{

    private ArrayList<ComentarioModel> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView comentario;
        ImageView imagemPerfil;
    }

    public CustomAdapter(ArrayList<ComentarioModel> data, Context context) {
        super(context, R.layout.row_comentario, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        ComentarioModel dataModel=(ComentarioModel)object;
        Log.d("CLICKED" , "Clicou no comentario : " + v.getId());


    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ComentarioModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_comentario, parent, false);
            viewHolder.comentario = (TextView) convertView.findViewById(R.id.comentario);
            viewHolder.imagemPerfil = (ImageView) convertView.findViewById(R.id.imagemPerfil);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.comentario.setText(dataModel.getTexto());
        viewHolder.imagemPerfil.setBackgroundResource(dataModel.getIdImage());
        viewHolder.comentario.setOnClickListener(this);
        viewHolder.comentario.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }
}
