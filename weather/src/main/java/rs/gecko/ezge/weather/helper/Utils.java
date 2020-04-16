package rs.gecko.ezge.weather.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    private static final String API_KEY = "eadfece3da874d6913eb9b3093f2e92e";
    private static final String API_LINE = "http://api.openweathermap.org/data/2.5/weather";

    public static String apiRequest(String lat, String lon){

        StringBuilder sb = new StringBuilder(API_LINE);
        sb.append(String.format("?lat=%s&lon=%s&appid=%s", lat, lon, API_KEY));

        return sb.toString();
    }

    public static String formatTimeStampToDateTime(double time){

        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        date.setTime((long) time * 1000);

        return dateFormat.format(time);
    }

    public static String getDateNow(){

        DateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getImage(String icon){
        return String.format("http://openweathermap.org/img/w/%s.png", icon);
    }
}
