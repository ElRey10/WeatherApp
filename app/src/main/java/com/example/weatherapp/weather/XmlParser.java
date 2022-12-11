package com.example.weatherapp.weather;

import android.os.AsyncTask;
import android.util.Log;


import androidx.lifecycle.MutableLiveData;

import com.example.weatherapp.MainActivity;
import com.example.weatherapp.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collections;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import fr.arnaudguyon.xmltojsonlib.XmlToJson;

public class XmlParser {
    Temperature temperature = new Temperature();
    Utils utils;
    MutableLiveData<Boolean> finished;

    public void xmlParse(Double lat, Double lon) {
        try {
            ExecutorService es = Executors.newCachedThreadPool();
            es.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        new ParseXML(String.valueOf(lat),String.valueOf(lon)).execute().get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            es.shutdown();
        } catch (Exception e) {
            System.out.println("XML Parsing Excpetion = " + e);
        }
    }

    public class ParseXML extends AsyncTask<String, Void, String> {
        String lat, lon;

        public ParseXML(String lat, String lon) {
            this.lat = lat;
            this.lon = lon;
        }

        @Override
        protected void onPostExecute(String unused) {
            super.onPostExecute(unused);
            XmlToJson xmlToJson = new XmlToJson.Builder(unused).build();
            JSONObject jsonObject = xmlToJson.toJson();
            getTemp(jsonObject);
        }

        @Override
        protected String doInBackground(String... args) {
            String response = HttpRequest.excuteGet("https://graphical.weather.gov/xml/SOAP_server/ndfdXMLclient.php?whichClient=NDFDgen&lat="+lat+"&lon="+lon+"&listLatLon=&lat1=&lon1=&lat2=&lon2=&resolutionSub=&listLat1=&listLon1=&listLat2=&listLon2=&resolutionList=&endPoint1Lat=&endPoint1Lon=&endPoint2Lat=&endPoint2Lon=&listEndPoint1Lat=&listEndPoint1Lon=&listEndPoint2Lat=&listEndPoint2Lon=&zipCodeList=&listZipCodeList=&centerPointLat=&centerPointLon=&distanceLat=&distanceLon=&resolutionSquare=&listCenterPointLat=&listCenterPointLon=&listDistanceLat=&listDistanceLon=&listResolutionSquare=&citiesLevel=&listCitiesLevel=&sector=&gmlListLatLon=&featureType=&requestedTime=&startTime=&endTime=&compType=&propertyName=&product=time-series&begin=2022-12-09T00%3A00%3A00&end=2026-12-09T00%3A00%3A00&Unit=e&maxt=maxt&mint=mint&temp=temp&icons=icons&Submit=Submit");
            return response;
        }

    }

    void getTemp(JSONObject jsonObject) {
        try {
            JSONArray temp = jsonObject.getJSONObject("dwml").getJSONObject("data").getJSONObject("parameters").getJSONArray("temperature");
            Log.d("TAG", "getTemp: " + temp.toString());
            Log.d("TAG", "getTemp: before " + finished.toString());
            temperature.averageTemp(temp);
            if (temperature.extractMaxTemp(temp) != null && temperature.extractMinTemp(temp) != null) {
                Log.d("TAG", "getTemp: " + temperature.minTempValue + "\n" + temperature.maxTempValue + "\n method" + temperature.getMaxTempValue() + "\n" + temperature.getMinTempValue());
                finished.setValue(true);
                Log.d("TAG", "getTemp: in " + finished.toString());
            }
            Log.d("TAG", "getTemp: after" + finished.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public MutableLiveData<Boolean> getFinished() {
        if (finished == null) {
            finished = new MutableLiveData<Boolean>();
        }
        return finished;
    }

}


