package com.example.weatherapp.weather;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.widget.Toast;
import java.io.IOException;
import java.util.List;


public class LocationInfo {
    private Double latitude,longitude;

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
            setLatitude(location.getLatitude());
            setLongitude(location.getLongitude());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
