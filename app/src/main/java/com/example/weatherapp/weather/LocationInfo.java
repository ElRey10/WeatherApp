package com.example.weatherapp.weather;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.widget.Toast;
import java.io.IOException;
import java.util.List;


public class LocationInfo {
    private String latitude;
    private String longitude;

    public void getLocationFromAddress(String strAddress,Context context) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            Log.d("TAG", "getLocationFromAddress: "+address.toString());
            if (address.isEmpty()) {
                Toast.makeText(context, "Address Not Found", Toast.LENGTH_SHORT).show();
                throw new IOException();
            }
            Address location = address.get(0);
            Log.d("TAG", "getLocationFromAddress: "+location);
            setLatitude(Double.toString(location.getLatitude()));
            setLongitude(Double.toString(location.getLongitude()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
