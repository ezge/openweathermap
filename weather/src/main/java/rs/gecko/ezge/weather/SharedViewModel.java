package rs.gecko.ezge.weather;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.model.LatLng;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<LatLng> location = new MutableLiveData<>();

    public void setLocation(LatLng input) {
        location.setValue(input);
    }

    public LiveData<LatLng> getLocation() {
        return location;
    }
}
