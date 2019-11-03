package com.fct.miei.ipm.fragments.Documentos;


        import android.app.Activity;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.brutal.ninjas.hackaton19.R;

        import java.util.List;


public class GridViewAdapterDocumentos extends RecyclerView.Adapter<GridViewAdapterDocumentos.ViewHolder> {
    private List<RecyclerViewItem> items;
    private Activity activity;

    public GridViewAdapterDocumentos(Activity activity, List<RecyclerViewItem> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_grid_documentos, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GridViewAdapterDocumentos.ViewHolder viewHolder, int position) {
        viewHolder.imageView.setImageResource(items.get(position).getDrawableId());
        viewHolder.textView.setText(items.get(position).getName());
        viewHolder.estrelas.setText( String.valueOf(items.get(position).getEstrelas()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * View holder to display each RecylerView item
     */
    protected class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
        private TextView estrelas;

        public ViewHolder(View view) {
            super(view);
            textView = (TextView)view.findViewById(R.id.text);
            imageView = (ImageView) view.findViewById(R.id.image);
            estrelas = (TextView)view.findViewById(R.id.stares);
        }
    }
}