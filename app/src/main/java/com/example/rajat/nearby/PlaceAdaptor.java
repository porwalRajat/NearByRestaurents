package com.example.rajat.nearby;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rajat on 02/09/17.
 */

public class PlaceAdaptor extends RecyclerView.Adapter<PlaceAdaptor.ViewHolder> {
    private List<Places> items;
    private Context context;

    public PlaceAdaptor(ArrayList<Places> placesList, Context applicationContext){
        super();
        items = placesList;
        context= applicationContext;
    }

    @Override
    public PlaceAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurent_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final PlaceAdaptor.ViewHolder holder, final int position) {
        Places list =  items.get(position);
        holder.name.setText(list.getName());
        holder.rating.setText(list.getRating());
        holder.address.setText(list.getAddress());
        Picasso.with(context)
                .load(list.getIcon())
                .into(holder.icon);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView address;
        public TextView rating;
        public ImageView icon;


        public ViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            address= (TextView)itemView.findViewById(R.id.address);
            rating = (TextView) itemView.findViewById(R.id.rating);
            icon= (ImageView)itemView.findViewById(R.id.ig1);

        }
    }
}
