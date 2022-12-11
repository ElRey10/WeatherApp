package com.example.weatherapp.Utils;

import android.util.Log;

import java.text.DecimalFormat;

public class Utils {
    private static final String TAG = "Utils";
    private DecimalFormat df = new DecimalFormat("0.00");

    public String doubleToString(Double value) {
        String val = String.valueOf(df.format(value));
        Log.d(TAG, "doubleToString: " + val);
        return val;
    }

}
