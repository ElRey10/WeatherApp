package com.example.weatherapp.RecyclerView;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;

public class LocationViewHolder extends RecyclerView.ViewHolder {
    View cardView;
    TextView locNames;

    public LocationViewHolder(@NonNull View itemView) {
        super(itemView);
        locNames = itemView.findViewById(R.id.city_name);
        cardView = itemView.findViewById(R.id.card_view);
    }
}
