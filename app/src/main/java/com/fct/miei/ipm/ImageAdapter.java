package com.fct.miei.ipm;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.brutal.ninjas.hackaton19.R;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
 
    // Keep all Images in array
    public Integer[] thumbnails = {
            R.drawable.aa, R.drawable.spbd,
            R.drawable.am, R.drawable.rit,
            R.drawable.pte, R.drawable.st,
            R.drawable.trsa,
    };
 
    // Constructor
    public ImageAdapter(Context c){
        mContext = c;
    }
 
    @Override
    public int getCount() {
        return thumbnails.length;
    }
 
    @Override
    public Object getItem(int position) {
        return thumbnails[position];
    }
 
    @Override
    public long getItemId(int position) {
        return 0;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(thumbnails[position]);
        imageView.setOnClickListener((event) ->{
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://docs.google.com/document/d/1Ns71SNE2gnDIJwpTlHc6UbnaVdbyUypkyMR_MrCOpb0/edit"));
            mContext.startActivity(intent);
        });
        imageView.setAdjustViewBounds(true);
        int padding = 8 * 4;
       imageView.setPadding(padding, padding , padding, padding); // why not be explicit about the padding while we're at it

        return imageView;
    }

}