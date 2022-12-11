package com.example.weatherapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LikedLocationAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView favLoc;
    public LikedLocationAdapter(@NonNull View itemView) {
        super(itemView);
        favLoc=itemView.findViewById(R.id.favLoc);
    }

    @Override
    public void onClick(View view) {

    }
}
