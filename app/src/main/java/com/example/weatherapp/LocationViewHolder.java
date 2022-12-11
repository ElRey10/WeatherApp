package com.example.weatherapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LocationViewHolder extends RecyclerView.ViewHolder {
    TextView locNames;
    public LocationViewHolder(@NonNull View itemView) {
        super(itemView);
        locNames = itemView.findViewById(R.id.city_name);
    }
}
