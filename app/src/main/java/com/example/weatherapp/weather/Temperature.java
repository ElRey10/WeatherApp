package com.example.weatherapp.Weather;

import android.util.Log;

import com.example.weatherapp.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;


public class Temperature {
    private static final String TAG = "Temperature";
    ArrayList<String> maxTempValues = new ArrayList<>();
    ArrayList<String> minTempValues = new ArrayList<>();
    ArrayList<String> tempValues = new ArrayList<>();
    public static String minTempValue, maxTempValue, avgTempValue;
    Utils utils = new Utils();

    public String extractMaxTemp(JSONArray temp) {
        try {
            JSONArray maxTemp = temp.getJSONObject(1).getJSONArray("value");
            if (!maxTempValues.isEmpty()) {
                maxTempValues.clear();
            }
            for (int i = 0; i < maxTemp.length(); i++) {
                JSONObject json = maxTemp.getJSONObject(i);
                maxTempValues.add(json.getString("content"));
            }
            setMaxTempValue(Collections.max(maxTempValues));
            Log.d(TAG, "extractMaxTemp: " + maxTempValue);
            return maxTempValue;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String extractMinTemp(JSONArray temp) {

        try {
            JSONArray minTemp = temp.getJSONObject(0).getJSONArray("value");
            if (!minTempValues.isEmpty()) {
                minTempValues.clear();
            }
            for (int i = 0; i < minTemp.length(); i++) {
                JSONObject json = minTemp.getJSONObject(i);
                minTempValues.add(json.getString("content"));
            }
            setMinTempValue(Collections.min(minTempValues));
            Log.d(TAG, "extractMinTemp: " + minTempValue);
            return minTempValue;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String averageTemp(JSONArray temp) {
        try {
            JSONArray avgTemp = temp.getJSONObject(2).getJSONArray("value");
            if (!tempValues.isEmpty()) {
                tempValues.clear();
            }
            for (int i = 0; i < avgTemp.length(); i++) {
                JSONObject json = avgTemp.getJSONObject(i);
                tempValues.add(json.getString("content"));
            }
            Log.d(TAG, "averageTemp: " + tempValues);
            setAvgTempValue(utils.doubleToString((tempValues.stream().mapToDouble(Double::parseDouble).average()).getAsDouble()));
            return avgTempValue;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setAvgTempValue(String avgTempValue) {
        this.avgTempValue = avgTempValue;
    }

    public static String getAvgTempValue() {
        return avgTempValue;
    }

    public void setMaxTempValue(String maxTempValue) {
        this.maxTempValue = maxTempValue;
    }


    public void setMinTempValue(String minTempValue) {
        this.minTempValue = minTempValue;
    }

    public static String getMinTempValue() {
        return minTempValue;
    }

    public static String getMaxTempValue() {
        return maxTempValue;
    }
}
