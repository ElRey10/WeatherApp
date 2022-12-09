package com.example.weatherapp.weather;

import android.os.AsyncTask;
import android.util.Log;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class XmlParser {
    public void xmlParse() {
        try {
//                URL url = new URL("https://graphical.weather.gov/xml/SOAP_server/ndfdXMLclient.php?whichClient=NDFDgen&lat=38.99&lon=-77.01&listLatLon=&lat1=&lon1=&lat2=&lon2=&resolutionSub=&listLat1=&listLon1=&listLat2=&listLon2=&resolutionList=&endPoint1Lat=&endPoint1Lon=&endPoint2Lat=&endPoint2Lon=&listEndPoint1Lat=&listEndPoint1Lon=&listEndPoint2Lat=&listEndPoint2Lon=&zipCodeList=&listZipCodeList=&centerPointLat=&centerPointLon=&distanceLat=&distanceLon=&resolutionSquare=&listCenterPointLat=&listCenterPointLon=&listDistanceLat=&listDistanceLon=&listResolutionSquare=&citiesLevel=&listCitiesLevel=&sector=&gmlListLatLon=&featureType=&requestedTime=&startTime=&endTime=&compType=&propertyName=&product=time-series&begin=2022-12-09T00%3A00%3A00&end=2026-12-09T00%3A00%3A00&Unit=e&maxt=maxt&mint=mint&icons=icons&Submit=Submit");
//                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//                DocumentBuilder db = dbf.newDocumentBuilder();
//                Document doc = db.parse(new InputSource(url.openStream()));
//                Log.d("TAG", "xmlParse: "+doc.toString());
//                doc.getDocumentElement().normalize();
            new HttpRequest().execute();

        } catch (Exception e) {
            System.out.println("XML Pasing Excpetion = " + e);
        }
    }

    public static class HttpRequest extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            String response = HttpRequest.excuteGet("https://graphical.weather.gov/xml/SOAP_server/ndfdXMLclient.php?whichClient=NDFDgen&lat=38.99&lon=-77.01&listLatLon=&lat1=&lon1=&lat2=&lon2=&resolutionSub=&listLat1=&listLon1=&listLat2=&listLon2=&resolutionList=&endPoint1Lat=&endPoint1Lon=&endPoint2Lat=&endPoint2Lon=&listEndPoint1Lat=&listEndPoint1Lon=&listEndPoint2Lat=&listEndPoint2Lon=&zipCodeList=&listZipCodeList=&centerPointLat=&centerPointLon=&distanceLat=&distanceLon=&resolutionSquare=&listCenterPointLat=&listCenterPointLon=&listDistanceLat=&listDistanceLon=&listResolutionSquare=&citiesLevel=&listCitiesLevel=&sector=&gmlListLatLon=&featureType=&requestedTime=&startTime=&endTime=&compType=&propertyName=&product=time-series&begin=2022-12-09T00%3A00%3A00&end=2026-12-09T00%3A00%3A00&Unit=e&maxt=maxt&mint=mint&temp=temp&icons=icons&Submit=Submit");
            Log.d("TAG", "xmlParse: " + response);

            return null;
        }

        public static String excuteGet(String targetURL) {
            URL url;
            HttpURLConnection connection = null;
            try {
                url = new URL(targetURL);
                connection = (HttpURLConnection) url.openConnection();
                connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:221.0) Gecko/20100101 Firefox/31.0"); // add this line to your code
                connection.setRequestMethod("GET");
                InputStream is;
                int status = connection.getResponseCode();
                Log.d("TAG", "excuteGet: " + status);
                if (status != HttpURLConnection.HTTP_OK)
                    is = connection.getErrorStream();
                else
                    is = connection.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                String line;
                StringBuffer response = new StringBuffer();
                while ((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }
                rd.close();
                return response.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }


        }
    }
}


