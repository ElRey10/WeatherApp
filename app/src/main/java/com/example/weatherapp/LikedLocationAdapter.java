package com.example.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LikedLocationAdapter extends RecyclerView.Adapter<LocationViewHolder> {
    Context context;
    ArrayList<LocationName> names;

    public LikedLocationAdapter(Context context, ArrayList<LocationName> names) {
        this.context = context;
        this.names = names;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LocationViewHolder(LayoutInflater.from(context).inflate(R.layout.liked_loc,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        holder.locNames.setText(names.get(position).getLocName());
    }

    @Override
    public int getItemCount() {
        return names.size();
    }
}
