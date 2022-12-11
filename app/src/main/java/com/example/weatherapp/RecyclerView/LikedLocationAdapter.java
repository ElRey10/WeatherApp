package com.example.weatherapp.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.Location.LocationName;
import com.example.weatherapp.R;

import java.util.ArrayList;

public class LikedLocationAdapter extends RecyclerView.Adapter<LocationViewHolder> {
    Context context;
    ArrayList<LocationName> names;
    private SelectListener listener;

    public LikedLocationAdapter(Context context, ArrayList<LocationName> names, SelectListener listener) {
        this.context = context;
        this.names = names;
        this.listener = listener;
    }


    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LocationViewHolder(LayoutInflater.from(context).inflate(R.layout.liked_loc, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.locNames.setText(names.get(position).getLocName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClicked(names.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (names == null) {
            return 0;
        } else {
            return names.size();
        }
    }
}
