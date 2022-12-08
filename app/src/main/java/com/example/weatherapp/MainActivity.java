package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherapp.weather.LocationInfo;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class MainActivity extends AppCompatActivity {
    private SearchView searchView;
    LocationInfo locationInfo;
    private TextView textView;
    private String TAG="MAINACTIVITY";
    String URL = "http://192.168.1.6:14870/SOAPWebService/services/DemoService?WSDL";
    String NAMESPACE = "http://com";
    String SOAP_ACTION = "http://com/square";
    String METHOD_NAME = "square";
    String PARAMETER_NAME = "n";
    SoapObject request;
    SoapObject testy;
    final String METHOD_NAME1 = "GetWeather";
    final String SOAP_ACTION1 = "http://www.webserviceX.NET/GetWeather";
    final String NAMESPACE2 = "http://www.webserviceX.NET";
    final String SOAP_URL = "http://www.webservicex.net/globalweather.asmx";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationInfo = new LocationInfo();
        textView = findViewById(R.id.bool);
        Log.d(TAG, "onCreate: "+isNetworkConnected());
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
    @SuppressLint("SetTextI18n")
    private void clickSearch(String s){
        ViewGroup.MarginLayoutParams params1= (ViewGroup.MarginLayoutParams) searchView.getLayoutParams();
        params1.topMargin=86;
        locationInfo.getLocationFromAddress(s,getApplicationContext());
        textView.setText("Location: "+s+"\nLatitude: "+locationInfo.getLatitude()+"\nLongitude: "+locationInfo.getLongitude());
        textView.setVisibility(View.VISIBLE);
        searchView.setLayoutParams(params1);

    }
    class CallWebService extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String s) {
            Log.d(TAG, "onPostExecute: "+s);
            textView.setText("Square = " + s);
        }

        @Override
        protected String doInBackground(String... params) {
            String result = "";

            SoapObject soapObject = new SoapObject(NAMESPACE, METHOD_NAME);
            Log.d(TAG, "doInBackground: "+soapObject);
            PropertyInfo propertyInfo = new PropertyInfo();
            propertyInfo.setName(PARAMETER_NAME);
            propertyInfo.setValue(params[0]);
            propertyInfo.setType(String.class);

            soapObject.addProperty(propertyInfo);

            SoapSerializationEnvelope envelope =  new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(soapObject);

            HttpTransportSE httpTransportSE = new HttpTransportSE(URL);

            try {
                httpTransportSE.call(SOAP_ACTION, envelope);
                SoapPrimitive soapPrimitive = (SoapPrimitive)envelope.getResponse();
                result = soapPrimitive.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d(TAG, "doInBackground: "+result);
            return result;
        }
    }
    private class testyTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            request = new SoapObject(NAMESPACE2, METHOD_NAME1);
            request.addProperty("CityName", "Delhi");
            request.addProperty("CountryName", "India");
            Log.e("request1", request.getProperty("CityName").toString());
            Log.e("request2", request.getProperty("CountryName").toString());

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE httpTransport = new HttpTransportSE(SOAP_URL);
            Log.d("transport", httpTransport.toString());
            httpTransport.debug = true;
            try {

                httpTransport.call(SOAP_ACTION1, envelope);
                testy = (SoapObject) envelope.bodyIn;


            } catch (Exception e) {
                e.getMessage();
            }

            //Log.d("dump Request: " , httpTransport.requestDump);
            String xml = httpTransport.responseDump;
         //   Log.e("dump response: ", xml); //Error here
            //Log.e("weather", testy.toString());

            return "blah";
        }

        @Override
        protected void onPostExecute(String aVoid) {

            Log.e("end test", testy.toString());
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(), testy.toString(), Toast.LENGTH_LONG).show();
        }

    }
}