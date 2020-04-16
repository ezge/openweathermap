package rs.gecko.ezge.weather.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static java.net.HttpURLConnection.HTTP_OK;

public class Helper {

    private static String stream = null;

    public Helper(){
    }

    public String getHttpResponse(String urlString){

        try {
            URL url = new URL(urlString);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            if (httpURLConnection.getResponseCode() == HTTP_OK){

                BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuilder sb = new StringBuilder();

                String line;
                while ((line = reader.readLine()) != null)
                    sb.append(line);
                stream = sb.toString();
                httpURLConnection.disconnect();
            }
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        return stream;
    }
}
