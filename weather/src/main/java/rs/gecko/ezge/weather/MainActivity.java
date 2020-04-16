package rs.gecko.ezge.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    int MY_PERMISSION = 0;
    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.INTERNET,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSION);
            Log.d(TAG, "Permission Not Granted");
        }

        FragmentManager manager = getSupportFragmentManager();
        ft = manager.beginTransaction();

        if (findViewById(R.id.container_map) != null) {

            if (savedInstanceState != null)
                return;

            ft.add(R.id.container_map, new MapFragment(), null);

        }

        if (findViewById(R.id.container_weather) != null){
            if (savedInstanceState != null)
                return;

            ft.add(R.id.container_weather, new WeatherFragment(), null);

        }

        ft.commit();
    }



}
