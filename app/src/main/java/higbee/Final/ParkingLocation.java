package higbee.Final;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

//support for implmenting a map view from
//http://www.vogella.com/tutorials/AndroidGoogleMaps/article.html
//https://developers.google.com/maps/documentation/android-api/start

public class ParkingLocation extends FragmentActivity implements OnMapReadyCallback{
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_location);
        // fetch xml layout fragment and notify when map is ready
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapPark);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Drive lastDrive = Drive.drivesLog.get(Drive.drivesLog.size() - 1);
        double lastLat = lastDrive.finLat;
        double lastLong = lastDrive.finLong;



        // Add marker to last parking location
        //move camera to focus on that spot
        LatLng parkingSpot = new LatLng(lastLat,lastLong);
        mMap.addMarker(new MarkerOptions().position(parkingSpot).title("Parking Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(parkingSpot));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(10));  //1: World  5: Landmass/continent  10: City  15: Streets   20: Buildings

    }
}
