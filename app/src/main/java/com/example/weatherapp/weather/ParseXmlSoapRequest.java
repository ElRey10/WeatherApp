package com.example.weatherapp.weather;

import android.os.AsyncTask;
import android.util.Log;

import org.checkerframework.checker.units.qual.C;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.DecimalFormat;

public class ParseXmlSoapRequest {
    String URL = "https://graphical.weather.gov/xml/sample_products/browser_interface/ndfdXMLclient.php?lat=13.08&lon=80.27&product=time-series&begin=2004-01-01T00:00:00&end=2019-04-20T00:00:00&maxt=maxt&mint=mint";
    String NAMESPACE = "https://graphical.weather.gov/xml/DWMLgen/wsdl/ndfdXML.wsdl";
    String SOAP_ACTION = "https://graphical.weather.gov/xml/DWMLgen/wsdl/ndfdXML.wsdl#NDFDgen";
    String METHOD_NAME = "NDFDgen";
    private static final String TAG = "XML";
    public void webService(){
        CallWebService callWebService = new CallWebService();
        callWebService.execute();
    }

    public class CallWebService extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String s) {
            Log.d(TAG, "onPostExecute: ");
        }

        @Override
        protected String doInBackground(String... params) {

            SoapObject soapObject = new SoapObject(NAMESPACE, METHOD_NAME);

            soapObject.addProperty("latitude", "13.08");
            soapObject.addProperty("longitude","80.09");

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(soapObject);
            envelope.dotNet = true;

            HttpTransportSE httpTransportSE = new HttpTransportSE(URL);

            try {
                httpTransportSE.call(SOAP_ACTION, envelope);
                SoapFault result = (SoapFault) envelope.bodyIn;
                if(result!=null){
                    Log.d(TAG, "doInBackground: "+result.toString());
                }
                else {
                    Log.d(TAG, "doInBackground: no resposne ");
                }
//                SoapPrimitive soapPrimitive = (SoapPrimitive) envelope.getResponse();
//                result = soapPrimitive.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
