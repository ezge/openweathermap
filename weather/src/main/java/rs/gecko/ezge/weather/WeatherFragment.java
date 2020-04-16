package rs.gecko.ezge.weather;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;

import rs.gecko.ezge.weather.helper.Helper;
import rs.gecko.ezge.weather.helper.Utils;
import rs.gecko.ezge.weather.model.OpenWeatherMap;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeatherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView txtCity, txtDescription, txtLastUpdate, txtHumidity, txtTime, txtCelsius;
    private ImageView imageView;
    private SharedViewModel viewModel;
    private OpenWeatherMap openWeatherMap = new OpenWeatherMap();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WeatherFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WeatherFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WeatherFragment newInstance(String param1, String param2) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        //set controls
        txtCity = view.findViewById(R.id.txtCity);
        txtDescription = view.findViewById(R.id.txtDescription);
        txtLastUpdate = view.findViewById(R.id.txtLastUpdate);
        txtHumidity = view.findViewById(R.id.txtHumidity);
        txtTime = view.findViewById(R.id.txtTime);
        txtCelsius = view.findViewById(R.id.txtCelsius);
        imageView = view.findViewById(R.id.imageView);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        viewModel.getLocation().observe(getViewLifecycleOwner(), new Observer<LatLng>() {
            @Override
            public void onChanged(@Nullable LatLng location) {
                new GetWeather().execute(Utils.apiRequest(String.valueOf(location.latitude), String.valueOf(location.longitude)));
            }
        });
    }

    private class GetWeather extends AsyncTask<String, Void, String> {

        ProgressDialog pd = new ProgressDialog(getContext());

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setTitle("Please wait...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String stream = null;
            String urlString = strings[0];

            Helper http = new Helper();
            stream = http.getHttpResponse(urlString);

            return stream;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s == null){
                pd.dismiss();
                return;
            }

            try {
                Gson gson = new Gson();
                Type token = new TypeToken<OpenWeatherMap>() {
                }.getType();
                openWeatherMap = gson.fromJson(s, token);

                //set controls
                txtCity.setText(String.format("%s, %s", openWeatherMap.getName(), openWeatherMap.getSys().getCountry()));
                txtLastUpdate.setText(String.format("Last Updated: %s", Utils.getDateNow()));
                txtDescription.setText(String.format("%s", openWeatherMap.getWeather().get(0).getDescription()));
                txtHumidity.setText(String.format("%d%%", openWeatherMap.getMain().getHumidity()));
                txtTime.setText(String.format("%s/%s", Utils.formatTimeStampToDateTime(openWeatherMap.getSys().getSunrise()),
                       Utils.formatTimeStampToDateTime(openWeatherMap.getSys().getSunset())));
                txtCelsius.setText(String.format("% .2f °C", openWeatherMap.getMain().getTemp()));
                Picasso.get().load(Utils.getImage(openWeatherMap.getWeather().get(0).getIcon())).into(imageView);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                pd.dismiss();
            }
        }
    }
}
