package com.example.weatherapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.weatherapp.RecyclerView.LikedLocationAdapter;
import com.example.weatherapp.Location.LocationName;
import com.example.weatherapp.R;
import com.example.weatherapp.RecyclerView.SelectListener;
import com.example.weatherapp.Location.LocationInfo;
import com.example.weatherapp.Weather.Temperature;
import com.example.weatherapp.ConnectandParse.XmlParser;

import java.net.InetAddress;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SelectListener {

    private SearchView searchView;
    LocationInfo locationInfo;
    XmlParser xmlParser;
    TextView maxTemp, minTemp, avgTemp, locName, likedTextHead;
    ImageView loading, like;
    RecyclerView recyclerView;
    String location;
    ArrayList<LocationName> locationNames = new ArrayList<>();
    private String TAG = "MAINACTIVITY";
    private LikedLocationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationInfo = new LocationInfo();
        xmlParser = new XmlParser();
        addFinishedObserver();
        maxTemp = findViewById(R.id.maxTemp);
        avgTemp = findViewById(R.id.avgTemp);
        minTemp = findViewById(R.id.minTemp);
        locName = findViewById(R.id.locName);
        loading = findViewById(R.id.loading);
        likedTextHead = findViewById(R.id.city_text_view);
        like = findViewById(R.id.like);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LikedLocationAdapter(getApplicationContext(), locationNames, this);
        recyclerView.setAdapter(adapter);
        Log.d(TAG, "onCreate: network " + isNetworkConnected() + " internet: " + isInternetAvailable());
        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d(TAG, "onQueryTextSubmit: " + s);
                location = s;
                clickSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d(TAG, "onQueryTextChange: " + s);
                return false;
            }
        });

        Log.d(TAG, "onCreate: ");
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                likedTextHead.setVisibility(View.VISIBLE);
                locationNames.add(new LocationName(location));
                adapter.notifyItemChanged(locationNames.size() - 1);
            }
        });
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }

    @SuppressLint("SetTextI18n")
    private void clickSearch(String s) {
        Animation iconAnimation = AnimationUtils.loadAnimation(this, R.anim.icon_anim2);
        iconAnimation.setRepeatCount(Animation.INFINITE);
        loading.startAnimation(iconAnimation);
        loading.setVisibility(View.VISIBLE);
        like.setVisibility(View.VISIBLE);
        minTemp.setVisibility(View.GONE);
        maxTemp.setVisibility(View.GONE);
        avgTemp.setVisibility(View.GONE);
        locName.setText("Location:\n" + s);
        locationInfo.getLocationFromAddress(s, getApplicationContext());
        ViewGroup.MarginLayoutParams params1 = (ViewGroup.MarginLayoutParams) searchView.getLayoutParams();
        params1.topMargin = 86;
        searchView.setLayoutParams(params1);
        xmlParser.xmlParse(locationInfo.getLatitude(), locationInfo.getLongitude());

    }

    @SuppressLint("SetTextI18n")
    void addFinishedObserver() {
        xmlParser.getFinished().observe(this, show -> {
            Log.d(TAG, "addFinishedObserver: show " + show);
            loading.setVisibility(View.GONE);
            minTemp.setVisibility(View.VISIBLE);
            maxTemp.setVisibility(View.VISIBLE);
            avgTemp.setVisibility(View.VISIBLE);
            minTemp.setText("Min Temp:\n" + Temperature.getMinTempValue());
            maxTemp.setText("Max Temp:\n" + Temperature.getMaxTempValue());
            avgTemp.setText("Avg Temp: " + Temperature.getAvgTempValue());
        });
    }

    @Override
    public void onItemClicked(LocationName locationName) {
        clickSearch(locationName.getLocName());
    }
}