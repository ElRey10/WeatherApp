package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.weatherapp.weather.LocationInfo;
import com.example.weatherapp.weather.ParseXmlSoapRequest;
import com.example.weatherapp.weather.XmlParser;

import java.net.InetAddress;

public class MainActivity extends AppCompatActivity {
    private SearchView searchView;
    LocationInfo locationInfo;
    XmlParser xmlParser;
    private TextView textView;
    private String TAG="MAINACTIVITY";
    ParseXmlSoapRequest parseXmlSoapRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationInfo = new LocationInfo();
        xmlParser =new XmlParser();
        textView = findViewById(R.id.bool);
        parseXmlSoapRequest = new ParseXmlSoapRequest();
        Log.d(TAG, "onCreate: network "+isNetworkConnected()+" internet: "+isInternetAvailable());
        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d(TAG, "onQueryTextSubmit: " + s);
                clickSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d(TAG, "onQueryTextChange: "+s);
                return false;
            }
        });
        Log.d(TAG, "onCreate: ");
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
    private void clickSearch(String s){
        ViewGroup.MarginLayoutParams params1= (ViewGroup.MarginLayoutParams) searchView.getLayoutParams();
        params1.topMargin=86;
        new getLocationAsynTask().execute(s);
        textView.setText("Location: "+s+"\nLatitude: "+locationInfo.getLatitude()+"\nLongitude: "+locationInfo.getLongitude());
        textView.setVisibility(View.VISIBLE);
        searchView.setLayoutParams(params1);
        xmlParser.xmlParse();

    }

private class getLocationAsynTask extends AsyncTask<String, Void, Void> {

    @Override
    protected Void doInBackground(String... params) {
        locationInfo.getLocationFromAddress(params[0],getApplicationContext());
        return null;
    }
}

}