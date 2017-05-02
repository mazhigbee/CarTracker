package higbee.Final;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ParkingLocation extends FragmentActivity implements OnMapReadyCallback{
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
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



        // Add a marker in Sydney and move the camera
        LatLng parkingSpot = new LatLng(lastLat,lastLong);
        mMap.addMarker(new MarkerOptions().position(parkingSpot).title("Parking Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(parkingSpot));
    }
}
