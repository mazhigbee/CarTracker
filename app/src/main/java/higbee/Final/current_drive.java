package higbee.Final;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;



/* resources for creating event lisenters for gps
https://developer.android.com/reference/android/location/LocationListener.html
http://www.exceptionbound.com/programming-tut/android-tutorial/get-current-gps-location-in-android
 */


public class current_drive extends AppCompatActivity  {


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

        //TODO make this useful?

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        //fetch system time for drive start (utc epoch)
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
                    Drive.curLat = location.getLatitude();
                    Drive.curLong = location.getLongitude();
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



        final ImageButton btnDoneDriving = (ImageButton) findViewById(R.id.endDrive);
        btnDoneDriving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //end time from utc
                drive.endTime = System.currentTimeMillis();
                //these are static....
                drive.totalTime = drive.endTime - drive.startTime;
                //set instance final lat long to static current lat long
                drive.finLong = Drive.curLong;
                drive.finLat = Drive.curLat;
                System.out.println("You have ended your drive at" + drive.finLat + " | " + drive.finLong);
                Toast.makeText(getApplicationContext(),"Ending drive",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(current_drive.this, drive_ended.class));


            }
        });
        ImageButton mapViewer = (ImageButton) findViewById(R.id.navBtn);
        mapViewer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(view.getContext())
                        .setTitle("Open Maps?")
                        .setMessage("Would you like to open you Map Application?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //yes
                                Uri loc = Uri.parse("geo:"+ Drive.curLat + ","+Drive.curLong + "?z=14");
                                Intent mapIntent = new Intent(Intent.ACTION_VIEW,loc);
                                startActivity(mapIntent);
                                Toast.makeText(getApplicationContext(),"Opening Map...",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //no
                                //do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });

        System.out.println("Currently using the following model: " + Car.carList.get(carIndex).model + " With " + Car.carList.get(carIndex).miles + " miles!");

    }




    //LOCATION INFO ADAPTED FROM   //http://blog.teamtreehouse.com/beginners-guide-location-android


/*
 *This functions propmts user to allow for gps
 * THIS IS NEED TO START THE REQUEST IN CONFIGLOC()
 */
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

  /*
   * Checks the manifest to make sure the user has okay'd the use of gps
   * if not the user will be prompted to make that change
   * then the location manager will fire the request location updates
   * without the call to request location updates location will not be updated
   */
  private void configLoc(){
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