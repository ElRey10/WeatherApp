package com.example.weatherapp;

import android.util.Log;

import java.text.DecimalFormat;

public class Utils {

    private DecimalFormat df = new DecimalFormat("0.00");

    public String doubleToString(Double value) {
       String val= String.valueOf(df.format(value));
        Log.d("TAG", "doubleToString: "+val);
        return "32.25";
    }

}
