package higbee.Final;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;



/* resources for creating event lisenters for gps
https://developer.android.com/reference/android/location/LocationListener.html
http://www.exceptionbound.com/programming-tut/android-tutorial/get-current-gps-location-in-android
 */


public class current_drive extends AppCompatActivity {
    protected static String curModel;
    protected static int carIndex;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private long startTime;
    private long endTime;
    private long startLat;
    private long startLong;
    private boolean firstLocCheck = false; //if false this class has not set start lat/long used in condition listener
    Drive drive;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_drive);
        System.out.println("Stuff has been init");

        gpsRequest();

        //TODO create drive object here?

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        startTime = System.currentTimeMillis();




        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //if the location has been sampled before first location should be true
                    //set the current lat/long to the updated location
                //if firstLocCheck is false create a new drive with the current location
                if(firstLocCheck == false){
                    drive = new Drive(location.getLatitude(),location.getLongitude(),Car.carList.get(carIndex),startTime);
                    firstLocCheck = true;
                } else {
                    drive.curLat = location.getLatitude();
                    drive.curLong = location.getLongitude();
                }
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
                Toast.makeText(getApplicationContext(),"Location services have been suspended....",Toast.LENGTH_SHORT).show();

            }
        };



        final Button btnDoneDriving = (Button) findViewById(R.id.endDrive);
        btnDoneDriving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTime = System.currentTimeMillis();
                //these are static....
                Drive.times = endTime - startTime;
                //set instance final lat long to static current lat long
                drive.finLong = drive.curLong;
                drive.finLat = drive.curLat;
                System.out.println("You have ended your drive at" + drive.finLat + " | " + drive.finLong);
                Toast.makeText(getApplicationContext(),"Ending drive",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(current_drive.this, drive_ended.class));


            }
        });

        System.out.println("Currently using the following model: " + Car.carList.get(carIndex).model + " With " + Car.carList.get(carIndex).miles + " miles!");

    }



    private void gpsRequest(){
        new AlertDialog.Builder(this)
                .setTitle("Enable GPS")
                .setMessage("Would you like to allow location tracking?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //yes

                        configLoc();
                        Toast.makeText(getApplicationContext(),"Location tracking is enabled",Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //no
                        Toast.makeText(getApplicationContext(),"Location Tracking has been disabled",Toast.LENGTH_SHORT).show();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    //http://blog.teamtreehouse.com/beginners-guide-location-android
    //
  void configLoc(){
      System.out.println("Configuring location");

      // first check for permissions
      if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
              PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
              Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
              requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                              Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                      , 10);
          }
          return;
      }
    locationManager.requestLocationUpdates("gps",5000,0,locationListener);
  }


}