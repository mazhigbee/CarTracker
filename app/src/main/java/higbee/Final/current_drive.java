package higbee.Final;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.DateFormat;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.Manifest;
import android.widget.TextView;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.Date;



/* resources for creating event lisenters for gps
https://developer.android.com/reference/android/location/LocationListener.html
http://www.exceptionbound.com/programming-tut/android-tutorial/get-current-gps-location-in-android
 */


public class current_drive extends AppCompatActivity {
    protected static String curModel;
    protected static int carIndex;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private LocationRequest mLocationRequest;
    private long startTime;
    private long endTime;
    private String curTime;
    private boolean isDrive = false; //check if a object for this drive has been created already.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_drive);
        System.out.println("Stuff has been init");

        //TODO create drive object here?

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        startTime = System.currentTimeMillis();


        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)
                .setFastestInterval(1 * 1000);


        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                System.out.println("Location has changed");
                System.out.println(location.getLatitude() + " | " + location.getLongitude());

                //updateLoc(location);
                //locProvider = location.getProvider();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {
                System.out.println("Provider enabled");
            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };


        final Button btnDoneDriving = (Button) findViewById(R.id.endDrive);
        btnDoneDriving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTime = System.currentTimeMillis();
                Drive.times = endTime - startTime;
                startActivity(new Intent(current_drive.this, drive_ended.class));


            }
        });

        System.out.println("Currently using the following model: " + Car.carList.get(carIndex).model + " With " + Car.carList.get(carIndex).miles + " miles!");
        createLocationRequest();
    }




    protected void createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

//    @Override
//    public void onConnected(Bundle connectionHint) {
//
//    }
////
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case 10:
//                configLoc();
//                break;
//            default:
//                break;
//        }
//    }
//  void configLoc(){
//
//      // first check for permissions
//      if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
//              PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
//              Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//              requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
//                              Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
//                      , 10);
//          }
//          return;
//      }
//    locationManager.requestLocationUpdates("gps",5000,0,locationListener);
//  }


}